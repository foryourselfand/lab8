package elements;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Organization implements Serializable {
	private final String name; //Поле не может быть null, Строка не может быть пустой
	private final long annualTurnover; //Значение поля должно быть больше 0
	private final OrganizationType organizationType; //Поле может быть null
	private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
	
	public Organization(int id, String name, long annualTurnover, OrganizationType organizationType) {
		this.id = id;
		this.name = name;
		this.annualTurnover = annualTurnover;
		this.organizationType = organizationType;
	}
	
	public Organization(String name, long annualTurnover, OrganizationType organizationType) {
		this.name = name;
		this.annualTurnover = annualTurnover;
		this.organizationType = organizationType;
	}
}
