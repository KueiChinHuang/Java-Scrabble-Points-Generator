package assignment2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;

public class Model {

    private String newWord;
    private HashMap<Character, Integer> reserveLetter = new HashMap<Character, Integer>();
    private String previousWords = "Previous Words: ";
    private int totalPoints;
    private String warning;

    /**
     * Constructor of Model
     */
    public Model() {

        reserveLetter.put('E', 12);
        reserveLetter.put('A', 9);
        reserveLetter.put('R', 6);
        reserveLetter.put('O', 8);
        reserveLetter.put('I', 8);
        reserveLetter.put('T', 6);
        reserveLetter.put('S', 4);
        reserveLetter.put('N', 6);
        reserveLetter.put('L', 4);
        reserveLetter.put('D', 4);
        reserveLetter.put('U', 4);
        reserveLetter.put('G', 3);
        reserveLetter.put('P', 2);
        reserveLetter.put('M', 2);
        reserveLetter.put('B', 2);
        reserveLetter.put('H', 2);
        reserveLetter.put('C', 2);
        reserveLetter.put('W', 2);
        reserveLetter.put('Y', 2);
        reserveLetter.put('F', 2);
        reserveLetter.put('V', 2);
        reserveLetter.put('K', 1);
        reserveLetter.put('X', 1);
        reserveLetter.put('Z', 1);
        reserveLetter.put('J', 1);
        reserveLetter.put('Q', 1);
    }

    public HashMap<Character, Integer> getReserveLetter() {
        return reserveLetter;
    }

    public boolean setNewWord(String newWord) {

        // if this word is a valid word
        if(isValidWord(newWord)) {
            // set it to this.newWord
            this.newWord = newWord;
            // and put this word into our previous words list
            setPreviousWords();
            // then return true
            return true;
        }
        // if this word is NOT a valid word
        else {
            return false;
        }
    }

    private boolean isValidWord(String newWord){

        // set up the flag for word validation
        boolean isValid = true;

        // if we meet these conditions, change flag to false
        if (newWord.toCharArray().length>8 ||
                newWord.toCharArray().length <= 1 ||
                reserveEnough(newWord) == false ||
                validVowel(newWord) == false ||
                noDuplicate(newWord) == false) {
            isValid = false;
        }

        // set up warning according to the condition
        if (newWord.toCharArray().length>8) {
            warning = "Word is too long (more than 8 characters)";
        }else if (newWord.toCharArray().length == 1) {
            warning = "Word is too short (only 1 character)";
        } else if (newWord.toCharArray().length <= 0) {
            warning = "Word is blank";
        } else if (reserveEnough(newWord) == false) {
            warning = "Word contains letter that is no longer available “in bag”";
        } else if (validVowel(newWord) == false) {
            warning = "Word does not include vowel";
        }else if (noDuplicate(newWord) == false) {
            warning = "You already typed this word before";
        }

        // if this is a valid word, then return true
        if(isValid == true){
            char arr[] = newWord.toCharArray();
            int num = 0;

            // because we already do all the validations and we ensure this word is a valid word
            // so we can minus this letter by on from our real reserveLetter HashMap
            for (char letter: arr) {
                if (reserveLetter.containsKey(letter)) {
                    num = reserveLetter.get(letter);
                    reserveLetter.put(letter, num-1);
                    System.out.println("Character " + letter + " got " + (num - 1) + " number left.");
                }
            }
            return true;
        }
        // if this is NOT a valid word, then return false
        else{
            return false;
        }

    }

    private boolean noDuplicate(String newWord){

        // set up a flag for this validation
        boolean isValid = true;

        // loop through each word from previous words list
        // we split previousWords by :, and space
        for (String pWord: previousWords.split("[:, ]")){
            // if we already have the same word in our previous list
            if (newWord.equals(pWord)){
                // change our flag to false
                isValid = false;
            }
        }

        // return the flag
        return isValid;
    }

    private boolean validVowel(String newWord){

        // set up a flag for this validation
        // default is false
        boolean isValid = false;

        // loop through each character in this new word
        for(char letter: newWord.toCharArray()){
            // if this new word contain any vowel, change the flag to true
            if (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U' || letter == 'Y'){
                isValid = true;
            }
        }

        // return the flag
        return isValid;
    }

    private boolean reserveEnough(String newWord) {

        // set up a flag for this validation
        boolean isEnough = true;

        // create a new temporary HashMap for reserveLetter
        // because we want to do the counting but we don't want to change the real HashMap data
        HashMap<Character, Integer> rLetter = new HashMap<Character, Integer>();
        rLetter.putAll(reserveLetter);

        char arr[] = newWord.toCharArray();
        int num = 0;

        for (char letter: arr) {
            // if this letter is in our temp HashMap
            if (rLetter.containsKey(letter)){
                // how many of this letter remain in our bag -> store in variable num
                num = rLetter.get(letter);

                // if it would become negative after we minus one
                if (num - 1 < 0) {
                    // it means that we don't have enough of this letter anymore
                    isEnough = false;
                }
                // if it's >= 0 after we minus one
                else {
                    // then let's minus this letter by one from our bag
                    // and our flag remain true
                    rLetter.put(letter, num-1);
                }
            }
        }

        // return our flag
        return isEnough;
    }

    public String getPreviousWords() {
        return previousWords;
    }

    public void setPreviousWords() {

        // if previousWords isn't default anymore, i want to add a comma first (before I add next word)
        if (this.previousWords != "Previous Words: "){
            this.previousWords += ", ";
        }

        // add next word into previousWords, but skip the character that's not a letter
        char arr[] = newWord.toCharArray();
        for (char letter: arr) {
            if (reserveLetter.containsKey(letter)){
                this.previousWords += letter;
            }
        }
    }

    public int setTotalPoints(String newWord) {

        // create a hashmap to store each character's point
        HashMap<Character, Integer> pointOfEachLetter = new HashMap<Character, Integer>();
        pointOfEachLetter.put('A', 1);
        pointOfEachLetter.put('B', 3);
        pointOfEachLetter.put('C', 3);
        pointOfEachLetter.put('D', 2);
        pointOfEachLetter.put('E', 1);
        pointOfEachLetter.put('F', 4);
        pointOfEachLetter.put('G', 2);
        pointOfEachLetter.put('H', 4);
        pointOfEachLetter.put('I', 1);
        pointOfEachLetter.put('J', 8);
        pointOfEachLetter.put('K', 5);
        pointOfEachLetter.put('L', 1);
        pointOfEachLetter.put('M', 3);
        pointOfEachLetter.put('N', 1);
        pointOfEachLetter.put('O', 1);
        pointOfEachLetter.put('P', 3);
        pointOfEachLetter.put('Q', 10);
        pointOfEachLetter.put('R', 1);
        pointOfEachLetter.put('S', 1);
        pointOfEachLetter.put('T', 1);
        pointOfEachLetter.put('U', 1);
        pointOfEachLetter.put('V', 4);
        pointOfEachLetter.put('W', 4);
        pointOfEachLetter.put('X', 8);
        pointOfEachLetter.put('Y', 4);
        pointOfEachLetter.put('Z', 10);

        // let's calculate how much points for this new word
        char arr[] = newWord.toCharArray();
        for (char letter: arr) {
            if (pointOfEachLetter.containsKey(letter)){
                totalPoints += pointOfEachLetter.get(letter);
            }
        }

        return totalPoints;
    }

    public String getWarning() {
        return warning;
    }

}
