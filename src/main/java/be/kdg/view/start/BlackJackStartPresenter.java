package be.kdg.view.start;

import be.kdg.view.Leaderboard.LeaderBoardPresenter;
import be.kdg.view.name.BlackJackNamePresenter;
import be.kdg.view.name.BlackJackNameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class BlackJackStartPresenter {

    private final BlackJackStartView view;
    private final Stage primaryStage;
    private final LeaderBoardPresenter leaderboard;

    public BlackJackStartPresenter(BlackJackStartView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.leaderboard = new LeaderBoardPresenter();
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers(){
        view.getButtonStartGame().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BlackJackNameView viewName = new BlackJackNameView(primaryStage);
                BlackJackNamePresenter presenterGame  = new BlackJackNamePresenter(viewName,primaryStage);
                view.getScene().setRoot(viewName);
            }
        });
        primaryStage.setOnCloseRequest(event -> {
            leaderboard.showLeaderBoard();
        });
    }
    private void updateView(){
    }
}
