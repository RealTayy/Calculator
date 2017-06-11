import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.math.MathContext;

public class testArea {

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

/*bPlus.addActionListener(e -> {
        // Do nothing if there is nothing to add to yet
        if (currentInput == null) {
            return;
        }

        //If leftOperand already has numbers in is assign currentInput to rightOperand Instead
        if (leftOperand == null) {
            leftOperand = currentInput;
        } else {
            rightOperand = currentInput;
        }

        //performs function if left and right OPs exist
        if(leftOperand != null && rightOperand != null){
            //Creates BD for left and right OP then applys function
            BigDecimal leftBD = new BigDecimal(leftOperand);
            BigDecimal rightBD = new BigDecimal(rightOperand);
            BigDecimal resultBD = leftBD.add(rightBD);
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
        }

        //Sets text for equation area
        if (equationTextArea == null) {
            equationTextArea.setText(currentInput + " + ");
        } else {
            equationTextArea.setText(equationTextArea.getText() + currentInput + " + ");
        }
        currentInput = null;

    });*/

    public static void main(String[] args) {
        String cleanNumberPattern = "0.0000000000E0";
        Double number = 11223344556677889900.0;
        DecimalFormat cleanNumber = new DecimalFormat(cleanNumberPattern);
        System.out.println(cleanNumber.format(Double.valueOf(number)));

/*        String num1 = "11111111.0";
        double num2 = .2;
        double num3 = .3;
        //System.out.println(Double.toString(num1) + " + " + Double.toString(num2) + " + " + Double.toString(num3) + " = " + (num1+num2+num3));
        BigDecimal a = new BigDecimal(num1);
        BigDecimal b = new BigDecimal(num2);
        BigDecimal c = new BigDecimal(num3);

        System.out.println(num1);*/







    }
}
