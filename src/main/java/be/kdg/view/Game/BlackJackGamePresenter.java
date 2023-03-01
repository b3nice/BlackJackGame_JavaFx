package be.kdg.view.Game;

import be.kdg.model.BlackJackModel;
import be.kdg.view.Start.BlackJackStartPresenter;
import be.kdg.view.Start.BlackJackStartView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlackJackGamePresenter {
    private BlackJackModel model;
    private BlackJackGameView view;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers(){
        view.getButtonExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BlackJackStartView viewStart = new BlackJackStartView();
                BlackJackStartPresenter presenterGame  = new BlackJackStartPresenter(viewStart,model);
                view.getScene().setRoot(viewStart);
            }
        });
        view.getButtonBet5().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 5;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet10().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 10;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet20().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 20;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet50().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 50;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBetClear().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int value = 0;
                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (model.getBet() == 0){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "You cannot bet nothing, please bet an amount.");
                        alert.showAndWait();
                }
                else{
                    view.gethBoxBetAmounts().setVisible(false);
                    model.startGame();

                    ImageView[] imageViewPlayerCards = new ImageView[5];
                    for (int i = 0; i < 5; i++) {
                        if (i  < 1){
                            imageViewPlayerCards[0] = new ImageView(new Image(String.valueOf(model.getFirstCardPlayer())));
                            imageViewPlayerCards[1] = new ImageView(new Image(String.valueOf(model.getSecondCardPlayer())));
                        }else{
                            imageViewPlayerCards[i] = new ImageView(new Image("/RedCardBack.PNG"));
                        }
                    }

                    view.setImageViewPlayerCards(imageViewPlayerCards);
                }
            }
        });
    }

    private void updateView(){
        view.getLabelPlayerName().setText(model.getName());
    }
}