package ATM;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerTransactionOperationAction {
	
	 public void addTransaction(int accountNumber, TransactionDataOperations txn, HashMap<Integer, ArrayList<TransactionDataOperations>> miniStatementMap) {
              ArrayList<TransactionDataOperations> transactions = miniStatementMap.getOrDefault(accountNumber, new ArrayList<>());
                  transactions.add(txn);

                if (transactions.size() > 5) {
                       transactions.remove(0);  
                      }

             miniStatementMap.put(accountNumber, transactions);
}

  public void CashWithdrawal(CustomerDataOperations getUserObj, int ATMBalance, int Withdrawal_Amount, 
        HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        int User_balance = getUserObj.getUserBalance();
        if (ATMBalance >= Withdrawal_Amount && User_balance >= Withdrawal_Amount) {
            int Revised_balance = User_balance - Withdrawal_Amount;
            getUserObj.setUserbalance(Revised_balance);
            ATMstorage.deductFromATMBalance(Withdrawal_Amount);
            System.out.println(Withdrawal_Amount + " has been withdrawned from your account");
            System.out.println("The current balance in your account is " + getUserObj.getUserBalance());
        } else {
            System.out.println("Regret to inform the amount is requested is currently not available");
            System.out.println("Please try after sometime");
        }
        int TransactionID = getUserObj.IncrementTransactionID();
        String TransactionRemarks = "Debited " + Withdrawal_Amount + " Ruppees from ATM";
        String TransactionType = "Debit";
        int Withdrawlamount = Withdrawal_Amount;
        TransactionDataOperations customerdetails = new TransactionDataOperations(TransactionID, TransactionRemarks, TransactionType, Withdrawlamount);
        int account_number = getUserObj.getUserAccNo();
        addTransaction(getUserObj.getUserAccNo(), customerdetails, MiniStatementDetails);
    }

    public void Transfer_Cash(CustomerDataOperations getUserObj, CustomerDataOperations TransferObj, int Transfer_amount, int ATM_Balance, 
                              HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        if (ATM_Balance < Transfer_amount) {
            System.out.println("Regret to inform the amount is requested is currently not available");
            System.out.println("Please try after sometime");
        } else if (Transfer_amount > getUserObj.getUserBalance()) {
            System.out.println("Balance insufficient");
        } else if (Transfer_amount > 10000) {
            System.out.println("You cannot debit amount more than 10000");
        } else if (Transfer_amount < 1000) {
            System.out.println("You cannot debit amount less than 1000");
        } else {
            int SenderBalance = getUserObj.getUserBalance();
            int Updated_Sender_Balance = SenderBalance - Transfer_amount;
            getUserObj.setUserbalance(Updated_Sender_Balance);
            int ReceiverBalance = TransferObj.getUserBalance();
            int Updated_Receiver_Balance = ReceiverBalance + Transfer_amount;
            TransferObj.setUserbalance(Updated_Receiver_Balance);
            System.out.println(Transfer_amount + " Ruppees has been sent to " + TransferObj.getUserName());
        }
        int SenderTransactionID = getUserObj.IncrementTransactionID();
        String SenderTransactionRemarks = "Funds transfered to Acc " + TransferObj.getUserAccNo();
        String SenderTransactionType = "Debit";
        int SenderWithdrawlamount = Transfer_amount;
        int SenderAccountNumber = getUserObj.getUserAccNo();
        TransactionDataOperations C1 = new TransactionDataOperations(SenderTransactionID, SenderTransactionRemarks, SenderTransactionType, SenderWithdrawlamount);
        addTransaction(getUserObj.getUserAccNo(), C1, MiniStatementDetails);
        
        int ReceiverTransactionID = TransferObj.IncrementTransactionID();
        String ReceiverTransactionRemarks = "Funds transfered from Acc " + getUserObj.getUserAccNo();
        String ReceiverTransactionType = "Credit";
        int ReceiverTransactionAmount = Transfer_amount;
        int ReceiverAccountNumber = TransferObj.getUserAccNo();
        TransactionDataOperations C2 = new TransactionDataOperations(ReceiverTransactionID, ReceiverTransactionRemarks, ReceiverTransactionType, ReceiverTransactionAmount);
        addTransaction(TransferObj.getUserAccNo(), C2, MiniStatementDetails);
    }

    public void MiniStatement(CustomerDataOperations UserObj, HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        System.out.println("Account Number: " + UserObj.getUserAccNo());
        System.out.println("Account Holder: " + UserObj.getUserName());
        System.out.println("Account Balance: " + UserObj.getUserBalance());
        System.out.println();
        System.out.printf("%-10s %-30s %-10s %-10s\n", "TxnID", "Remarks", "Type", "Amount");
        System.out.println("--------------------------------------------------------------");
        ArrayList<TransactionDataOperations> txns = MiniStatementDetails.get(UserObj.getUserAccNo());
        if (txns != null) {
            for (TransactionDataOperations txn : txns) {
                System.out.printf("%-10d %-30s %-10s %-10d\n",
                        txn.getTransaction_ID(), txn.getTransaction_Remarks(), txn.getTransaction_Type(), txn.getTransaction_Amount());
            }
        }
    }
}
