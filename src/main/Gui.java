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

public class Gui {

	private JFrame frame;
	private JTextField fieldStockSymbol;
	private JTextField fieldExpertOpinion;
	private JTextArea textArea;

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
				String quoteInput = fieldStockSymbol.getText();
				if (quoteInput.trim().length() <= 0) return;
				try{
					Stock s = StockFetcher.getStock(quoteInput);
					
					Fuzzy.fis.setVariable("EarningsPerShare", s.getEps());
					Fuzzy.fis.setVariable("ExpertOpinion", s.getEOPEG());
					Fuzzy.fis.setVariable("PricePerEarnings", s.getPe());
					
					Fuzzy.fis.evaluate();
					setTextArea(s);
					
				}catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Error fetching information");
				}
			}
		});
		btnStockSymbol.setBounds(104, 44, 89, 23);
		frame.getContentPane().add(btnStockSymbol);
		
		JLabel labelStockSymbol = new JLabel("Enter Stock Symbol (GOOG, YHOO)");
		labelStockSymbol.setBounds(10, 20, 202, 14);
		frame.getContentPane().add(labelStockSymbol);
		
		JLabel labelExpertOpinion = new JLabel("Override Expert Opinion");
		labelExpertOpinion.setBounds(10, 137, 144, 14);
		frame.getContentPane().add(labelExpertOpinion);
		
		fieldExpertOpinion = new JTextField();
		fieldExpertOpinion.setBounds(10, 162, 86, 20);
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
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Something has gone wrong!");
				}
			}
		});
		btnExpertOpinion.setBounds(104, 161, 89, 23);
		frame.getContentPane().add(btnExpertOpinion);
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
}
