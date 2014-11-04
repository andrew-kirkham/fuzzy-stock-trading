package main;

public class Main {
	public static void main(String args[]){
//
//		String[] eps = new String[] { "small", "big" };
//		String[] eo = new String[] { "high", "low" };
//		String[] ppe = new String[] { "low", "fair", "high", "very_high" };
//		int i=1;
//		for (String e : eps){
//			for (String o : eo){
//				for (String p : ppe){
//					System.out.println("RULE " + i++ + ": IF ExpertOpinion IS " + o + " AND EarningsPerShare IS " + e 
//							+ " AND PricePerEarnings IS " + p + " THEN Recommendation IS ");
//				}
//			}
//		}
		Fuzzy.loadFIS();

		String quoteInput = StockSymbol.quoteDialog();
		Stock s = StockFetcher.getStock(quoteInput);
		
		Fuzzy.fis.setVariable("EarningsPerShare", s.getEps());
		Fuzzy.fis.setVariable("ExpertOpinion", s.getEOPEG());
		Fuzzy.fis.setVariable("PricePerEarnings", s.getPe());
		
		Fuzzy.fis.evaluate();
		Fuzzy.fis.getVariable("Recommendation").chartDefuzzifier(false);
		Fuzzy.fis.chart();
		
	}
}
