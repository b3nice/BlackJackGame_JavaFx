package be.kdg.view.Start;

import be.kdg.model.Application;
import be.kdg.view.Name.BlackJackNamePresenter;
import be.kdg.view.Name.BlackJackNameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BlackJackStartPresenter {

    private Application model = new Application();
    private BlackJackStartView view;

    public BlackJackStartPresenter(BlackJackStartView view, Application model) {
        this.view = view;
        this.model = model;
        this.addEventHandlers();
        this.updateView();
    }
    public BlackJackStartPresenter(BlackJackStartView view) {
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers(){
        view.getButtonStartGame().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BlackJackNameView viewName = new BlackJackNameView();
                BlackJackNamePresenter presenterGame  = new BlackJackNamePresenter(viewName, model);
                view.getScene().setRoot(viewName);
            }
        });
    }
    private void updateView(){
    }
}
