package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Account {
	public static final String FILE_WITH_ACCOUNT = "Server/src/application/account";
	public static String USER;
	public static String PASS;
	
	static {
		try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
		     BufferedReader reader = new BufferedReader(fileReader)) {
			USER = reader.readLine();
			PASS = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Account() {
		throw new IllegalStateException("Utility class");
	}
}
