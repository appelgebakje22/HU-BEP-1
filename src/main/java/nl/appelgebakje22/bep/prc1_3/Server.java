package nl.appelgebakje22.bep.prc1_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.sound.midi.Soundbank;

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
		String path = null;
		while (true) {
			String line = reader.readLine();
			if (path == null) // Always true for the first line in the request
				path = line.split(" ")[1];
			if (line.isEmpty())
				break;
		}
		System.out.println("Requested path: " + path);
		// Response
		File requestedFile = new File("." + path).getAbsoluteFile();
		OutputStream out = conn.getOutputStream();
		HTTPPrintWriter writer = new HTTPPrintWriter(out);
		if (!requestedFile.exists() || requestedFile.isDirectory()) {
			writer.println("HTTP/1.1 404 File Not Found");
			writer.println();
			writer.println("<h1>404: File not found!</h1>");
		} else {
			Path fileAsPath = requestedFile.toPath();
			byte[] fileBytes = Files.readAllBytes(fileAsPath);
			writer.println("HTTP/1.1 200 OK");
			writer.println("Content-Length: " + fileBytes.length);
			writer.println();
			out.write(fileBytes);
			writer.println();
		}
		conn.close();
	}
}