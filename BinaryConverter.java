import java.util.ArrayList;

/**
 * translate the asm text to binary text
 */
public class BinaryConverter {

    private static  final int DIVISOR=2;
    private static final int ONE=1;
    private static final int MAX_BINARY_LENGTH=16;

    /** the text written in binary language*/
    private ArrayList<String> binaryLines;

    public BinaryConverter(){
        this.binaryLines= new ArrayList<>();
    }

    /**
     * convert an A instruction
     * @param line a string that represent the decimal number
     */
    public void convertAInstruction(String line){
        String reverseBinaryNumber= convertDecimalToBinary(line); // convert the number
        if(reverseBinaryNumber.length()!=MAX_BINARY_LENGTH) //check if the number isn't 16 bit wide
        {
            reverseBinaryNumber=addZeros(reverseBinaryNumber); //add zeros to the string
        }
        String binaryNumber=reverseNumber(reverseBinaryNumber); // reverse the number
        binaryLines.add(binaryNumber); // add the binary number to the binaryLines output
    }
    /**
     * convert the decimal numbers into binary numbers
     * @param line a string that represent the decimal number
     * @return return a binary number in string format
     */
    private String convertDecimalToBinary(String line){
        int decimal= Integer.parseInt(line); // convert into decimal number
        int remainder;
        String reverseBinaryNumber="";
        // the convert decimal to binary process
        while(decimal!=ONE)
        {
            remainder=decimal%DIVISOR; // modulo by two
            //insert the remainder into the binary number
            reverseBinaryNumber+=String.valueOf(remainder);
            decimal=decimal/DIVISOR; // divide by two
        }
        reverseBinaryNumber+=String.valueOf(ONE); //insert the last digit
        return reverseBinaryNumber;
    }

    /**
     * reverse the converted binary number
     * @param reverseBinaryNumber a string that represent the reverse number
     * @return return a string that represent a proper binary number
     */
    private String reverseNumber(String reverseBinaryNumber){
        String binaryNumber="";
        // reverse the number
        for(int i=reverseBinaryNumber.length();i>0;i--)
        {
            binaryNumber+=reverseBinaryNumber.charAt(i);
        }
        return binaryNumber;

    }

    /**
     * add zeros until the string length is 16
     * @param line a string that represent the reverse number
     * @return return a full 16 bit binary number as string
     */
    private String addZeros(String line){
        for(int i=line.length();i<=MAX_BINARY_LENGTH;i++){
            line+='0';
        }
        return line;
    }
}


