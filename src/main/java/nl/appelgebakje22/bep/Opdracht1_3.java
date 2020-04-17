package nl.appelgebakje22.bep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public final class Opdracht1_3 {

	public static void main(String[] args) throws IOException {
		final int port = 3050;
		if (args.length == 0 || args[0].equals("--startServer"))
			new Opdracht1_3.Server(port).start();
	}

	private static class Server {

		private final ServerSocket server;

		public Server(int port) throws IOException {
			this.server = new ServerSocket(port);
		}

		public void start() throws IOException {
			Socket conn = this.server.accept();
			System.out.println("Connected to client: " + conn.toString());
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			DataInputStream in = new DataInputStream(conn.getInputStream());
			// Request
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			if (!requestedFile.exists() || requestedFile.isDirectory()) {
				writer.write("HTTP/1.1 404 File Not Found\r\n");
				writer.write("\r\n");
				writer.write("<h1>404: File not found!</h1>\r\n");
			} else {
				writer.write("HTTP/1.1 200 OK\r\n");
				writer.write("\r\n");
				writer.flush();
				out.write(Files.readAllBytes(requestedFile.toPath()));
				out.flush();
				writer.write("\r\n");
			}
			writer.flush();
			conn.close();
		}
	}
}