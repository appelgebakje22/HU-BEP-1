package nl.appelgebakje22.bep.prc1_2;

import java.io.*;

public final class Opdracht1_2 {

	private static final int PORT = 3050;

	public static void main(String[] args) throws IOException {
		new Server(Opdracht1_2.PORT).start();
	}

}