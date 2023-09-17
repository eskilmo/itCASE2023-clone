package NesteRundeKalkulator;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NesteRundeController {

    Session session;
    
    @FXML Button newSessionButton, addUserButton, finishedButton, initialRound, beerButton, cocktailButton, shotButton, newRound;
    @FXML TextField sessionName, addUser;
    @FXML Label finishedLabel, buyerLabel, Leaderboard;



    public void handleNewSession() {
        String name = sessionName.getText();
        this.session = new Session(name);

        newSessionButton.setVisible(false);
        sessionName.setVisible(false);
        addUserButton.setVisible(true);
        addUser.setVisible(true);
        finishedButton.setVisible(true);
        finishedLabel.setVisible(true);
    }


    public void handleAddUser() {
        String name = addUser.getText();
        User user = new User(name);
        session.addUser(user);
        addUser.clear();
    }

    public void handleFinished() {
        addUserButton.setVisible(false);
        addUser.setVisible(false);
        finishedButton.setVisible(false);
        finishedLabel.setVisible(false);
        initialRound.setVisible(true);
    }

    public void handleInitialRound() {
        User user = session.getRandomUser();
        session.setBuyingUser(user);
        initialRound.setVisible(false);
        buyerLabel.setVisible(true);
        buyerLabel.setText(user.getName()+" kjøper første runde!");
        beerButton.setVisible(true);
        cocktailButton.setVisible(true);
        shotButton.setVisible(true);
        Leaderboard.setText("");
        String output = "";
        for (User user2 : session.getUsers()) {
            output += user2.getName()+ " har spandert " + user2.getBalance() +" ganger \n";
        }
        Leaderboard.setText(output);
    }

    public void handleBeerBuy(){
        session.getBuyingUser().setBalance(1);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        buyerLabel.setText("Kos dere med ølen!");
        //Skrive til fil
    }

    public void handleCocktailBuy(){
        session.getBuyingUser().setBalance(2);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        buyerLabel.setText("Kos dere med drinken!");

        //Skrive til fil
    }

    public void handleShotBuy(){
        session.getBuyingUser().setBalance(2);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        buyerLabel.setText("Kos dere med shoten!");

        //Sktive til fil
    }

    public void handleNewRound() {
        User nextBuyer = session.suggestNextUser();
        newRound.setVisible(false);
        buyerLabel.setVisible(true);
        buyerLabel.setText(nextBuyer.getName() + " kjøper neste runde!");
        beerButton.setVisible(true);
        cocktailButton.setVisible(true);
        shotButton.setVisible(true);
    }

    public void updateLeaderBoard() {

    }


}
