package be.kdg.view.name;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Player;
import be.kdg.view.leaderboard.LeaderBoardPresenter;
import be.kdg.view.game.BlackJackGamePresenter;
import be.kdg.view.game.BlackJackGameView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BlackJackNamePresenter {

    private final BlackJackModel model;
    private final BlackJackNameView view;
    private final Stage primaryStage;
    private int selectedValueComboBox;
    private final LeaderBoardPresenter leaderboard;

    /**
     * Constructor for the BlackJackNamePresenter
     * @param view The view of the BlackJackNamePresenter
     * @param primaryStage The stage of the BlackJackNamePresenter
     */
    public BlackJackNamePresenter(BlackJackNameView view, Stage primaryStage) {
        this.view = view;
        model = new BlackJackModel();
        this.primaryStage = primaryStage;
        this.selectedValueComboBox = 1;
        this.leaderboard = new LeaderBoardPresenter();
        this.addEventHandlers();
    }

    /**
     * Method to add event handlers to the view
     */
    private void addEventHandlers() {

        view.getButtonNext().setOnAction(actionEvent -> {
            ArrayList<String> nameInFields = new ArrayList<>();
            int counterNameValues = 0;

            for(int i = 0; selectedValueComboBox > i; i++){
                nameInFields.add(view.getTextFields()[i].getText());
            }

            for (String nameInArray:nameInFields) {
                if (!nameInArray.isEmpty()){
                    counterNameValues++;
                }
            }
            int playerIndex = 0;
            if (counterNameValues != nameInFields.size()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your username in the textfield.");
                alert.showAndWait();
            } else {
                for (String nameInArray:nameInFields) {
                    model.makePlayers(nameInArray, 1000, playerIndex);
                    playerIndex++;
                }

                checkPlayerBalance();

                BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
                BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame,model,viewGame.getImageViewMakerAndEditor(), primaryStage);

                view.getScene().setRoot(viewGame);
            }
        });
        view.getComboBox().setOnAction(e -> {
            selectedValueComboBox = Integer.parseInt(view.getComboBox().getValue().split(" ")[0]);
            for (int i = 0; i < view.getTextFields().length; i++) {
                view.getTextFields()[i].setVisible(i < selectedValueComboBox);
            }
        });
        primaryStage.setOnCloseRequest(event -> leaderboard.showLeaderBoard());

    }

    /**
     * Method to check the balance of the players and set the balance of the players.
     */
    public void checkPlayerBalance(){
        String fileName = "src/main/resources/playerLog.txt";
        int lineCount = 0;
        ArrayList<String> arrayListNames = new ArrayList<>();

        String lineFileTxt;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((lineFileTxt = br.readLine()) != null) {
                arrayListNames.add(lineFileTxt);
                lineCount++;
            }
            lineCount--;
        } catch (IOException e) {
            e.printStackTrace();
        }

        int indexInArrayList = 0;
        if (lineCount > 0){
            for (int i = 0; i <= lineCount; i++) {
                String valueArrayList = arrayListNames.get(indexInArrayList);
                indexInArrayList++;
                char charComma = ',';
                char charSemikolon = ';';
                int commaIndex = valueArrayList.indexOf(charComma);
                int spaceIndex = valueArrayList.indexOf(charSemikolon);
                ArrayList<String> names = new ArrayList<>();
                names.add(valueArrayList.substring(0, commaIndex));
                for (Player player : model.getPlayers()) {
                    for (String name : names) {
                        if (name.equals(player.getName())) {
                            String balance = valueArrayList.substring(commaIndex + 1, spaceIndex);
                            player.setBalance(Integer.parseInt(balance));
                        }
                    }
                }
            }
        }
    }
}
