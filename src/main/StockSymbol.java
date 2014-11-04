package main;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StockSymbol {

    public static String quoteDialog() {
        Object[] options1 = { "Okay", "Cancel" };
        String symbolEntered = "";
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter valid stock symbol (ie: GOOG or YHOO)"));
        JTextField textField = new JTextField(10);
        panel.add(textField);

        int result = JOptionPane.showOptionDialog(null, panel, "Enter valid stock symbol (ie: GOOG or APPL)",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (result == JOptionPane.YES_OPTION){
        	symbolEntered = textField.getText();
        }
        return symbolEntered;
    }
}

