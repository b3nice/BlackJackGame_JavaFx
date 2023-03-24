package be.kdg.view.Leaderboard;


import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LeaderboardView extends GridPane {

    private Label labelLeaderboard;
    private Label labelPlayerList;
    public LeaderboardView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes(){
        labelLeaderboard = new Label("TOP PLAYERS");
        labelPlayerList = new Label(" ");
        labelLeaderboard.getStyleClass().add("label_leaderboard");
        labelPlayerList.getStyleClass().add("label_leaderboard");
        labelPlayerList.setWrapText(true);

        this.getStyleClass().add("backGround_leaderboard");
    }

    private void layoutNodes(){
        this.add(labelLeaderboard,0,0);
        this.add(labelPlayerList,0,1);
        this.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(labelLeaderboard, HPos.CENTER);
        GridPane.setHalignment(labelPlayerList, HPos.CENTER);
    }

    public Label getLabelPlayerList() {
        return labelPlayerList;
    }
}
