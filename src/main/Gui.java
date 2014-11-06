package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Gui {

	private JFrame frame;
	private JTextField fieldStockSymbol;
	private JTextField fieldExpertOpinion;
	private JTextArea textArea;
	
	private JLabel labelBuy;
	private JLabel labelSell;
	private JLabel labelStrongBuy;
	private JLabel labelStrongSell;
	private JLabel labelHold;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		Fuzzy.loadFIS();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fieldStockSymbol = new JTextField();
		fieldStockSymbol.setBounds(10, 45, 86, 20);
		frame.getContentPane().add(fieldStockSymbol);
		fieldStockSymbol.setColumns(10);
		
		JButton btnStockSymbol = new JButton("Go");
		btnStockSymbol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String quoteInput = fieldStockSymbol.getText().trim();
				if (quoteInput.length() <= 0) return;
				try{
					Stock s = StockFetcher.getStock(quoteInput);
					
					Fuzzy.fis.setVariable("EarningsPerShare", s.getEps());
					Fuzzy.fis.setVariable("ExpertOpinion", s.getEOPEG());
					Fuzzy.fis.setVariable("PricePerEarnings", s.getPe());
					
					Fuzzy.fis.evaluate();
					setTextArea(s);
					setRecommendationArea();
					
				}catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Error fetching information");
				}
			}
		});
		btnStockSymbol.setBounds(104, 44, 89, 23);
		frame.getContentPane().add(btnStockSymbol);
		
		JLabel labelStockSymbol = new JLabel("Enter Stock Symbol (GOOG, YHOO)");
		labelStockSymbol.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelStockSymbol.setBounds(10, 20, 224, 14);
		frame.getContentPane().add(labelStockSymbol);
		
		JLabel labelExpertOpinion = new JLabel("Override Expert Opinion");
		labelExpertOpinion.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelExpertOpinion.setBounds(10, 206, 183, 14);
		frame.getContentPane().add(labelExpertOpinion);
		
		fieldExpertOpinion = new JTextField();
		fieldExpertOpinion.setBounds(10, 231, 86, 20);
		frame.getContentPane().add(fieldExpertOpinion);
		fieldExpertOpinion.setColumns(10);
		
		JButton btnShowCharts = new JButton("Show charts");
		btnShowCharts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fuzzy.fis.chart();
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "No stocks entered!");
				}
			}
		});
		btnShowCharts.setBounds(10, 88, 119, 23);
		frame.getContentPane().add(btnShowCharts);
		
		textArea = new JTextArea();
		textArea.setBounds(259, 20, 165, 231);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		
		JButton btnExpertOpinion = new JButton("Go");
		btnExpertOpinion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String expertInput = fieldExpertOpinion.getText();
				try{
					double input = Double.parseDouble(expertInput);
					if (input > 5 || input < 0) {
						JOptionPane.showMessageDialog(null, "Please enter a value between 0 and 5!"); 
						return;
					}
					Fuzzy.fis.setVariable("ExpertOpinion", input);
					Fuzzy.fis.evaluate();
					setRecommendationArea();
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Something has gone wrong!");
				}
			}
		});
		btnExpertOpinion.setBounds(104, 230, 89, 23);
		frame.getContentPane().add(btnExpertOpinion);
		
		JLabel lblRecommendation = new JLabel("Recommendation");
		lblRecommendation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRecommendation.setBounds(10, 123, 119, 14);
		frame.getContentPane().add(lblRecommendation);
		
		labelStrongBuy = new JLabel("Strong Buy");
		labelStrongBuy.setBounds(10, 141, 86, 14);
		frame.getContentPane().add(labelStrongBuy);
		
		labelBuy = new JLabel("Buy");
		labelBuy.setBounds(10, 164, 46, 14);
		frame.getContentPane().add(labelBuy);
		
		labelStrongSell = new JLabel("Strong Sell");
		labelStrongSell.setBounds(104, 141, 85, 14);
		frame.getContentPane().add(labelStrongSell);
		
		labelSell = new JLabel("Sell");
		labelSell.setBounds(104, 164, 46, 14);
		frame.getContentPane().add(labelSell);
		
		labelHold = new JLabel("Hold");
		labelHold.setBounds(188, 141, 46, 14);
		frame.getContentPane().add(labelHold);
	}
	
	public void setTextArea(Stock s){
		textArea.append("Name: " + s.getName());
		textArea.append("\nStock Symbol: " + s.getSymbol());
		textArea.append("\nCurrent Price: " + s.getPrice());
		textArea.append("\nDay High: " + s.getDayhigh());
		textArea.append("\nDay Low: " + s.getDaylow());
		textArea.append("\n\n52 Week High: " + s.getWeek52high());
		textArea.append("\n52 Week Low: " + s.getWeek52low());
		textArea.append("\n50 Day Moving Avg: " + s.getMovingav50day());
		textArea.append("\n\nPrice per Earnings: " + s.getPe());
		textArea.append("\nEarnings per Share: " + s.getEps());
	}
	
	public void setRecommendationArea(){
		labelStrongBuy.setText("Strong Buy: " + Fuzzy.fis.getVariable("Recommendation").getMembership("strong_buy"));
		labelBuy.setText("Buy: " + Fuzzy.fis.getVariable("Recommendation").getMembership("buy"));
		labelStrongSell.setText("Strong Sell: " + Fuzzy.fis.getVariable("Recommendation").getMembership("strong_sell"));
		labelSell.setText("Sell: " + Fuzzy.fis.getVariable("Recommendation").getMembership("sell"));
		labelHold.setText("Hold: " + Fuzzy.fis.getVariable("Recommendation").getMembership("hold"));
	}
}
