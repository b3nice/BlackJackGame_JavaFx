package be.kdg;

import be.kdg.view.start.BlackJackStartPresenter;
import be.kdg.view.start.BlackJackStartView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("BlackJack21");

        BlackJackStartView view = new BlackJackStartView(primaryStage);
        BlackJackStartPresenter presenter = new BlackJackStartPresenter(view, primaryStage);

        Scene scene = new Scene(view);
        scene.getStylesheets().add("/Styles.css");

        primaryStage.setScene(scene);

        primaryStage.sizeToScene();

        Image iconImage = new Image("/Club_cards.jpg");
        primaryStage.getIcons().add(iconImage);

        primaryStage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double taskbarHeight = screenBounds.getHeight() - Screen.getPrimary().getVisualBounds().getMaxY();

        primaryStage.setMaxWidth(screenBounds.getWidth());
        primaryStage.setMaxHeight(screenBounds.getHeight() - taskbarHeight);

        primaryStage.setMaximized(true);

        primaryStage.setMinWidth(primaryStage.getMaxWidth());
        primaryStage.setMinHeight(primaryStage.getMaxHeight());

        final double ASPECT_RATIO = 16.0 / 9.0;
        primaryStage.widthProperty().addListener((observableValue, oldWidth, newWidth) -> primaryStage.setHeight(newWidth.doubleValue() / ASPECT_RATIO));
        primaryStage.heightProperty().addListener((observableValue, oldHeight, newHeight) -> primaryStage.setWidth(newHeight.doubleValue() * ASPECT_RATIO));
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((bounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((bounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
