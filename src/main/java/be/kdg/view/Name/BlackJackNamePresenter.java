package be.kdg.view.Name;

import be.kdg.model.Application;
import be.kdg.view.Game.BlackJackGamePresenter;
import be.kdg.view.Game.BlackJackGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BlackJackNamePresenter {

    private Application model;
    private BlackJackNameView view;


    public BlackJackNamePresenter(BlackJackNameView view, Application model) {
        this.view = view;
        this.model = model;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers(){

        view.getButtonNext().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nameInField = view.getTextFieldName().getText();

                if (nameInField.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a value in the textfield.");
                    alert.showAndWait();
                } else {
                    model.setName(nameInField);

                    BlackJackGameView viewGame = new BlackJackGameView();
                    BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model);

                    view.getScene().setRoot(viewGame);
                }
            }
        });

    }
    private void updateView(){

    }
}
