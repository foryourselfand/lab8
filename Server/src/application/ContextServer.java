package application;

import collection.ProductList;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import commands.*;
import lombok.Getter;
import lombok.Setter;
import network.AddressedRequest;
import network.AddressedResponse;
import network.HandlerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.BindException;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Setter
public class ContextServer {
	public static final int QUANTITY_THREAD = 1;
	protected Logger logger;
	
	private HandlerClient handlerClient;
	private HandlerCommands handlerCommands;
	private HandlerDatabase handlerDatabase;
	
	private BlockingQueue<AddressedRequest> queueAddressedRequests;
	private BlockingQueue<AddressedResponse> queueAddressedResponse;
	
	private ProductList productList;
	
	public ContextServer() {
		handlerClient = new HandlerClient();
		handlerCommands = new HandlerCommands(this);
		handlerDatabase = new HandlerDatabase(this);
		
		logger = LoggerFactory.getLogger(ContextServer.class);
		
		queueAddressedRequests = new LinkedBlockingQueue<>();
		queueAddressedResponse = new LinkedBlockingQueue<>();
		
		handlerCommands
				.setCommand(new CommandAdd())
				.setCommand(new CommandAddIfMin())
				.setCommand(new CommandClear())
				.setCommand(new CommandHelp())
				.setCommand(new CommandInfo())
				.setCommand(new CommandLogin())
				.setCommand(new CommandPrintAscending())
				.setCommand(new CommandPrintLessUnitOfMeasure())
				.setCommand(new CommandRegistration())
				.setCommand(new CommandRemoveById())
				.setCommand(new CommandRemoveByManufactureCost())
				.setCommand(new CommandRemoveFirst())
				.setCommand(new CommandUpdateById());
	}
	
	private String getUrl() {
		String host = "se.ifmo.ru";
		int port = 2222;
		
		String user = Account.USER;
		String pass = Account.PASS;
		
		String listenerHost = "localhost";
		int listenerPort = 5888;
		
		String listeningHost = "pg";
		int listeningPort = 5432;
		
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(pass);
			session.setConfig("StrictHostKeyChecking", "no");
			
			session.connect();
			
			session.setPortForwardingL(listenerPort, listeningHost, listeningPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "jdbc:postgresql://" + listenerHost + ":" + listenerPort + "/studs";
	}
	
	public void initialization(String[] args) {
		if (args.length != 1) {
			logger.error("Некорректный ввод порта!");
			System.exit(1);
		}
		
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			logger.error("Некорректный ввод порта!");
			System.exit(1);
		}
		try {
			handlerDatabase.initialization(getUrl(), Account.USER, Account.PASS);
		} catch (Exception e) {
			logger.error("Не удалось подключиться к базе данных!");
			System.exit(1);
		}
		
		try {
			productList = handlerDatabase.getProductList();
		} catch (SQLException e) {
			logger.error("Не удалось прочесть коллекцию из базы данных!");
			System.exit(0);
		}
		logger.info("Коллекция заполнена.");
		
		try {
			handlerClient.bind(port);
			logger.info("Сервер инициализирован.");
		} catch (BindException e) {
			logger.error("Этот порт занят! Выберите другой порт и перезапустите программу.");
			System.exit(0);
		} catch (IOException e) {
			logger.error("Запустить сервер не удалось: " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void execution() {
		logger.info("Работа сервера запущенна.");
		
		ThreadPoolExecutor executorReceiver = (ThreadPoolExecutor) Executors.newFixedThreadPool(QUANTITY_THREAD);
		for (int i = 0; i < QUANTITY_THREAD; i++)
			executorReceiver.submit(new Receiver(this));
		
		ThreadPoolExecutor executorTransmitter = (ThreadPoolExecutor) Executors.newFixedThreadPool(QUANTITY_THREAD);
		while (true) {
			if (! queueAddressedRequests.isEmpty())
				new Thread(new HandlerRequest(this)).start();
			
			if (! queueAddressedResponse.isEmpty())
				executorTransmitter.submit(new Transmitter(this));
		}
	}
	
	public void run() {
		Runtime.getRuntime().addShutdownHook(new Thread(()->logger.info("Завершение программы.")));
		execution();
	}
}
