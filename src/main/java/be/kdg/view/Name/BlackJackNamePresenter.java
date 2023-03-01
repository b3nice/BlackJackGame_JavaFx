package be.kdg.view.Name;

import be.kdg.model.BlackJackModel;
import be.kdg.view.Game.BlackJackGamePresenter;
import be.kdg.view.Game.BlackJackGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class BlackJackNamePresenter {

    private BlackJackModel model;
    private BlackJackNameView view;


    public BlackJackNamePresenter(BlackJackNameView view, BlackJackModel model) {
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
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your username in the textfield.");
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
