package main;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String args[]){
		Fuzzy.loadFIS();

		String quoteInput = StockSymbol.quoteDialog();
		Stock s = StockFetcher.getStock(quoteInput);
		
		Fuzzy.fis.setVariable("EarningsPerShare", s.getEps());
		Fuzzy.fis.setVariable("ExpertOpinion", s.getEOPEG());
		Fuzzy.fis.setVariable("PricePerEarnings", s.getPe());
		
		Fuzzy.fis.evaluate();
		Fuzzy.fis.getVariable("Recommendation").chartDefuzzifier(false);
		Fuzzy.fis.chart();
		
		double r = Fuzzy.fis.getVariable("Recommendation").defuzzify();
		JOptionPane.showMessageDialog(null, "Your recommendation value is " + r);
		System.exit(0);
	}
}
