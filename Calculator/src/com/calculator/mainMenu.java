/* TO THE TO DO LIST!
    BUILD NEW NEGATIVE THING! do an on/off switch?
    add ability to do 9++++++++++++++++++
 */
package com.calculator;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    private String leftOperand;
    private String rightOperand;
    private String currentResults;
    private String currentInput;
    private String lastResult;
    private String currentOperation;
    private String lastOperation;

    private boolean lastOpEqual = false;

    StringBuilder ongoingOperation = new StringBuilder();

    //Formats for text input area depending on length of string
    String formatA = "###,###.###############";
    String formatE = "0.0000000000000#E0";
    DecimalFormat currentFormat = new DecimalFormat(formatA);

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

        //Add Mouse Listeners for Operation Buttons
        bPlus.addActionListener(new operationButtonClicked("+"));
        bMinus.addActionListener(new operationButtonClicked("-"));
        bDivide.addActionListener(new operationButtonClicked("/"));
        bTimes.addActionListener(new operationButtonClicked("*"));

        bEqual.addActionListener(e -> {
            currentOperation = "=";

            //performs last operation if it exist and there is no new input
            if (currentInput == null && lastOperation != null) {
                if (lastOperation != null && rightOperand != null) {
                    performOperation(lastOperation);
                    equationTextArea.setText(lastOperation + " " + rightOperand);
                }
                return;
            }

            // Do nothing if there is nothing to add to yet
            if (currentInput == null){
                return;
            }

            //If leftOperand already has numbers in its assign currentInput to rightOperand Instead
            if (leftOperand == null) {
                leftOperand = currentInput;
            } else {
                rightOperand = currentInput;
            }

            //performs operation if left and right OPs exist
            if(leftOperand != null && rightOperand != null){
                performOperation(lastOperation);
                equationTextArea.setText(null);
            }

            lastOpEqual = true;
            currentInput = null;




        });

        bDecimal.addActionListener(e -> {
            //If currentInput is 0. then it keeps it at 0.
            if (currentInput == "0."){
                return;
            }

            if(currentInput != null && currentInput.contains(".")){
                StringBuilder sbInput = new StringBuilder(currentInput);

                //Deletes 0 at beginning if it exist for aesthetics
                if(sbInput.charAt(0) == '0') {
                    sbInput.deleteCharAt(0);
                }

                //Deletes decimal if already exist
                sbInput.deleteCharAt(sbInput.indexOf("."));
                currentInput = sbInput.toString();
            }

            //Sets text to have decimal at end
            currentInput = (currentInput == null) ? "0." : currentInput + ".";
            inputTextArea.setText(currentFormat.format(Double.parseDouble(currentInput)) + ".");
        });

        bSign.addActionListener(e -> {
            StringBuilder sbInput;

            //If there is no current input does nothing
            if (currentInput == null){
                return;
            } else {
                sbInput = new StringBuilder(currentInput);
            }

            //Takes on or takes off '-' if there is a current input
            if (sbInput.charAt(0) == '-') {
                if (sbInput.length() == 1) {
                    //if the only input is currently '-' changes display back to '0'
                    sbInput.setCharAt(0, '0');
                    currentInput = null;
                    inputTextArea.setText("0");
                    return;
                } else {
                    //delete '-' if it is there
                    sbInput.deleteCharAt(0);
                }
            } else {
                //inserts '-' if it isn't there
                sbInput.insert(0,"-");
            }

            //Sets display text
            currentInput = sbInput.toString();
            BigDecimal tempBD = new BigDecimal(currentInput);
            inputTextArea.setText(currentFormat.format(tempBD));
        });

        bBackspace.addActionListener(e -> {
            if (currentInput == null) {
                return;
            } else if (currentInput != null && currentInput.length() == 1) {
                currentInput = null;
                inputTextArea.setText("0");
            } else if (currentInput != null) {
                currentInput = currentInput.replaceFirst(".$","");
                inputTextArea.setText(currentFormat.format(Double.parseDouble(currentInput)));
            } else {
                inputTextArea.setText("-1");
            }

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
            lastResult = null;
            currentOperation = null;
            lastOperation = null;
            inputTextArea.setText("0");
            equationTextArea.setText("");
        });

    }

    private class numberButtonClicked implements ActionListener {

        private String value;

        public numberButtonClicked(String value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentInput != null && currentInput.length() >=15){
                //Do nothing when reaching max length
                return;
            }

            if (value == "0" && currentInput == null){
                //Do nothing if user tries to type 0 while there are no other numbers in input field
            } else {
                currentInput = (currentInput == null) ? value : currentInput + value;
                BigDecimal currentBD = new BigDecimal(currentInput);
                //If a decimal exist and user tries to type 0 afterwards. This displays the ".0" that would otherwise be cleaned up by DecimalFormat.
                //Else displays as normal
                if (currentInput.contains(".") && value == "0"){
                    inputTextArea.setText(currentFormat.format(currentBD) + ".0");
                } else {
                    inputTextArea.setText(currentFormat.format(currentBD));
                }



            }
        }
    }

    private class operationButtonClicked implements ActionListener {

        private String currentOperation;

        public operationButtonClicked (String operation){
            this.currentOperation = operation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Do nothing if there is nothing to add to yet
            if ((currentInput == null && lastResult == null) && lastOperation == null) {
                return;
            }


            //Sets text for equation area
            if (lastOpEqual == true) {
                // replaces currentInput with lastResult for ongoing equations EX. you hit 1 + 2 then hit = THEN hit + 3 it will display the result.
                equationTextArea.setText(lastResult + " " + currentOperation + " ");
                lastOpEqual = false;
            }else if (currentInput == null){
                //This step happens when you are trying to change your operation with no currentInput
                //Change the operation sign in the equationTextArea to new operation
                StringBuilder tempSB = new StringBuilder(equationTextArea.getText());
                tempSB.delete(tempSB.length() - 2, tempSB.length());
                equationTextArea.setText(tempSB.toString() + currentOperation + " ");
            } else {
                //Prints out the last calculation on equationTextArea
                equationTextArea.setText(equationTextArea.getText() + currentInput + " " + currentOperation + " ");
            }

            //If leftOperand already has numbers in it. Assign currentInput to rightOperand Instead
            if (leftOperand == null) {
                leftOperand = currentInput;
            } else {
                rightOperand = currentInput;
            }

            //Performs operation if left and right OPs exist
            if(leftOperand != null && rightOperand != null){
                performOperation(lastOperation);
            }

            lastOperation = currentOperation;
            currentInput = null;

        }
    }

    private void performOperation (String lastOperation){
        BigDecimal leftBD = new BigDecimal(leftOperand);
        BigDecimal rightBD = new BigDecimal(rightOperand);
        BigDecimal resultBD = new BigDecimal(0);
        //Apply function according to operation
        switch (lastOperation){
            case "+" :
                resultBD = leftBD.add(rightBD);
                break;
            case "-" :
                resultBD = leftBD.subtract(rightBD);
                break;
            case "/" :
                resultBD = leftBD.divide(rightBD, 15 , RoundingMode.FLOOR);
                break;
            case "*" :
                resultBD = leftBD.multiply(rightBD);
                break;
        }
        currentResults = resultBD.toString();
        leftOperand = currentResults;

        //Returns result in input area in correct format
        if (currentFormat.format(resultBD).length() > 18) {
            currentFormat.applyPattern(formatE);
            inputTextArea.setText(currentFormat.format(resultBD));
            currentFormat.applyPattern(formatA);
        } else {
            inputTextArea.setText(currentFormat.format(resultBD));
        }

        //creates lastResult if doing "=" operations
        if (currentOperation == "=") {
            lastResult = resultBD.toString();
        } else {
            //cleans up rightOperand for combo equations but keeps it if you want to spam =======
            rightOperand = null;
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