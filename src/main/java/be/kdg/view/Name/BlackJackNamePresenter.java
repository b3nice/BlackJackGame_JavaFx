package be.kdg.view.Name;

import be.kdg.model.BlackJackModel;
import be.kdg.view.Game.BlackJackGamePresenter;
import be.kdg.view.Game.BlackJackGameView;
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



    public BlackJackNamePresenter(BlackJackNameView view, BlackJackModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers(){

        view.getButtonNext().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nameInField = view.getTextFieldName().getText();

                if (nameInField.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your username in the textfield.");
                    alert.showAndWait();
                } else {
                    model.setName(nameInField);

                    checkPlayerBalance();

                    BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
                    BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model, viewGame.getImageViewMakerAndEditor(), primaryStage);

                    view.getScene().setRoot(viewGame);
                }
            }
        });
        
    }
    
    public void checkPlayerBalance(){
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
        if (lineCount >= 0){
            for (int i = 0; i <= lineCount; i++) {
                String valueArrayList = arrayListNames.get(indexInArrayList);
                indexInArrayList++;

                int spaceIndex = valueArrayList.indexOf(',');
                String name = valueArrayList.substring(0, spaceIndex);

                if (name.equals(model.getName())){
                    String balance = valueArrayList.substring(spaceIndex + 1);
                    model.setBalance(Integer.parseInt(balance));
                }
            }
        }
    }
    
    private void updateView(){

    }
}
