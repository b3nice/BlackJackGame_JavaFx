package be.kdg.view.Game;

import be.kdg.model.BlackJackModel;
import be.kdg.view.Start.BlackJackStartPresenter;
import be.kdg.view.Start.BlackJackStartView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class BlackJackGamePresenter {
    private BlackJackModel model;
    private BlackJackGameView view;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getButtonExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BlackJackModel newModel = new BlackJackModel();
                BlackJackStartView viewStart = new BlackJackStartView();
                BlackJackStartPresenter presenterGame = new BlackJackStartPresenter(viewStart, newModel);
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
                if (model.getBet() == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "You cannot bet nothing, please bet an amount.");
                    alert.showAndWait();
                } else {
                    view.gethBoxBetAmounts().setVisible(false);
                    model.startGame();

                    ImageViewSetter imageViewSetter = new ImageViewSetter(view.getImageViewPlayerCards());
                    imageViewSetter.setImage(0, new Image(String.valueOf(model.getFirstCardPlayer())));
                    imageViewSetter.setImage(1, new Image(String.valueOf(model.getSecondCardPlayer())));
                    System.out.println(model.getSecondCardPlayer());

                    ImageViewSetter imageViewSetter1 = new ImageViewSetter(view.getImageViewDealerCards());
                    imageViewSetter1.setImage(0, new Image("/RedCardBack.PNG"));
                    imageViewSetter1.setImage(1, new Image(String.valueOf(model.getDealerCards().get(0))));
                }
            }
        });
    }

    private void updateView() {
        view.getLabelPlayerName().setText(model.getName());
    }
}