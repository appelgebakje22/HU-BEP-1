package nl.appelgebakje22.bep.prc2;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

final class MyServlet extends Thread {

	private final Socket socket;

	MyServlet(Socket sock) {
		super("Client-Thread: " + sock.getRemoteSocketAddress());
		this.socket = sock;
		this.start();
	}

	@Override
	public void run() {
		try {
			// Request
			Scanner scanner = new Scanner(this.socket.getInputStream());
			String path = null;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (path == null) // Always true for the first line in the request
					path = line.split(" ")[1];
				if (line.isEmpty())
					break;
			}
			System.out.println("Requested path: " + path);

			// Response
			File requestedFile = new File("." + path).getAbsoluteFile();

			OutputStream output = this.socket.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			if (!requestedFile.exists() || requestedFile.isDirectory()) {
				writer.write("HTTP/1.1 404 File Not Found\r\n");
				writer.write("\r\n");
				writer.write("<h1>404: File not found!</h1>\r\n");
			} else {
				writer.write("HTTP/1.1 200 OK\r\n");
				writer.write("\r\n");
				writer.flush();
				output.write(Files.readAllBytes(requestedFile.toPath()));
				output.flush();
				writer.write("\r\n");
			}
			writer.flush();
			this.socket.close();
		} catch (IOException e) {
			System.err.println("Exception in thread: " + this.getName());
			e.printStackTrace();
		}
	}
}