package nl.appelgebakje22.bep.prc1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	private final ServerSocket server;

	public Server(int port) throws IOException {
		this.server = new ServerSocket(port);
	}

	public void start() throws IOException {
		Socket conn = this.server.accept();
		System.out.println("Connected to client: " + conn.toString());
		Scanner scanner = new Scanner(conn.getInputStream());
		while (scanner.hasNextLine())
			System.out.println(scanner.nextLine());
		System.out.println("Client has disconnected! Exiting!");
	}
}