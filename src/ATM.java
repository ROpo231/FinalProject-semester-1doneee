import java.util.Scanner;


public class ATM {
    private Scanner scan;
    private Account saveAccount;
    private Account checkAccount;
    private Customer user;
    private Account currentAccount;

    public ATM() {
        this.scan = new Scanner(System.in);
    }
    public void start() {
        welcoming();



    }



    private void welcoming() {
        System.out.println("Welcome to your friendly local ATM!!!\n You dont have a account yet would you like to create one? (y/n) : ");
        String makeAccount = scan.nextLine();
        if(makeAccount.equals("y")) {
            creatingAccount();
            enterPin();
            mainMenu();
        }else if(makeAccount.equals("n")) {
            System.out.println("Ok then goodbye!!!");
        } else {
            System.out.println("Sorry I only take y or n please start again");
            welcoming();
        }
    }




    private void creatingAccount() {
        System.out.println("Ok great but i will need some information first");
        System.out.println("Whats your name? :");
        String name = scan.nextLine();
        System.out.println("What do you want your pin to be?(pins are only 4 integers and no spaces please!!!) :");
        String pin = scan.nextLine();
        while(!checkPinContents(pin)){
            System.out.println("Something went wrong with your pin! Try again. (pins are only 4 integers and no spaces please!!!) ");
            pin = scan.nextLine();
        }
        user = new Customer(pin, name);
        saveAccount = new Account(user, "savings account");
        checkAccount = new Account(user, "checking Account");
        System.out.println("Your CHECKING and SAVING accounts have successfully been made.");
    }




    private void mainMenu() {
        System.out.println("Would you like to :\n1. Withdraw money\n2. Deposit money\n3. Transfer money between accounts\n4. Get account balances\n5. Get transaction history\n6. Change PIN\n7. Exit\nOption :");
        String option = scan.nextLine();
        switch (option){
            case "1":
                theCurrentAccount();
                withdrawMoney();
                break;
            case "2":
                theCurrentAccount();
                deposit();
                break;
            case "3":
                transfer();
                break;
            case "4":
                System.out.println("Savings Account: " + saveAccount.getMoney());
                System.out.println("Checking Account: " + checkAccount.getMoney());
                break;
            case "5":

            case "6":
                changePin();
            case "7":
            default:
                System.out.println("Something went wrong. \nPlease retype option number only");
                option = scan.nextLine();
        }




    }





    private void transfer(){
        System.out.println("First please select the account you want to take money out of to put in the other account.");
        theCurrentAccount();
        System.out.println("How much would you like to transfer? :");
        double transferAmount =  scan.nextDouble();
        while(transferAmount < 0 || (transferAmount * 100) % 1 != 0 || transferAmount <= currentAccount.getMoney()){
            System.out.println("Amount invalid try again (Make sure you have the amount you are putting!!):");
            transferAmount = scan.nextDouble();
        }
        if(currentAccount == saveAccount){
            saveAccount.withdrawMoney(transferAmount);
            checkAccount.depositMoney(transferAmount);
        }else{
            checkAccount.withdrawMoney(transferAmount);
            saveAccount.depositMoney(transferAmount);
        }


    }
    private void deposit(){
        if(currentAccount == saveAccount){
            System.out.println("How much would you like to deposit?(for saving we dont accept coins)");
            double amount = scan.nextDouble();
            while(amount < 0 || amount % 1 != 0){
                System.out.println("Amount invalid try again :");
                amount = scan.nextDouble();
            }
            saveAccount.depositMoney(amount);
            System.out.println("Done");


        }else{
            System.out.println("How much would you like to deposit? (for checking)");
            double amount = scan.nextDouble();
            while(amount < 0 || (amount * 100) % 1 != 0   ){
                System.out.println("Amount invalid try again :");
                amount = scan.nextDouble();
            }
            checkAccount.depositMoney(amount);
            System.out.println("Done");

        }

    }




    private void changePin(){
        System.out.println("What do you want your new pin to be? :");
        String pin = scan.nextLine();
        while(!checkPinContents(pin)){
            System.out.println("Something went wrong with your pin! Try again. (pins are only 4 integers and no spaces please!!!) ");
            pin = scan.nextLine();
        }
        user.setPin(pin);
        System.out.println("The new pin has been successfully set");
    }




    private void withdrawMoney() {
        String thError = "";
        if(currentAccount.getMoney() >= 5) {
            System.out.println("You can only withdraw in bills of 5s and 20s\nHow much would you like to withdraw?(Integer amount only & No spaces please!!) :");
            double totalAmount = scan.nextDouble();
            if (totalAmount < 5 || totalAmount % 5 != 0) {
                System.out.println("ERROR:Invalid amount\nGoodbye!");

            }else if (totalAmount > currentAccount.getMoney()) {
                System.out.println("ERROR:insufficient funds!\nGoodbye!");
            } else {
                System.out.println("How many  20 bills :");
                int twenty = scan.nextInt();
                while (twenty * 20 > totalAmount) {
                    System.out.println("Invalid amount (Might be too many bills)\nTry again :");
                    twenty = scan.nextInt();
                }
                System.out.println("How many  5 bills :");
                int fives = scan.nextInt();
                while (fives * 5 > totalAmount - (twenty * 20) || fives * 5 < totalAmount - (twenty * 20)) {
                    System.out.println("Invalid amount (Might be too many/little bills)\nTry again :");
                    fives = scan.nextInt();
                }
                currentAccount.withdrawMoney((twenty * 20) + (fives * 5));
                System.out.println("Ok done!!");
            }
        }else{
            System.out.println("ERROR: insufficient funds!\nGoodbye!");
        }



    }
    private void theCurrentAccount(){
        System.out.println("Which account? Savings or Checking (S/C) :");
        String option = scan.nextLine().toLowerCase();
        while(!(option.equals("s") || option.equals("c"))){
            System.out.println("Invalid option! Try again (S/C) :");
            option = scan.nextLine().toLowerCase();
        }
        if(option.equals("s")){
            currentAccount = saveAccount;
        }else{
            currentAccount = checkAccount;
        }
    }




    private void enterPin() {
        System.out.println("Please enter a pin to access you account.(No spaces please)");
        String pin = scan.nextLine();
        while(!user.getPin().equals(pin)){
            System.out.println("Somethings worng. Try again.(No spaces please)");
            pin = scan.nextLine();
        }
        System.out.println("Access granted.");
    }
    private boolean checkPinContents(String thePin) {
        if(thePin.length() != 4) {
            return false;
        }
        try {
            int varChecker = Integer.parseInt(thePin);
        }catch (Exception e) {
            return false;
        }
        return true;
    }











}




