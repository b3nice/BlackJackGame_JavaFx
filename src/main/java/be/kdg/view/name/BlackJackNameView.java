package be.kdg.view.name;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BlackJackNameView extends GridPane {

    private final Stage primaryStage;
    private TextField[] textFields;
    private Button buttonNext;
    private ComboBox<String> comboBox;
    private VBox vBoxTextFields;



    public BlackJackNameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.initialiseNodes();
        this.layoutNodes();
        this.resizeNodes();
    }

    private void initialiseNodes() {
        vBoxTextFields = new VBox();
        ObservableList<String> items = FXCollections.observableArrayList(
                "1 Player",
                "2 Players",
                "3 Players",
                "4 Players",
                "5 Players"
        );
        comboBox = new ComboBox<String>(items);
        comboBox.setValue("1 Player");
        textFields = new TextField[5];
        String promptText = "Enter your username here";
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new TextField();
            textFields[i].setPromptText(promptText);
            textFields[i].getStyleClass().add("textField");
            vBoxTextFields.getChildren().add(textFields[i]);
            if (i != 0){
                textFields[i].setVisible(false);
            }

        }
        buttonNext = new Button("Next");
        buttonNext.getStyleClass().add("button");
        this.getStyleClass().add("backGround_name");
    }

    private void layoutNodes() {
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
        this.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

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


        this.add(comboBox, 1, 1);
        this.add(vBoxTextFields, 2, 2);
        this.add(buttonNext, 2, 4);

        for (TextField textField : textFields) {
            GridPane.setHalignment(textField, HPos.CENTER);
        }
        vBoxTextFields.setAlignment(Pos.CENTER);


        this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.prefHeightProperty().bind(primaryStage.heightProperty());

        GridPane.setHalignment(vBoxTextFields, HPos.CENTER);
        GridPane.setHalignment(buttonNext, HPos.CENTER);
        GridPane.setHalignment(comboBox, HPos.RIGHT);
        GridPane.setValignment(comboBox, VPos.BOTTOM);
        GridPane.setMargin(comboBox, new javafx.geometry.Insets(0, 0, 13, 0));

        Platform.runLater(() -> buttonNext.requestFocus());
        this.setFocusTraversable(false);

    }

    public void resizeNodes(){
        comboBox.setMinSize(primaryStage.widthProperty().doubleValue() * 0.1, primaryStage.widthProperty().doubleValue() * 0.03);
        for (TextField textField : textFields) {
            textField.setFont(Font.font(primaryStage.widthProperty().doubleValue() * 0.015));
            textField.setMinSize(primaryStage.widthProperty().doubleValue() * 0.25, primaryStage.widthProperty().doubleValue() * 0.04);
        }

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            comboBox.setMinSize(newValue.doubleValue() * 0.1, newValue.doubleValue() * 0.03);
            buttonNext.setFont(Font.font(newValue.doubleValue() * 0.03));
            buttonNext.setMinSize(newValue.doubleValue() * 0.1, newValue.doubleValue() * 0.03);
        });

        for (TextField textField : textFields) {
            primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
                textField.setFont(Font.font(newValue.doubleValue() * 0.015));
                textField.setMinSize(newValue.doubleValue() * 0.25, newValue.doubleValue() * 0.04);
            });
        }
    }


    public ComboBox<String> getComboBox() {
        return comboBox;
    }
    public TextField[] getTextFields() {
        return textFields;
    }
    public Button getButtonNext() {
        return buttonNext;
    }
}
