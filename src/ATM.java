import java.util.Scanner;


public class ATM {
    private Scanner scan;
    private Account saveAccount;
    private Account checkAccount;
    private Customer user;
    private Account currentAccount;
    private TransactionHistory entireHistory;

    public ATM() {
        scan = new Scanner(System.in);
        entireHistory = new TransactionHistory();
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
        boolean over = false;
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
                checkBalance();
                break;
            case "5":
                theTransaction();
                break;
            case "6":
                changePin();
                break;
            case "7":
                over = true;
                System.out.println("Thanks for being a customer goodbye");
                break;
            default:
                System.out.println("Something went wrong. \nPlease retype option number only");
                option = scan.nextLine();
        }
        scan.nextLine();
        if(!over){
            System.out.println("Do you want to continue (y/n)");
            String leave = scan.nextLine().toLowerCase();
            if (leave.equals("y")) {
                enterPin();
                mainMenu();
            } else {
                System.out.println("Thanks for being a customer goodbye");
            }
        }
    }
    private void theTransaction(){
        System.out.println(entireHistory.getHistory());
        receipt(2, "Checking transaction history","Successfully checked");

    }
    private void checkBalance(){
        System.out.println("Savings Account: " + saveAccount.getMoney());
        System.out.println("Checking Account: " + checkAccount.getMoney());
        receipt(2, "Getting account balance" , " Got account balance");
    }





    private void transfer() {
        String theAcc;
        String suc = "money transfer was successful";
        System.out.println("First please select the account you want to take money out of to put in the other account.");
        theCurrentAccount();
         if(currentAccount == saveAccount){
            theAcc = " From Savings Account to Checking Account";
        }else{
            theAcc = " From Checking Account to Savings Account";
        }
        System.out.println("How much would you like to transfer? :");
        double transferAmount = scan.nextDouble();
        if (transferAmount < 0 || (transferAmount * 100) % 1 != 0 || transferAmount > currentAccount.getMoney()) {
            System.out.println("ERROR: Amount invalid");
            if(transferAmount < 0 || (transferAmount * 100) % 1 != 0){
                suc = "Amount typed invalid";
            }else{
                suc = "you do not have enough money in your account to withdraw that amount FAILED";
            }
        }else if(currentAccount == saveAccount){
            saveAccount.withdrawMoney(transferAmount);
            checkAccount.depositMoney(transferAmount);

        }else{
            checkAccount.withdrawMoney(transferAmount);
            saveAccount.depositMoney(transferAmount);
        }
        receipt(1,"transfer $" +transferAmount + theAcc, suc );


    }
    private void deposit(){
        String theAcc;
        if(currentAccount != saveAccount){
            theAcc = " to Checking Account";
        }else{
            theAcc = " to Savings Account";
        }
        String suc;
        System.out.println("How much would you like to deposit?");
        double amount = scan.nextDouble();
        if(amount < 0 || (amount * 100) % 1 != 0){
            System.out.println("Amount invalid ");
            suc = "Amount inputted invalid FAILED";
        }else {
            currentAccount.depositMoney(amount);
            suc = "Deposited $" + amount +  "into" + theAcc;
        }
        if(currentAccount == saveAccount){
            theAcc = " From Savings Account to Checking Account";
        }else{
            theAcc = " From Checking Account to Savings Account";
        }
        receipt(1, "Depositing $" +amount + theAcc, suc );



    }




    private void changePin(){
        String suc = "changed pin";
        System.out.println("What do you want your new pin to be?(pins are only 4 integers and no spaces please!!!) :");
        String pin = scan.nextLine();
        if(!checkPinContents(pin)){
            suc = "pin input invalid FAILED";
            System.out.println("Error pin input invalid ");
        }else{
            user.setPin(pin);
            System.out.println("The new pin has been successfully set");
        }
        user.setPin(pin);
        System.out.println("The new pin has been successfully set");
        receipt(2, "changing PIN",suc  );
    }




    private void withdrawMoney() {
        double totalAmount;
        String theAcc;
        String suc;
        if(currentAccount != saveAccount){
            theAcc = " Withdrawing from Checking Account $";
            suc = "Withdrew from Checking Account  $";
        }else{
            theAcc = " Withdrawing from to Savings Account $";
            suc = "Withdrew from Savings Account $";
        }

        System.out.println("You can only withdraw in bills of 5s and 20s\nHow much would you like to withdraw?(Integer amount only & No spaces please!!) :");
        totalAmount = scan.nextDouble();
        if (totalAmount < 5 || totalAmount % 5 != 0) {
            System.out.println("ERROR:Invalid amount\nGoodbye!");
            suc = "Invalid amount of money for withdraw FAILED";

        }else if (totalAmount > currentAccount.getMoney()) {
            System.out.println("ERROR:insufficient funds!\nGoodbye!");
            suc = "dont have enough money in account for withdraw FAILED";
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
        receipt(1, theAcc + totalAmount, suc );





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
        System.out.println("Please enter a pin to access your account.(No spaces please)");
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
    private String numberFormat(int iD){
        if(iD < 10){
            return "000" + iD;
        }else if(iD < 100){
            return "00" + iD;
        }else if(iD < 1000){
            return "0" + iD;
        } else{
            return "" + iD;
        }
    }

    private void receipt(int iDNum, String summery, String suc ){
        String iD;
        if(iDNum == 1){
            iD = " A" + numberFormat(entireHistory.getAID());
            entireHistory.incIDA();
        }else{
            iD = " S" + numberFormat(entireHistory.getSID());
            entireHistory.incIDS();
        }
        String str = summery + " ID:"+iD + "  " + suc + " Saving -$" + saveAccount.getMoney() + " Checking -$" + checkAccount.getMoney();
        entireHistory.addHistory(str);
        System.out.println("RECEIPT:\n" + str);
    }











}




