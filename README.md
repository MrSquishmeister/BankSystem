# BankSystem

This application has been created to demonstrate how a simple bank system would work.
As with all banks, a user can signup if they are a new customer or login if they have an existing account with the bank.

The program accesses a mySQL database where user data is stored to verify the inputted credentials.

## Technical Documentation

### Login Process

The user inputs their userID and userPIN - set when they first signup. First a validation check is done to check that
both inputted text-fields are of type int. If they are not the user is prompted by a message to input the correct credentials 
for their account.

If the inputs are both type int the userID is used to cross referenced with the database and check that the user exists with that
ID exists. The pin entered is then checked against the pin stored for that userID in the database. If it does not match the user
is prompted that either the account does not exist or the password does not match. When the ID exists and the pin matches the user 
is granted acesses to their account and shown the account-view which displays their balance. 

### Signup Process

## Resources

* Javafx
* Scene builder
* mySQL
