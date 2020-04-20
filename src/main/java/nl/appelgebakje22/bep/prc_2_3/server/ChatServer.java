package nl.appelgebakje22.bep.prc_2_3.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	public final static int PORT = 3051;

	public static void main(String[] args) throws IOException {
		List<PrintWriter> allClients = new ArrayList<>();

		try (ServerSocket ss = new ServerSocket(PORT)) {
			System.out.printf("Server started on port %d!\n", PORT);

			while (true) {
				try {
					Socket s = ss.accept();

					// voor elke nieuwe client een aparte thread om binnenkomende chatberichten te verwerken
					new Thread(() -> {
						PrintWriter pw = null;

						// try-with-resource (scanner wordt na afloop automatisch gesloten):
						try (Scanner scanner = new Scanner(s.getInputStream())) {
							allClients.add(pw = new PrintWriter(s.getOutputStream()));

							while (scanner.hasNextLine()) {
								String message = scanner.nextLine();
								System.out.println("Incoming and distributing: " + message);

								// schrijf het binnenkomende bericht naar alle clients!
								for (PrintWriter printer : allClients) {
									printer.println(message);
									printer.flush();
								}
							}
						} catch (IOException ioe) {
							ioe.printStackTrace();
						} finally {
							System.out.println("Client-verbinding verbroken!");
							allClients.remove(pw);
						}
					}).start();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}