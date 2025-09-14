package ATM;

public class TransactionDataOperations {
    private int Transaction_ID;
    private String Transaction_Remarks;
    private String Transaction_Type;
    private int Transaction_Amount;

public TransactionDataOperations(int ID, String TransactionRemarks, String TransactionType, int TransactionAmount){
        this.Transaction_ID = ID;
        this.Transaction_Remarks = TransactionRemarks;
        this.Transaction_Type = TransactionType;
        this.Transaction_Amount = TransactionAmount;
    }

   
    public int getTransaction_ID() {
        return Transaction_ID;
    }
    public void setTransaction_ID(int ID) {
        this.Transaction_ID = ID;
    }

   
    public String getTransaction_Remarks() {
        return Transaction_Remarks;
    }
    public void setTransaction_Remarks(String Remark) {
        this.Transaction_Remarks = Remark;
    }

    
    public String getTransaction_Type() {
        return Transaction_Type;
    }
    public void setTransaction_Type(String Type) {
        this.Transaction_Type = Type;
    }

   
    public int getTransaction_Amount() {
        return Transaction_Amount;
    }
    public void setTransaction_Amount(int Amount) {
        this.Transaction_Amount = Amount;
    }
}
