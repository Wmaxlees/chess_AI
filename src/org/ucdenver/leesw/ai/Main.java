package org.ucdenver.leesw.ai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Chess AI");
        primaryStage.setScene(new Scene(root, 1205, 880));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
