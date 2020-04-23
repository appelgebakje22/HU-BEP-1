package nl.appelgebakje22.bep.prc1_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private final ServerSocket server;

	public Server(int port) throws IOException {
		this.server = new ServerSocket(port);
	}

	public void start() throws IOException {
		Socket conn = this.server.accept();
		System.out.println("Connected to client: " + conn.toString());
		// Request
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while (true) {
			String line = reader.readLine();
			System.out.println(line);
			if (line.isEmpty())
				break;
		}
		// Response
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write("HTTP/1.1 200 OK\r\n");
		writer.write("\r\n");
		writer.write("<h1>It works!</h1>\r\n");
		writer.flush();
		conn.close();
	}
}
