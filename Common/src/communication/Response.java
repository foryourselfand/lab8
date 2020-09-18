package communication;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Response implements Serializable {
	private final String nameCommand;
	private final String resultCommand;
	private final Argument argument;
	
	public Response(Argument argument) {
		this.nameCommand = null;
		this.resultCommand = null;
		this.argument = argument;
	}
	
	public Response(String nameCommand, String resultCommand) {
		this.nameCommand = nameCommand;
		this.resultCommand = resultCommand;
		this.argument = null;
	}
}
