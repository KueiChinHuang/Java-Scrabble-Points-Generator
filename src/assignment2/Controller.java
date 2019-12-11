package assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    @FXML
    Label point;
    @FXML
    TextField new_word;
    @FXML
    Label previous_words;
    @FXML
    Label warning;
    @FXML
    GridPane gridPane;
    @FXML Label game_over;
    @FXML Label game_over_annotation;

    Model scrabbleModel;

    @FXML
    private void letters_clicked (ActionEvent event){

        Button button = (Button)(event.getSource());
        String buttonValue = button.getText();
        new_word.setText(new_word.getText() + buttonValue);
    }

    @FXML
    private void submit(ActionEvent event){

        AtomicInteger gameOverCounter = new AtomicInteger();
        AtomicInteger noVowelCounter = new AtomicInteger();
        String newWordString = new_word.getText().toUpperCase();

        // If it's a valid word
        if(scrabbleModel.setNewWord(newWordString)){
            // don't show the warning
            warning.setVisible(false);
            // put the new word into collection previous_words
            previous_words.setText(scrabbleModel.getPreviousWords());
            // update the total points
            point.setText(String.valueOf(scrabbleModel.setTotalPoints(newWordString)));


            // print how many characters left for this game
            scrabbleModel.getReserveLetter().entrySet().forEach(characterIntegerEntry -> {

                if ((characterIntegerEntry.getKey() == 'A' ||
                        characterIntegerEntry.getKey() == 'E' ||
                        characterIntegerEntry.getKey() == 'I' ||
                        characterIntegerEntry.getKey() == 'O' ||
                        characterIntegerEntry.getKey() == 'U' ||
                        characterIntegerEntry.getKey() == 'Y' )
                        && characterIntegerEntry.getValue() == 0){
                    noVowelCounter.getAndIncrement();
                }

                if (characterIntegerEntry.getValue() != 0){
                    System.out.print(characterIntegerEntry.getKey() + "_" + characterIntegerEntry.getValue() + " / ");
                }
                else {
                    gameOverCounter.getAndIncrement();
                }

                for (Node node : gridPane.getChildren()) {
                    if (node instanceof Button) {
                        final Button button = (Button) node;
                        String buttonValue = button.getText();
                        if (buttonValue.equals(String.valueOf(characterIntegerEntry.getKey())) &&
                                characterIntegerEntry.getValue() == 0){
                            button.setDisable(true);
                        }
                    }
                }
            });

            if (noVowelCounter.intValue() == 6 || gameOverCounter.intValue() >= 25){
                game_over.setVisible(true);
                if(noVowelCounter.intValue() == 6)
                    game_over_annotation.setText("No more vowel");
                if(gameOverCounter.intValue() >= 25)
                    game_over_annotation.setText("No more word possibility");
                game_over_annotation.setVisible(true);
                point.setTextFill(Color.CORAL);
                point.setFont(Font.font(null, FontWeight.BOLD, 34));
            }

            System.out.println(gameOverCounter + " letters has run out.");
            System.out.println();
        }
        // If it's NOT a valid word
        else{
            // set the warning text according the situation
            warning.setText(scrabbleModel.getWarning());
            // show the warning
            warning.setVisible(true);
            System.out.println(newWordString + " is not a valid word!");
            System.out.println();
        }

        // initialize the textfield for user
        new_word.setText("");
    }

    @FXML
    private void clear(ActionEvent event){
        // when user typed something wrong, they can clear the textfield
        new_word.setText("");
    }

    @FXML
    private void initialize(){
        scrabbleModel = new Model();
        System.out.println();
        System.out.println("Successfully start a new game!");
        previous_words.setText("Previous Words: ");
        point.setText("0");
        new_word.setText("");
        warning.setVisible(false);
        game_over.setVisible(false);
        game_over_annotation.setVisible(false);
        point.setTextFill(Color.BLACK);
        point.setFont(Font.font(null, FontWeight.NORMAL, 34));

        scrabbleModel.getReserveLetter().entrySet().forEach(characterIntegerEntry -> {
            for (Node node : gridPane.getChildren()) {
                if (node instanceof Button) {
                    final Button button = (Button) node;
                    button.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void try_again(){
        initialize();
    }
}
