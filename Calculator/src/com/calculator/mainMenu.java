/* TO THE TO DO LIST!
    cleanDecimals needs to be able to fix shit like 21.0000000 kek
    DECIMALS SUPER FUCKED UP LOL!
    BUILD NEW NEGATIVE THING! do an on/off switch?
 */
package com.calculator;

import javax.swing.*;
import java.awt.event.*;

public class mainMenu {
    private JPanel mainPane;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b7;
    private JButton b8;
    private JButton b9;
    private JButton b0;

    private JButton bDecimal;
    private JButton bSign;

    private JButton bBackspace;
    private JButton bClear;
    private JButton bClearEntry;

    private JButton bPlus;
    private JButton bMinus;
    private JButton bTimes;
    private JButton bDivide;
    private JButton bEqual;

    private JTextArea inputTextArea;
    private JTextPane equationTextArea;
    private JTextArea historyText;
    private JPanel outputPane;
    private JPanel historyPanee;

    private Double leftOperand;
    private Double rightOperand;
    private Double currentResults;
    private String currentInput;

    public mainMenu() {
        //Add Mouse Listeners for Numbers
        b1.addActionListener(new numberButtonClicked(b1.getText()));
        b2.addActionListener(new numberButtonClicked(b2.getText()));
        b3.addActionListener(new numberButtonClicked(b3.getText()));
        b4.addActionListener(new numberButtonClicked(b4.getText()));
        b5.addActionListener(new numberButtonClicked(b5.getText()));
        b6.addActionListener(new numberButtonClicked(b6.getText()));
        b7.addActionListener(new numberButtonClicked(b7.getText()));
        b8.addActionListener(new numberButtonClicked(b8.getText()));
        b9.addActionListener(new numberButtonClicked(b9.getText()));
        b0.addActionListener(new numberButtonClicked(b0.getText()));

        bDecimal.addActionListener(e -> {
            //Deletes decimal if already exist
            if(currentInput != null && currentInput.contains(".")){
                StringBuilder sbInput = new StringBuilder(currentInput);

                //Deletes 0 at beginning if it exist for aesthetics
                if(sbInput.charAt(0) == '0') {
                    sbInput.deleteCharAt(0);
                }

                sbInput.deleteCharAt(sbInput.indexOf("."));
                currentInput = sbInput.toString();
            }

            //Sets text to have decimal at end
            currentInput = (currentInput == null) ? "0." : currentInput + ".";
            inputTextArea.setText(addCommas(currentInput));
        });

        bSign.addActionListener(e -> {
            StringBuilder sbInput = new StringBuilder(currentInput);
            if (currentInput ==null){
                currentInput = "-";
            }
            else if (sbInput.charAt(0) == '-') {
                sbInput.deleteCharAt(0);
            } else {
                sbInput.insert(0,"-");
            }
            currentInput = sbInput.toString();
            inputTextArea.setText(addCommas(cleanDecimals(currentInput)));
        });

        bBackspace.addActionListener(e -> {
            currentInput = currentInput.replaceFirst(".$","");
            inputTextArea.setText(addCommas(cleanDecimals(currentInput)));
        });

        bClearEntry.addActionListener(e -> {
            currentInput = null;
            inputTextArea.setText("0");
        });

        bClear.addActionListener(e -> {
            leftOperand = null;
            rightOperand = null;
            currentInput = null;
            currentResults = null;
            inputTextArea.setText("0");
            equationTextArea.setText("");
        });

        bPlus.addActionListener(e -> {
            //
            if (currentInput == null) {
                currentInput = "0";
            }

            //If leftOperand already has numbers in is assign currentInput to rightOperand Instead
            if (leftOperand == null) {
                leftOperand = Double.parseDouble(currentInput);
            } else {
                rightOperand = Double.parseDouble(currentInput);
            }

            //Adds left and right OPs if they exist
            if(leftOperand != null && rightOperand != null){
                currentResults = leftOperand + rightOperand;
                //!!! DELETE THE LINE BELOW THIS WHEN DONE
                historyText.setText("Left: " + leftOperand + "\n" + "Right: " + rightOperand +"\n" + "Result:" + currentResults);
                leftOperand = currentResults;
                inputTextArea.setText(cleanDecimals(Double.toString(currentResults)));
            }

            //Sets text for equation area
            if (equationTextArea == null) {
                equationTextArea.setText(cleanDecimals(currentInput) + " + ");
            } else {
                equationTextArea.setText(equationTextArea.getText() + cleanDecimals(currentInput) + " + ");
            }
            currentInput = null;

        });

    }

    public String cleanDecimals (String str){
        int length = str.length();

        if (str == null){
            return "-1";
        }

        //returns string of 1 length numbers
        if (length == 1){
            return (str.contains(".") ? "0" : str);
        }

        //Cleans up unneeded "." if there are no decimal numbers
        if (str.substring(length-1, length).contains(".")) {
            str = str.replace(".", "");
        //Cleans up unneeded ".0" if there are no decimal numbers
        } else if (str.substring(length-2, length).contains(".0")) {
            str = str.replace(".0", "");
        }

        return str;
    }

    public String addCommas (String str){int length = str.length();
        StringBuilder sbString = new StringBuilder(str);
        int decimalIndex = (str.contains(".")) ? sbString.indexOf(".") : sbString.length(); //finds position of "." if there... if not return length
        int lengthInteger = sbString.substring(0, decimalIndex).length(); //finds length of integer to the left of decimal
        int commaNumber = (lengthInteger % 3 == 0) ? (lengthInteger /3) -1 : lengthInteger / 3; //finds number of commas needed
        int extraNumbers = (lengthInteger % 3 == 0) ? 3 : lengthInteger % 3; //finds number of digits to the left of last comma

        //Add commas in correct spots
        for (int i = 0; i < commaNumber; i++){
            sbString.insert((i*4)+extraNumbers,',');
        }

        return sbString.toString();
    }

    private class numberButtonClicked implements ActionListener {

        private String value;

        public numberButtonClicked(String value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (value == "0" && currentInput == null){
                //Do nothing if user tries to type 0 while there are no other numbers in input field
            } else {
                currentInput = (currentInput == null) ? value : currentInput + value;
                inputTextArea.setText(addCommas(currentInput));
            }
        }
    }

    public static void main(String[] args) {
      JFrame frame = new JFrame("App");
      frame.setContentPane(new mainMenu().mainPane);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
    }
}