package com.lee.banksystem.controller;

import com.lee.banksystem.MainApplication;
import com.lee.banksystem.model.BankModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class BankController {
    @FXML
    private AnchorPane bankAnchorPane;
    @FXML
    private TextField userIDTextField;
    @FXML
    private TextField userPinTextField;
    @FXML
    private Label promptLabel;
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
            throw new RuntimeException(e);
        }
    }

    void switchScene(String fxml) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        bankAnchorPane.getChildren().removeAll();
        bankAnchorPane.getChildren().setAll(nextAnchorPane);
    }

}
