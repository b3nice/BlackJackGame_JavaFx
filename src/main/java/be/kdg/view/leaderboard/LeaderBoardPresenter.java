package be.kdg.view.leaderboard;

import be.kdg.model.Player;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeaderBoardPresenter {
    private final ArrayList<Player> arrayListPlayers;
    private final LeaderboardView leaderboardView;

    public LeaderBoardPresenter() {
        this.arrayListPlayers = new ArrayList<>();
        this.leaderboardView = new LeaderboardView();
    }

    public void showLeaderBoard(){
        calculateLeaderBoard();
        Stage stage = new Stage();
        Scene scene = new Scene(leaderboardView);
        scene.getStylesheets().add("/Styles.css");
        stage.setTitle("LeaderBoard");
        Image iconImage = new Image("/Club_cards.jpg");
        stage.getIcons().add(iconImage);
        stage.initModality(Modality.APPLICATION_MODAL);

        StringBuilder leaderboardText = new StringBuilder();
        leaderboardText.append("[#]|Name\t Balance|\n");
        int index = 1;
        for (Player player: arrayListPlayers) {
            int space = 20 - player.getName().length();
            leaderboardText.append(String.format("[" + index + "] " + player.getName() + "% " + space + "d" + "\n", player.getBalance()));
            index++;
        }

        leaderboardView.getLabelPlayerList().setText(leaderboardText.toString());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(300);
        stage.setHeight(450);
        stage.show();
    }

    public void calculateLeaderBoard(){
        String fileName = "src/main/resources/player.txt";
        String lineFileTxt;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((lineFileTxt = br.readLine()) != null) {
                arrayListPlayers.add(new Player(lineFileTxt.substring(0, lineFileTxt.indexOf(',')), Integer.parseInt(lineFileTxt.substring(lineFileTxt.indexOf(',') + 1)),0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayListPlayers.sort((player1, player2) -> player2.getBalance() - player1.getBalance());
    }
}
