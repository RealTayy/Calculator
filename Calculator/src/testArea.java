public class testArea {

    public String cleanStringFull (String str){
        int length = str.length();

        if (str == null){
            return "-1";
        }

        //returns string of 1 length numbers
        if (length == 1){
            return (str.contains(".") ? "0" : str);
        }

        //Cleans up unneeded "." if there are no decimals numbers
        if (str.substring(length-1, length).contains(".")) {
            str = str.replace(".", "");
        }

        //Cleans up unneeded ".0" if there are no decimals numbers
        if (str.substring(length-2, length).contains(".0")) {
            str = str.replace(".0", "");
        }




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

        //Cleans up unneeded "." if there are no decimals numbers
        if (str.substring(length-1, length).contains(".")) {
            str = str.replace(".", "");
        }

        //Cleans up unneeded ".0" if there are no decimals numbers
        if (str.substring(length-2, length).contains(".0")) {
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

    public static void main(String[] args) {
        testArea blah = new testArea();
        System.out.println(blah.addCommas(blah.cleanDecimals("1121145.1655651156")));
    }
}
