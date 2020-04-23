package nl.appelgebakje22.bep.prc1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

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
