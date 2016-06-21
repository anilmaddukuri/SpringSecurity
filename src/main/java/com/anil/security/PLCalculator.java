import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PLCalculator {
	
	static List<Integer> strikes = new ArrayList<Integer>();
	static List<Integer> callPrices = new ArrayList<Integer>();
	static List<Integer> putPrices = new ArrayList<Integer>();

	static int range = 500;
	
	static {
		callPrices.add(536);
		callPrices.add(441);
		callPrices.add(347);
		callPrices.add(264);
		callPrices.add(179);
		callPrices.add(113);
		callPrices.add(60);
		callPrices.add(27);
		callPrices.add(10);
		callPrices.add(4);
		callPrices.add(2);
		callPrices.add(1);
		putPrices.add(10);
		putPrices.add(14);
		putPrices.add(22);
		putPrices.add(34);
		putPrices.add(53);
		putPrices.add(83);
		putPrices.add(126);
		putPrices.add(190);
		putPrices.add(273);
		putPrices.add(375);
		putPrices.add(475);
		putPrices.add(564);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int inputStrike = Integer.parseInt(sc.nextLine());
		int lower = round(inputStrike, false);
		int lowerLimit = lower - range;
		int upper = round(inputStrike, true);
		int upperLimit = upper + range;
		do {
			strikes.add(lowerLimit);
			lowerLimit += 100;
		} while(lowerLimit <= upperLimit);
		for (int i = 0; i < strikes.size(); i++) {
			System.out.println(strikes.get(i) + " " + callPrices.get(i) + " " + putPrices.get(i));
		}
		int spread = 100;
		for (int i = 0; i < strikes.size(); i++) {
			int tmp = strikes.get(i) + spread;
			if(strikes.indexOf(tmp) >= 0) {					
				BullPutSpreadStrategy strategy = new BullPutSpreadStrategy(strikes.get(i), putPrices.get(i), tmp, putPrices.get(strikes.indexOf(tmp)));
				strategy.execute(7000, 9000, 10);
			}
		}
		sc.close();
	}
	
	public static int round(int number, boolean upper) {
		int last2Digits = number % 100;
		if(upper) {
			return (number + (100 - last2Digits));
		} else {
			return (number - last2Digits);
		}
	}

}

class BullPutSpreadStrategy {
	
	private int lowerStrike;
	private int lowerPrice;
	private int higherStrike;
	private int higherPrice;
	private int[] priceResult;
	
	public BullPutSpreadStrategy(int lowerStrike, int lowerPrice, int higherStrike, int higherPrice) {
		this.lowerStrike = lowerStrike;
		this.lowerPrice = lowerPrice;
		this.higherStrike = higherStrike;
		this.higherPrice = higherPrice;
	}
	
	public void execute(int lower, int higher, int step) {
		for (int lastDayPrice = lower; lastDayPrice <= higher; lastDayPrice += step) {
			//compute the PL made by lower strike
			int intrinsicValueUponExpiryOfLower = Math.max((lowerStrike - lastDayPrice), 0);
			int PLLowerStrike = intrinsicValueUponExpiryOfLower - lowerPrice;
			//compute the PL made by higher strike
			int intrinsicValueUponExpiryOfHigher = Math.max((higherStrike - lastDayPrice), 0);
			int PLHigherStrike = higherPrice - intrinsicValueUponExpiryOfHigher;
			//result is the addition of the above two. 
			int netPL = PLHigherStrike + PLLowerStrike;
			//Max profit
			int MAX_PROFIT = higherPrice - lowerPrice;
			int MAX_LOSS = (higherStrike - lowerStrike) - MAX_PROFIT;
			double RRR = (double)MAX_LOSS/(double)MAX_PROFIT;
			//print it
			System.out.println(lowerStrike + " " + higherStrike + " " + lastDayPrice + " " + netPL + " " + RRR);
		}
	}
	
	
}
