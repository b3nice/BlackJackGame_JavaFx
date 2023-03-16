package be.kdg.view.Name;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BlackJackNameView extends GridPane {

    Stage primaryStage;
    TextField textFieldName;
    Button buttonNext;

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public Button getButtonNext() {
        return buttonNext;
    }


    public BlackJackNameView(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes(){
        textFieldName = new TextField();
        textFieldName.setPromptText("Enter your username here");
        buttonNext = new Button("Next");

        textFieldName.getStyleClass().add("textField");
        buttonNext.getStyleClass().add("button");

        this.getStyleClass().add("backGround_name");
    }

    private void layoutNodes(){
        this.setOnMouseClicked(event -> this.requestFocus());


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(15);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(15);
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

        textFieldName.setMinWidth(300);

        this.add(textFieldName,2,2);
        this.add(buttonNext,2,4);

        this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.prefHeightProperty().bind(primaryStage.heightProperty());

        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        buttonNext.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        textFieldName.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()/1.5), fontSize.divide(1.5)));
        fontSize.bind(primaryStage.widthProperty().multiply(0.015));

        GridPane.setHalignment(textFieldName, HPos.CENTER);
        GridPane.setHalignment(buttonNext, HPos.CENTER);

        Platform.runLater(() -> buttonNext.requestFocus());
        this.setFocusTraversable(false);
        //this.setGridLinesVisible(true);
    }
}
