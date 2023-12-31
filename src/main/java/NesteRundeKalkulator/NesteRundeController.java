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
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NesteRundeController {

    Session session;
    
    @FXML Button newSessionButton, addUserButton, finishedButton, initialRound, beerButton, cocktailButton, shotButton, newRound, exit, other;
    @FXML TextField sessionName, addUser, otherPrice;
    @FXML Label finishedLabel, buyerLabel, Leaderboard, priceTitle, priceList, sessionLabel;
    @FXML Pane exitPane;
    @FXML AnchorPane background;
    @FXML ImageView trond;

    // BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("trond.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    // Background background = new Background(backgroundImage);

    public void handleNewSession() {
        String name = sessionName.getText();
        this.session = new Session(name);

        newSessionButton.setVisible(false);
        sessionName.setVisible(false);
        addUserButton.setVisible(true);
        addUser.setVisible(true);
        finishedButton.setVisible(true);
        finishedLabel.setVisible(true);
        buyerLabel.setText("");
        sessionLabel.setText(name);
        sessionLabel.setVisible(true);
    }


    public void handleAddUser() {
        try {
            if (addUser.getText().equals("")) {
                throw new IllegalArgumentException("Ulovlig bruker");
            }
            String name = addUser.getText();
            User user = new User(name);
            session.addUser(user);
            addUser.clear();
        } catch (Exception e) {
            Alert alert=new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    public void handleFinished() {
        addUserButton.setVisible(false);
        addUser.setVisible(false);
        finishedButton.setVisible(false);
        finishedLabel.setVisible(false);
        initialRound.setVisible(true);
        sessionLabel.setVisible(false);
        // initialRound.setBackground(background);
    }

    public void handleInitialRound() {
        User user = session.getRandomUser();
        if (user.getName().toLowerCase().equals("trond")) {
            trond.setVisible(true);
        }
        session.setBuyingUser(user);
        initialRound.setVisible(false);
        buyerLabel.setVisible(true);
        buyerLabel.setText(user.getName()+" kjøper første runde!");
        priceList.setVisible(true);
        priceTitle.setVisible(true);
        beerButton.setVisible(true);
        cocktailButton.setVisible(true);
        shotButton.setVisible(true);
        other.setVisible(true);
        otherPrice.setVisible(true);
        exit.setVisible(true);
        Leaderboard.setText("");
        String output = "";
        for (User user2 : session.getUsers()) {
            output += user2.getName()+ " har spandert drikke på " + user2.getBalance() +" kr \n";
        }
        Leaderboard.setText(output);
    }

    public void handleBeerBuy(){
        session.nextUserBuyRound(75);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        other.setVisible(false);
        otherPrice.setVisible(false);
        trond.setVisible(false);
        buyerLabel.setText("Kos dere med ølen!");
        updateLeaderBoard();
        //Skrive til fil
    }

    public void handleCocktailBuy(){
        session.nextUserBuyRound(100);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        other.setVisible(false);
        otherPrice.setVisible(false);
        trond.setVisible(false);
        buyerLabel.setText("Kos dere med drinken!");
        updateLeaderBoard();

        //Skrive til fil
    }

    public void handleShotBuy(){
        session.nextUserBuyRound(80);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        other.setVisible(false);
        otherPrice.setVisible(false);
        trond.setVisible(false);
        buyerLabel.setText("Kos dere med shoten!");
        updateLeaderBoard();

        //Sktive til fil
    }

    public void handleOtherDrinkBuy(){
        try {
            if (otherPrice.getText().equals("")) {
                throw new IllegalArgumentException("Ulovlig input");
            }
            int drinkPrice = Integer.parseInt(otherPrice.getText());
            if (drinkPrice<=0 || drinkPrice>1000) {
                throw new IllegalArgumentException("Ulovlig input");
            }
        } 
        catch (IllegalArgumentException e) {
            Alert alert=new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        int drinkPrice = Integer.parseInt(otherPrice.getText());
        session.nextUserBuyRound(drinkPrice);
        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        newRound.setVisible(true);
        buyerLabel.setText("Kos dere med shoten!");
        updateLeaderBoard();

        //Sktive til fil
    }

    public void handleNewRound() {
        User nextBuyer = session.suggestNextUser();
        if (nextBuyer.getName().toLowerCase().equals("trond")) {
            trond.setVisible(true);
        }
        session.setBuyingUser(nextBuyer);
        newRound.setVisible(false);
        buyerLabel.setVisible(true);
        buyerLabel.setText(nextBuyer.getName() + " kjøper neste runde!");
        beerButton.setVisible(true);
        cocktailButton.setVisible(true);
        shotButton.setVisible(true);
        other.setVisible(true);
        otherPrice.setVisible(true);
        updateLeaderBoard();
    }

    public void updateLeaderBoard() {
        Leaderboard.setText("");
        String output = "";
        for (User user2 : session.getUsers()) {
            output += user2.getName()+ " har spandert drikke på " + user2.getBalance() +" kr \n";
        }
        Leaderboard.setText(output);
    }

    public void handleExit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public void handleOther() {
        try {
            if (otherPrice.getText().equals("")) {
                throw new IllegalArgumentException("Ulovlig input");
            }
            int drinkPrice = Integer.parseInt(otherPrice.getText());
            if (drinkPrice<=0 || drinkPrice>1000) {
                throw new IllegalArgumentException("Ulovlig input");
            }
            session.nextUserBuyRound(drinkPrice);
        } 
        catch (Exception e) {
            Alert alert=new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.showAndWait();
        }

        beerButton.setVisible(false);
        cocktailButton.setVisible(false);
        shotButton.setVisible(false);
        other.setVisible(false);
        otherPrice.clear();
        otherPrice.setVisible(false);
        newRound.setVisible(true);
        trond.setVisible(false);
        buyerLabel.setText("Kos dere med drikken!");
        updateLeaderBoard();
    }
}
