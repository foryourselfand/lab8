package elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public class Coordinates implements Serializable {
	private final double x;
	private final int y; //Максимальное значение поля: 999
}
