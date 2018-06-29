package main;

import java.io.File;

import database.DatabaseManager;
import helpercomponents.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import organizationalunits.CommiteeType;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

//		Scene scene = new Scene(FXMLLoader.load(new File("Views/dptTabStimulusType.fxml").toURI().toURL()));
		Scene scene = new Scene(FXMLLoader.load(new File(Views.MAINSCREEN).toURI().toURL()));
		DatabaseManager.getInstance().getEmf();
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		DecisionProcessManager.getInstance().getEmf();
	}
	
	public static void main(String[] args){
		
		launch();
	}
}
