package nl.appelgebakje22.bep.prc1;

import java.io.IOException;

public final class Opdracht1 {

	private static final String ADDRESS = "localhost";
	private static final int PORT = 3050;


	public static void main(String[] args) throws IOException {
		if (args.length == 0 || args[0].equals("--startServer"))
			new Server(Opdracht1.PORT).start();
		else if (args[0].equals("--startClient"))
			new Client(Opdracht1.ADDRESS, Opdracht1.PORT).start();
	}

}