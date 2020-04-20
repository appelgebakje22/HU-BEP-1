package nl.appelgebakje22.bep.prc_2_3.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.appelgebakje22.bep.prc_2_3.server.ChatServer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatAppController {

	@FXML
	private TextField chatField;
	@FXML
	private TextArea chatBox;
	@FXML
	private Button submit;
	@FXML
	private TextField chatName;

	private final Socket connection;
	private final PrintWriter serverWriter;

	public ChatAppController() throws IOException {
		this.connection = new Socket("127.0.0.1", ChatServer.PORT);
		this.serverWriter = new PrintWriter(new OutputStreamWriter(this.connection.getOutputStream()));
		new MessageListener().start();
	}

	@FXML
	private void sendMessage() {
		String username = this.chatName.getText();
		if (username == null || username.isBlank()) {
			this.chatBox.setText("Invalid username!");
			return;
		} else {
			//Only execute this when we are validating the username
			if (this.chatName.isEditable()) {
				this.chatName.setEditable(false);
			}
		}
		String msg = this.chatField.getText();
		if (msg == null || msg.isBlank())
			return;
		this.serverWriter.println(username + " : " + msg);
		this.serverWriter.flush();
		this.chatField.setText(null);
	}

	private class MessageListener extends Thread {
		public void run() {
			try (Scanner scanner = new Scanner(ChatAppController.this.connection.getInputStream())) {
				while (scanner.hasNext()) {
					ChatAppController.this.chatBox.appendText(scanner.nextLine() + "\n");
				}
			} catch (IOException e) {
				ChatAppController.this.chatBox.appendText("AN ERROR OCCURRED!");
			}
		}
	}

	public void closeConnection()  {
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}