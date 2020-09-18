package elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product implements Serializable {
	private Integer idUser;
	private Integer id; //Поле не может быть null, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
	private String name; //Поле не может быть null, Строка не может быть пустой
	private Coordinates coordinates; //Поле не может быть null
	private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
	private double price; //Значение поля должно быть больше 0
	private String partNumber; //Поле может быть null
	private Long manufactureCost; //Поле не может быть null
	private UnitOfMeasure unitOfMeasure; //Поле может быть null
	private Organization organization; //Поле может быть null
	
	public Product(String name, Coordinates coordinates, double price, String partNumber, Long manufactureCost, UnitOfMeasure unitOfMeasure, Organization organization) {
		this.name = name;
		this.coordinates = coordinates;
		this.price = price;
		this.partNumber = partNumber;
		this.manufactureCost = manufactureCost;
		this.unitOfMeasure = unitOfMeasure;
		this.organization = organization;
	}
	
	public void generateCreationDate() {
		if (this.creationDate != null)
			return;
		this.creationDate = ZonedDateTime.now();
	}
	
	public void updateProduct(Product product) {
		name = product.name;
		coordinates = product.coordinates;
		price = product.price;
		partNumber = product.partNumber;
		manufactureCost = product.manufactureCost;
		unitOfMeasure = product.unitOfMeasure;
		int organizationId = organization.getId();
		organization = product.organization;
		organization.setId(organizationId);
	}
}
