package gui;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public class Circle {
	private final double x;
	private final double y;
	private final double radius;
	private final Color color;
}
