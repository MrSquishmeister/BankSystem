package com.lee.banksystem.model;

import javafx.scene.control.Alert;
import java.sql.*;

public class BankModel {
    private int userID, userBalance;

    public boolean Login(int inputuserID, int userPIN) {
        userID = inputuserID;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", "root", "rootpassword");
            preparedStatement = connection.prepareStatement("SELECT userPIN, userBalance FROM bankDB.users WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided userID not found");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int retrievedUserPIN = resultSet.getInt("userPIN");
                    //userBalance = resultSet.getInt("userBalance");
                    if (retrievedUserPIN == userPIN) {
                        System.out.println("userPIN MATCHES");
                        return true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided userPin incorrect");
                        alert.show();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean Signup(int inputuserID, int userPIN) {
        userID = inputuserID;
        Connection connection = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", "root", "rootpassword");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bankDB.users WHERE userID = ?");
            psCheckUserExists.setInt(1, userID);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided userID already exists");
                alert.show();
            }else{
                //int userBalance = 0;
                psInsert = connection.prepareStatement("INSERT INTO bankDB.users(userID, userPIN, userBalance) VALUES(?, ?, ?)");
                psInsert.setInt(1, userID);
                psInsert.setInt(2, userPIN);
                psInsert.setInt(3, 0);
                psInsert.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public int Deposit(int userInput, String operator) {
        if(operator.equals("Deposit")){
            userBalance = userBalance + userInput;
        }else if (operator.equals("Withdraw")){
            userBalance = userBalance - userInput;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", "root", "rootpassword");
            preparedStatement = connection.prepareStatement("UPDATE bankDB.users SET userBalance = ?  WHERE userID = ?");
            preparedStatement.setInt(1, userBalance);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userBalance;
    }

}

