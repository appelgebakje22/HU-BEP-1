package nl.appelgebakje22.bep;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public final class Opdracht1 {

	public static void main(String[] args) throws IOException {
		final String address = "127.0.0.1";
		final int port = 3050;
		if (args.length == 0 || args[0].equals("--startServer"))
			new Server(port).start();
		else if (args[0].equals("--startClient"))
			new Client(address, port).start();
	}

	private static class Server {

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

	private static class Client {

		private final String address;
		private final int port;
		private final Socket client;

		public Client(String address, int port) {
			this.address = Objects.requireNonNull(address, "address is null!");
			this.port = port;
			this.client = new Socket();
		}

		public void start() throws IOException {
			this.client.connect(new InetSocketAddress(this.address, this.port));
			System.out.println("Connected to server: " + this.client.toString());
			OutputStream out = this.client.getOutputStream();
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String theLine = scanner.nextLine();
				if (theLine.equals("exit"))
					break;
				out.write(theLine.concat("\n").getBytes());
			}
			System.out.println("Exit command received! Exiting!");
			this.client.close();
		}
	}
}