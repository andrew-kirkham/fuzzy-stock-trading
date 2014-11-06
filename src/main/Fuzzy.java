package main;
import net.sourceforge.jFuzzyLogic.FIS;

public class Fuzzy {
	public static FIS fis;
	public static void loadFIS(){
		String filename = "StockFIS.fcl";
		fis = FIS.load(filename,true);
	}
}
