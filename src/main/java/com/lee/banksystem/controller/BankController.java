package com.lee.banksystem.controller;

import com.lee.banksystem.MainApplication;
import com.lee.banksystem.model.BankModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class BankController {
    @FXML
    private AnchorPane bankAnchorPane;
    @FXML
    private Pane loginPane;
    @FXML
    private TextField userIDTextField;
    @FXML
    private TextField userPinTextField;
    @FXML
    private Label promptLabel;
    @FXML
    private Pane signupPane;
    @FXML
    private TextField newuserIDTextField;
    @FXML
    private TextField newuserPinTextField;
    @FXML
    private TextField newuserPinCheckTextField;
    @FXML
    private Label newpromptLabel;
    private BankModel BankModel = new BankModel();

    @FXML
    void onLoginButtonClick() {
        try{
            int userID = Integer.parseInt(userIDTextField.getText());
            int userPIN = Integer.parseInt(userPinTextField.getText());
            if(BankModel.Login(userID, userPIN)){
                switchScene("view/account-view.fxml");
            }
        }
        catch (NumberFormatException e) {
            promptLabel.setText("Provide credentials to login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignupButtonClick(){
        try{
            int userID = Integer.parseInt(newuserIDTextField.getText());
            int userPIN = Integer.parseInt(newuserPinTextField.getText());
            int userPINCheck = Integer.parseInt(newuserPinCheckTextField.getText());
            if(userPIN != userPINCheck){
                newpromptLabel.setText("Passwords do not match");
            }
            else{
                if(BankModel.Signup(userID, userPIN)){
                    switchScene("view/account-view.fxml");
                }
            }
        } catch (NumberFormatException e) {
            newpromptLabel.setText("Provide credentials to signup");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onNewUserClick(){
        loginPane.setVisible(false);
        signupPane.setVisible(true);
    }

    @FXML
    void onExistingUserClick(){
        signupPane.setVisible(false);
        loginPane.setVisible(true);
    }

    void switchScene(String fxml) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        bankAnchorPane.getChildren().removeAll();
        bankAnchorPane.getChildren().setAll(nextAnchorPane);
    }

}
