package be.kdg.view.start;

import be.kdg.view.leaderboard.LeaderBoardPresenter;
import be.kdg.view.name.BlackJackNamePresenter;
import be.kdg.view.name.BlackJackNameView;
import javafx.stage.Stage;

public class BlackJackStartPresenter {

    private final BlackJackStartView view;
    private final Stage primaryStage;
    private final LeaderBoardPresenter leaderboard;

    /**
     * Here we initialise the presenter and add the eventhandlers.
     * @param view this is the view we use to set the root of the scene.
     * @param primaryStage this is the stage we use to set the root of the scene.
     */
    public BlackJackStartPresenter(BlackJackStartView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.leaderboard = new LeaderBoardPresenter();
        this.addEventHandlers();
    }

    /**
     * Here we add the eventhandlers to the buttons.
     */
    private void addEventHandlers(){
        view.getButtonStartGame().setOnAction(actionEvent -> {
            BlackJackNameView viewName = new BlackJackNameView(primaryStage);
            BlackJackNamePresenter presenterGame  = new BlackJackNamePresenter(viewName,primaryStage);
            view.getScene().setRoot(viewName);
        });
        primaryStage.setOnCloseRequest(event -> leaderboard.showLeaderBoard());
    }
}
