package nl.appelgebakje22.bep.prc1_3;

import java.io.*;

public final class Opdracht1_3 {

	private static final int PORT = 3050;

	public static void main(String[] args) throws IOException {
		new Server(Opdracht1_3.PORT).start();
	}

}