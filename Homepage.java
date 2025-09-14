package ATM;
import java.util.*;

public class Homepage {

    public static HashMap<Integer, ArrayList<TransactionDataOperations>> MiniStatementDetails = new HashMap<>();

    public static void main(String args[]) {
        Scanner Scan = new Scanner(System.in);

        ArrayList<CustomerDataOperations> UserDetails = new ArrayList<>();

        UserDetails.add(new CustomerDataOperations(101, "Suresh", 2343, 25234));
        UserDetails.add(new CustomerDataOperations(102, "Ganesh", 5432, 34123));
        UserDetails.add(new CustomerDataOperations(103, "Magesh", 7854, 26100));
        UserDetails.add(new CustomerDataOperations(104, "Naresh", 2345, 80000));
        UserDetails.add(new CustomerDataOperations(105, "Harish", 1907, 103400));

        CustomerTransactionOperationAction User = new CustomerTransactionOperationAction();

        while (true) {
            System.out.println("1. Load Cash to ATM");
            System.out.println("2. Show Customer Details");
            System.out.println("3. Show ATM Operations");
            System.out.println("Choose: ");

            int choice = Scan.nextInt();

            if (choice == 1) {
                System.out.println("How much amount that you are going to Load !?");
                int cash = Scan.nextInt();
                ATMstorage.Loadcash(cash);
                System.out.println("The Current balance in ATM device:- ");
                System.out.println(ATMstorage.GetATMBalance());
            } else if (choice == 2) {
                CustomerDataOperations.ShowUserdetails(UserDetails);
            } else if (choice == 3) {
                System.out.println("Please enter your Account Number :-");
                int Acc_no = Scan.nextInt();
                System.out.println("Enter the PIN:-");
                int Pin = Scan.nextInt();
                boolean isValidated = CustomerDataOperations.UserAccountValidation(Acc_no, Pin, UserDetails);
                if (isValidated) {
                    System.out.println("1. Check Balance");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. Transfer Money");
                    System.out.println("4. Mini Statement");

                    int Selected_Choice = Scan.nextInt();

                    CustomerDataOperations UserObj = CustomerDataOperations.getUserObjusingAccountNumber(Acc_no, UserDetails);
                    int ATM_Balance = ATMstorage.GetATMBalance();

                    if (Selected_Choice == 1) {
                        System.out.println("Your Current available balance is " + UserObj.getUserBalance());
                    } else if (Selected_Choice == 2) {
                        System.out.println("Please specify the amount that you would like to withdraw:-");
                        int Withdrawl_amount = Scan.nextInt();
                        User.CashWithdrawal(UserObj, ATM_Balance, Withdrawl_amount, MiniStatementDetails);
                    } else if (Selected_Choice == 3) {
                        System.out.println("Enter the account number of the User that you would like to make this Fund transfer to:-");
                        int Accno = Scan.nextInt();
                        System.out.println("Enter the amount:-");
                        int Transfer_Amount = Scan.nextInt();
                        CustomerDataOperations TransferObj = CustomerDataOperations.getUserObjusingAccountNumber(Accno, UserDetails);
                        if (TransferObj != null) {
                            User.Transfer_Cash(UserObj, TransferObj, Transfer_Amount, ATM_Balance, MiniStatementDetails);
                        }
                    } else if (Selected_Choice == 4) {
                        User.MiniStatement(UserObj, MiniStatementDetails);
                    } else {
                        System.out.println("Invalid Choice");
                    }
                }
            } else {
                System.out.println("Invalid Option. Please try again.");
            }
        }
    }
}
