public class TransactionHistory {
    private String history;
    private static int sID;
    private static int aID;
    public TransactionHistory(){
        this.history = "";
        aID = 0;
        sID = 0;
    }
    private void addHistoryA(String str){
        history += "\n" + "ID :A" +numberFormat(aID) + " " +str;
        aID++;
    }
    private void addHistoryS(String str){
        history += "\n" + "ID :S" +numberFormat(sID) + " " +str;
        sID++;
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

}
