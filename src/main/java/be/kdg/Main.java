package be.kdg;

import be.kdg.view.Start.BlackJackStartPresenter;
import be.kdg.view.Start.BlackJackStartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        BlackJackStartView view = new BlackJackStartView();
        BlackJackStartPresenter presenter = new BlackJackStartPresenter(view);

        Scene scene = new Scene(view);

        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);

        final double ASPECT_RATIO = 16.0 / 9.0;

        primaryStage.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            primaryStage.setHeight(newWidth.doubleValue() / ASPECT_RATIO);
        });

        primaryStage.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
            primaryStage.setWidth(newHeight.doubleValue() * ASPECT_RATIO);
        });



        primaryStage.setTitle("BlackJack21");


        Image iconImage = new Image("/Club_cards.jpg");
        primaryStage.getIcons().add(iconImage);

        primaryStage.show();
    }
}
