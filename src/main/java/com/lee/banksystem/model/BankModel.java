package com.lee.banksystem.model;

import javafx.scene.control.Alert;
import java.sql.*;

public class BankModel {
    public boolean Login(int userID, int userPIN) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", "root", "rootpassword");
            preparedStatement = connection.prepareStatement("SELECT userPIN, userBalance, userFirstname, userLastname FROM bankDB.users WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided userID not found");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int retrievedUserPIN = resultSet.getInt("userPIN");
                    UserInfo.userBalance = resultSet.getInt("userBalance");
                    UserInfo.userFirstname = resultSet.getString("userFirstname");
                    UserInfo.userLastname = resultSet.getString("userLastname");
                    UserInfo.userID = userID;
                    if (retrievedUserPIN == userPIN) {
                        System.out.println("PIN MATCHES");
                        return true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided Pin incorrect");
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

    public boolean Signup(int userID, int userPIN) {
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
                psInsert = connection.prepareStatement("INSERT INTO bankDB.users(userID, userPIN, userBalance) VALUES(?, ?, ?)");
                psInsert.setInt(1, userID);
                psInsert.setInt(2, userPIN);
                psInsert.setInt(3, 0);
                psInsert.executeUpdate();
                UserInfo.userID = userID;
                UserInfo.userBalance = 0;
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

    public int DepositorWithdraw(int userInput, String operator) {
        if(operator.equals("Deposit")){
            UserInfo.userBalance += userInput;
        }else if (operator.equals("Withdraw")){
            UserInfo.userBalance -= userInput;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", "root", "rootpassword");
            preparedStatement = connection.prepareStatement("UPDATE bankDB.users SET userBalance = ?  WHERE userID = ?");
            preparedStatement.setInt(1, UserInfo.userBalance);
            preparedStatement.setInt(2, UserInfo.userID);
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
        return UserInfo.userBalance;
    }

}

