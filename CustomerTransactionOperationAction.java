package ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerTransactionOperationAction {

    private final ReentrantLock lock = new ReentrantLock();

    public void addTransaction(int accountNumber, TransactionDataOperations txn,
                               HashMap<Integer, ArrayList<TransactionDataOperations>> miniStatementMap) {
        lock.lock();
        try {
            ArrayList<TransactionDataOperations> transactions =
                    miniStatementMap.getOrDefault(accountNumber, new ArrayList<>());
            transactions.add(txn);
            if (transactions.size() > 5) {
                transactions.remove(0);
            }
            miniStatementMap.put(accountNumber, transactions);
        } finally {
            lock.unlock();
        }
    }

    public void CashWithdrawal(CustomerDataOperations getUserObj, int ATMBalance, int Withdrawal_Amount,
                               HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        lock.lock();
        try {
            int User_balance = getUserObj.getUserBalance();
            if (ATMBalance >= Withdrawal_Amount && User_balance >= Withdrawal_Amount) {
                if (ATMstorage.deductFromATMBalance(Withdrawal_Amount)) {
                    int Revised_balance = User_balance - Withdrawal_Amount;
                    getUserObj.setUserbalance(Revised_balance);
                    System.out.println(Withdrawal_Amount + " has been withdrawned from your account");
                    System.out.println("The current balance in your account is " + getUserObj.getUserBalance());

                    int TransactionID = getUserObj.IncrementTransactionID();
                    String TransactionRemarks = "Debited " + Withdrawal_Amount + " Ruppees from ATM";
                    TransactionDataOperations customerdetails =
                            new TransactionDataOperations(TransactionID, TransactionRemarks, "Debit", Withdrawal_Amount);
                    addTransaction(getUserObj.getUserAccNo(), customerdetails, MiniStatementDetails);
                } else {
                    System.out.println("Unable to dispense amount due to denomination shortage.");
                }
            } else {
                System.out.println("Regret to inform the requested amount is currently not available");
                System.out.println("Please try after sometime");
            }
        } finally {
            lock.unlock();
        }
    }

    public void Transfer_Cash(CustomerDataOperations getUserObj, CustomerDataOperations TransferObj,
                              int Transfer_amount, int ATM_Balance,
                              HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        lock.lock();
        try {
            if (ATM_Balance < Transfer_amount) {
                System.out.println("ATM does not have enough balance.");
                return;
            } else if (Transfer_amount > getUserObj.getUserBalance()) {
                System.out.println("Balance insufficient");
                return;
            } else if (Transfer_amount > 10000) {
                System.out.println("You cannot debit amount more than 10000");
                return;
            } else if (Transfer_amount < 1000) {
                System.out.println("You cannot debit amount less than 1000");
                return;
            }

            int SenderBalance = getUserObj.getUserBalance();
            int Updated_Sender_Balance = SenderBalance - Transfer_amount;
            getUserObj.setUserbalance(Updated_Sender_Balance);

            int ReceiverBalance = TransferObj.getUserBalance();
            int Updated_Receiver_Balance = ReceiverBalance + Transfer_amount;
            TransferObj.setUserbalance(Updated_Receiver_Balance);

            System.out.println(Transfer_amount + " Ruppees has been sent to " + TransferObj.getUserName());

            int SenderTransactionID = getUserObj.IncrementTransactionID();
            TransactionDataOperations C1 = new TransactionDataOperations(SenderTransactionID,
                    "Funds transfered to Acc " + TransferObj.getUserAccNo(), "Debit", Transfer_amount);
            addTransaction(getUserObj.getUserAccNo(), C1, MiniStatementDetails);

            int ReceiverTransactionID = TransferObj.IncrementTransactionID();
            TransactionDataOperations C2 = new TransactionDataOperations(ReceiverTransactionID,
                    "Funds transfered from Acc " + getUserObj.getUserAccNo(), "Credit", Transfer_amount);
            addTransaction(TransferObj.getUserAccNo(), C2, MiniStatementDetails);

        } finally {
            lock.unlock();
        }
    }

    public void MiniStatement(CustomerDataOperations UserObj,
                              HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails) {
        lock.lock();
        try {
            System.out.println("Account Number: " + UserObj.getUserAccNo());
            System.out.println("Account Holder: " + UserObj.getUserName());
            System.out.println("Account Balance: " + UserObj.getUserBalance());
            System.out.println();
            System.out.printf("%-10s %-30s %-10s %-10s\n", "TxnID", "Remarks", "Type", "Amount");
            System.out.println("--------------------------------------------------------------");

            ArrayList<TransactionDataOperations> txns = MiniStatementDetails.get(UserObj.getUserAccNo());
            if (txns != null && !txns.isEmpty()) {
                for (TransactionDataOperations txn : txns) {
                    System.out.printf("%-10d %-30s %-10s %-10d\n",
                            txn.getTransaction_ID(), txn.getTransaction_Remarks(),
                            txn.getTransaction_Type(), txn.getTransaction_Amount());
                }
            } else {
                System.out.println("No transactions available.");
            }
        } finally {
            lock.unlock();
        }
    }
}
