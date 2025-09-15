package ATM;

import java.util.HashMap;
import java.util.Map;

public class ATMstorage {
  
	private static int ATM_Balance; 

    public static Map<Integer, Integer> denominations = new HashMap<>();

static {
    denominations.put(1000, 20);
    denominations.put(500, 100);
    denominations.put(100, 300);
}
	

public synchronized static void Loadcash(int cash) {
    if (cash % 100000 != 0) {
        System.out.println("Cash must be in multiples of 100000");
        return;
    }
    int n = cash / 100000;
    denominations.put(1000, denominations.get(1000) + (20 * n));
    denominations.put(500, denominations.get(500) + (100 * n));
    denominations.put(100, denominations.get(100) + (300 * n));
    System.out.println("Cash imported Successfully");
}

public synchronized static int GetATMBalance() {
	int total = 0;
    for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
        total += entry.getKey() * entry.getValue();
    }
    return total;
}

public synchronized static boolean deductFromATMBalance(int amount) {
    int remaining = amount;
    HashMap<Integer, Integer> tempDenominations = new HashMap<>(denominations);

    int availableThousands = tempDenominations.get(1000);
    int neededThousands = remaining / 1000;
    int usedThousands = Math.min(neededThousands, availableThousands);
    remaining -= usedThousands * 1000;
    tempDenominations.put(1000, availableThousands - usedThousands);

 
    int availableFiveHundreds = tempDenominations.get(500);
    int neededFiveHundreds = remaining / 500;
    int usedFiveHundreds = Math.min(neededFiveHundreds, availableFiveHundreds);
    remaining -= usedFiveHundreds * 500;
    tempDenominations.put(500, availableFiveHundreds - usedFiveHundreds);

  
    int availableHundreds = tempDenominations.get(100);
    int neededHundreds = remaining / 100;
    int usedHundreds = Math.min(neededHundreds, availableHundreds);
    remaining -= usedHundreds * 100;
    tempDenominations.put(100, availableHundreds - usedHundreds);

    if (remaining > 0) {
        System.out.println("Unable to dispense exact amount with available denominations.");
        return false; 
    }


    denominations = tempDenominations;
    System.out.println("Cash withdrawn successfully!");
    System.out.println("1000 x " + usedThousands + ", 500 x " + usedFiveHundreds + ", 100 x " + usedHundreds);
    return true;
}

}
