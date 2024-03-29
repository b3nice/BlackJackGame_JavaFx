package be.kdg.view.start;

import be.kdg.model.BlackJackModel;
import be.kdg.view.name.BlackJackNamePresenter;
import be.kdg.view.name.BlackJackNameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class BlackJackStartPresenter {

    private BlackJackModel model = new BlackJackModel();
    private final BlackJackStartView view;
    private final Stage primaryStage;


    public BlackJackStartPresenter(BlackJackStartView view, BlackJackModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.addEventHandlers();
        this.updateView();
    }
    public BlackJackStartPresenter(BlackJackStartView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers(){
        view.getButtonStartGame().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BlackJackNameView viewName = new BlackJackNameView(primaryStage);
                BlackJackNamePresenter presenterGame  = new BlackJackNamePresenter(viewName, model, primaryStage);
                view.getScene().setRoot(viewName);
            }
        });
    }
    private void updateView(){
    }
}
