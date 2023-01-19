package com.lee.banksystem.controller;

import com.lee.banksystem.model.BankModel;
import com.lee.banksystem.model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AccountController {
    @FXML
    private AnchorPane accountAnchorPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private TextField inputTextField;

    private BankModel BankModel = new BankModel();

    @FXML
    public void initialize (){
        titleLabel.setText("Welcome to the Bank, " + UserInfo.userFirstname + " " + UserInfo.userLastname);
        balanceLabel.setText("Balance : £" + UserInfo.userBalance);
    }

    @FXML
    void onButtonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        String operatorString = button.getText();
        try{
            int userInput = Integer.parseInt(inputTextField.getText());
            int userBalance = BankModel.DepositorWithdraw(userInput, operatorString);
            balanceLabel.setText("Balance: £" + userBalance);
        }catch (NumberFormatException e){
            System.out.println("NOT INT");
        }
    }

}
