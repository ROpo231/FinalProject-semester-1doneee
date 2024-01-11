import java.util.Scanner;
import java.lang.Thread;
public class ATM {
    private Scanner scan;
    private Account saveAccount;
    private Account checkAccount;
    private Customer user;



    public ATM() {
        this.scan = new Scanner(System.in);
    }
    public void start() {

    }

    public void welcoming() {
        System.out.println("Welcome to your friendly local ATM!!!\n You dont have a account yet would you like to create one? (y/n) : ");
        String makeAccount = scan.nextLine();
        if(makeAccount.equals("y")) {
            creatingAccount();
            enterPin();

        }else if(makeAccount.equals("n")) {
            System.out.println("Ok then goodbye!!!");
        } else {
            System.out.println("Sorry I only take y or n please start again");
            try {
                Thread.sleep(5000);
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            } catch (InterruptedException e) {
                System.out.println("something went wrong with the delay thing in the welcoming method");
            }
            this.welcoming();
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
        saveAccount = new Account(user);
        checkAccount = new Account(user);
        System.out.println("Your CHECKING and SAVING accounts have successfully been made.");
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
            int contentChecker = Integer.parseInt(thePin);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

}
