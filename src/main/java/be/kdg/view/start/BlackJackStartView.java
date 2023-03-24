package be.kdg.view.start;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BlackJackStartView extends GridPane {

    private Label labelWelcome;
    private Button buttonStartGame;
    private final Stage primaryStage;


    public BlackJackStartView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Here we initialise the nodes and do a bit of styling. No extra
     **/
    private void initialiseNodes(){
        labelWelcome = new Label("Welcome to Blackjack");
        buttonStartGame = new Button("Start");

        labelWelcome.getStyleClass().add("label");
        buttonStartGame.getStyleClass().add("button");

        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        labelWelcome.setFont(new Font(50));
        labelWelcome.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonStartGame.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        fontSize.bind(primaryStage.widthProperty().multiply(0.015));

        this.getStyleClass().add("backGround_start");
    }

    /**
     * Here we layout the nodes in the gridpane.
     **/
    private void layoutNodes(){
        //This is to section the gridpane in 5x5.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(20);
        this.getColumnConstraints().addAll(col1, col2, col3,col4,col5);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(20);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(20);
        this.getRowConstraints().addAll(row1, row2, row3, row4, row5);
        this.setOnMouseClicked(event -> this.requestFocus());


        this.add(buttonStartGame,2,3);
        this.add(labelWelcome,2,1);

        buttonStartGame.setPrefWidth(200);

        GridPane.setHalignment(labelWelcome, HPos.CENTER);
        GridPane.setHalignment(buttonStartGame, HPos.CENTER);
        GridPane.setValignment(buttonStartGame, VPos.TOP);

        BorderPane.setMargin(labelWelcome,new Insets(100,0,0,0));

        BorderPane.setAlignment(labelWelcome, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(buttonStartGame, Pos.CENTER);
    }
    public Button getButtonStartGame() {
        return buttonStartGame;
    }
}







