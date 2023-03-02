package be.kdg.view.Start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class BlackJackStartView extends BorderPane {

    Label labelWelcome;
    Button buttonStartGame;
    private BackgroundImage logo;


    public Button getButtonStartGame() {
        return buttonStartGame;
    }

    public BlackJackStartView(){
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes(){
        labelWelcome = new Label("Welcome to Blackjack");
        buttonStartGame = new Button("Start");

        buttonStartGame.setStyle("-fx-font-size: 20px;");
        labelWelcome.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
    }

    private void layoutNodes(){

        labelWelcome.setFont(new Font(50));
        this.setCenter(buttonStartGame);
        this.setTop(labelWelcome);
        BorderPane.setMargin(labelWelcome,new Insets(100,0,0,0));

        BorderPane.setAlignment(labelWelcome, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(buttonStartGame, Pos.CENTER);

    }
}







