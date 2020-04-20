package nl.appelgebakje22.bep.prc2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.function.Supplier;

public final class Server {

	public static void main(String[] args) {
		//Create server
		Supplier<ServerSocket> serverSupplier = () -> {
			try {
				return new ServerSocket(3050);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		};
		final ServerSocket server = serverSupplier.get();
		if (server == null) {
			Runtime.getRuntime().halt(1);
		}

		//Create stdin listener thread
		new Thread(() -> {
			while (true) {
				Scanner scanner = new Scanner(System.in);
				String command = scanner.nextLine();
				if (command.equalsIgnoreCase("quit")) {
					System.err.println("Quit command received!");
					try {
						server.close();
					} catch (IOException ignored) {
						//NOOP
					}
					break;
				}
			}
		}, "Input listener").start();

		//Open server
		while (true) {
			try {
				new MyServlet(server.accept()).start();
			} catch (IOException e) {
				//We don't care about errors caused by closing the server in the "Input Listener" thread
				if (e.getMessage().equalsIgnoreCase("Socket closed"))
					break;
				e.printStackTrace();
			}
		}
	}
}