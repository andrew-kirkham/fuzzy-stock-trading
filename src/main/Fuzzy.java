package main;
import net.sourceforge.jFuzzyLogic.FIS;

public class Fuzzy {
	public static FIS fis;
	public static void loadFIS(){
		String filename = "C:\\Users\\REDBULL\\workspace\\FuzzyLogic\\src\\StockFIS.fcl";
		fis = FIS.load(filename,true);
	}
}
