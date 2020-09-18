package application;

import collection.ProductList;
import elements.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZonedDateTime;

public class HandlerDatabase {
	private final ContextServer context;
	private Connection connection;
	
	public HandlerDatabase(ContextServer context) {
		this.context = context;
	}
	
	public void initialization(String url, String user, String password) throws SQLException {
		if (user == null || password == null) {
			connection = DriverManager.getConnection(url);
		} else {
			connection = DriverManager.getConnection(url, user, password);
		}
	}
	
	public ProductList getProductList() throws SQLException {
		ProductList productList = new ProductList(context);
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM product")) {
			while (resultSet.next()) {
				int idUser = resultSet.getInt(1);
				int id = resultSet.getInt(2);
				String name = resultSet.getString(3);
				double coordinateX = resultSet.getDouble(4);
				int coordinateY = resultSet.getInt(5);
				String creationDate = resultSet.getString(6);
				double price = resultSet.getDouble(7);
				String partNumber = resultSet.getString(8);
				long manufactureCost = resultSet.getLong(9);
				String unitOfMeasure = resultSet.getString(10);
				int organizationId = resultSet.getInt(11);
				String organizationName = resultSet.getString(12);
				long organizationAnnualTurnover = resultSet.getLong(13);
				String organizationType = resultSet.getString(14);
				
				productList.addFromDatabase(new Product(
						idUser,
						id,
						name,
						new Coordinates(coordinateX, coordinateY),
						ZonedDateTime.parse(creationDate),
						price,
						partNumber,
						manufactureCost,
						UnitOfMeasure.valueOf(unitOfMeasure),
						new Organization(
								organizationId,
								organizationName,
								organizationAnnualTurnover,
								OrganizationType.valueOf(organizationType)
						)
				));
			}
		}
		return productList;
	}
	
	public Product addProduct(Product product) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product VALUES (?, DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, product.getIdUser());
			preparedStatement.setString(2, product.getName());
			preparedStatement.setDouble(3, product.getCoordinates().getX());
			preparedStatement.setInt(4, product.getCoordinates().getY());
			preparedStatement.setString(5, product.getCreationDate().toString());
			preparedStatement.setDouble(6, product.getPrice());
			preparedStatement.setString(7, product.getPartNumber());
			preparedStatement.setLong(8, product.getManufactureCost());
			preparedStatement.setString(9, product.getUnitOfMeasure().toString());
			preparedStatement.setString(10, product.getOrganization().getName());
			preparedStatement.setLong(11, product.getOrganization().getAnnualTurnover());
			preparedStatement.setString(12, product.getOrganization().getOrganizationType().toString());
			preparedStatement.executeUpdate();
			ResultSet set = preparedStatement.getGeneratedKeys();
			if (set.next()) {
				product.setId(set.getInt(set.findColumn("id")));
				product.getOrganization().setId(set.getInt(set.findColumn("organization_id")));
			}
			else {
				throw new SQLException();
			}
		}
		return product;
	}
	
	public void updateProduct(Product product) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET id_user = ?, id = ?, name = ?, coordinate_x = ?, coordinate_y = ?, creationdate = ?, price = ?, partnumber = ?, manufacturecost = ?, unitofmeasure = ?, organization_id = ?, organization_name = ?,  organization_annualturnover = ?, organization_type = ? WHERE id = ?")) {
			preparedStatement.setInt(1, product.getIdUser());
			preparedStatement.setInt(2, product.getId());
			preparedStatement.setString(3, product.getName());
			preparedStatement.setDouble(4, product.getCoordinates().getX());
			preparedStatement.setInt(5, product.getCoordinates().getY());
			preparedStatement.setString(6, product.getCreationDate().toString());
			preparedStatement.setDouble(7, product.getPrice());
			preparedStatement.setString(8, product.getPartNumber());
			preparedStatement.setLong(9, product.getManufactureCost());
			preparedStatement.setString(10, product.getUnitOfMeasure().toString());
			preparedStatement.setInt(11, product.getOrganization().getId());
			preparedStatement.setString(12, product.getOrganization().getName());
			preparedStatement.setLong(13, product.getOrganization().getAnnualTurnover());
			preparedStatement.setString(14, product.getOrganization().getOrganizationType().toString());
			preparedStatement.setInt(15, product.getId());
			preparedStatement.execute();
		}
	}
	
	public void removeProduct(Product product) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?")) {
			preparedStatement.setInt(1, product.getId());
			preparedStatement.execute();
		}
	}
	
	public void registrationUser(String login, String password) throws NoSuchAlgorithmException, SQLException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
		byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (DEFAULT , ?, ?)")) {
			preparedStatement.setString(1, login);
			String hash = new String(bytes, StandardCharsets.UTF_8);
			preparedStatement.setString(2, hash);
			preparedStatement.execute();
		}
	}
	
	public int isExistingUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ?")) {
			preparedStatement.setString(1, login);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int idUser = resultSet.getInt(1);
					String databasePass = resultSet.getString(3);
					MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
					byte[] passwordBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
					String inputPass = new String(passwordBytes, StandardCharsets.UTF_8);
					
					if (inputPass.equals(databasePass)) {
						return idUser;
					} else {
						throw new SQLException();
					}
				} else {
					throw new SQLException();
				}
			}
		}
	}
}
