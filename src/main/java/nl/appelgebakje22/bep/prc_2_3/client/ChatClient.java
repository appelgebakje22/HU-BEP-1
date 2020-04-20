package nl.appelgebakje22.bep.prc_2_3.client;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatClient extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		String fxmlPagina = "/nl.appelgebakje22.bep.prc_2_3.client/ChatApp.fxml";
		URL input = ChatClient.class.getResource(fxmlPagina);
		if (input == null)
			throw new NullPointerException("Cannot load FXML File: " + fxmlPagina);
		FXMLLoader loader = new FXMLLoader(input);
		Parent root = loader.load();
		ChatAppController controller = loader.getController();

		stage.setOnCloseRequest(e -> controller.closeConnection());
		stage.setTitle("ChatApplication 1.0");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
