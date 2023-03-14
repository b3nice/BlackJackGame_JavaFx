package be.kdg.view.Name;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BlackJackNameView extends GridPane {

    TextField textFieldName;
    Button buttonNext;

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public Button getButtonNext() {
        return buttonNext;
    }


    public BlackJackNameView(){
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

        this.add(textFieldName,2,2);
        this.add(buttonNext,2,4);

        this.setPadding(new Insets(50, 0, 50, 0));

        GridPane.setHalignment(textFieldName, HPos.CENTER);
        GridPane.setHalignment(buttonNext, HPos.CENTER);

        Platform.runLater(() -> buttonNext.requestFocus());
        this.setFocusTraversable(false);
    }
}
