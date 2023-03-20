package be.kdg.view.name;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Player;
import be.kdg.view.game.BlackJackGamePresenter;
import be.kdg.view.game.BlackJackGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private final int selectedValueComboBox;
    private final ArrayList<Player> players;

    public BlackJackNamePresenter(BlackJackNameView view, Stage primaryStage) {
        this.view = view;
        players = new ArrayList<Player>();
        model = new BlackJackModel();
        this.primaryStage = primaryStage;
        this.selectedValueComboBox = 1;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers() {

        view.getButtonNext().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> nameInFields = new ArrayList<String>();
                int counterNameValues = 0;

                for (int i = 0; selectedValueComboBox > i; i++) {
                    nameInFields.add(view.getTextFields()[i].getText());
                }

                for (String nameInArray : nameInFields) {
                    if (!nameInArray.isEmpty()) {
                        counterNameValues++;
                    }
                }
                int playerIndex = 0;
                if (counterNameValues != nameInFields.size()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your username in the textfield.");
                    alert.showAndWait();
                } else {
                    for (String nameInArray : nameInFields) {
                        players.add(new Player(nameInArray, 200, playerIndex));
                    }

                    checkPlayerBalance();

                    BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
                    BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model,viewGame.getImageViewMakerAndEditor(), primaryStage, players);

                    view.getScene().setRoot(viewGame);
                }
            }
        });
        view.comboBox.setOnAction(e -> {
            int selectedValue = Integer.parseInt(view.getComboBox().getValue().split(" ")[0]);
            for (int i = 0; i < view.getTextFields().length; i++) {
                view.getTextFields()[i].setVisible(i < selectedValue);
            }
        });

    }

    public void checkPlayerBalance() {
        String fileName = "src/main/resources/player.txt";
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
        if (lineCount >= 0) {
            for (int i = 0; i <= lineCount; i++) {
                String valueArrayList = arrayListNames.get(indexInArrayList);
                indexInArrayList++;

                int spaceIndex = valueArrayList.indexOf(',');
                ArrayList<String> names = new ArrayList<>();
                System.out.println(names);
                System.out.println(spaceIndex);
                names.add(valueArrayList.substring(0, spaceIndex));
                System.out.println(players.size());

                for (Player player : players) {
                    for (String name : names) {
                        if (name.equals(player.getName())) {
                            String balance = valueArrayList.substring(spaceIndex + 1);
                            player.setBalance(Integer.parseInt(balance));
                        }
                    }
                }
            }
        }
    }

    private void updateView() {

    }
}
