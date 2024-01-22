public class Account {
    private double money;
    private Customer user;
    private String accountType;

    public Account (Customer user, String accountType){
        this.money = 0;
        this.user = user;
        this.accountType = accountType;
    }
    public double getMoney(){
        return this.money;
    }
    public void setMoney(double amount){
        this.money = amount;
    }

    public void depositMoney(double amount){
        this.setMoney(this.getMoney() + amount);
    }
    public void withdrawMoney(double amount){
        this.setMoney(this.getMoney() - amount);
    }


}
