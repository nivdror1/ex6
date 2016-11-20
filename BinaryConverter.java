import java.util.ArrayList;

/**
 * translate the asm text to binary text
 */
public class BinaryConverter {

    private static final int DIVISOR=2;
    private static final int ONE=1;
    private static final int MAX_BINARY_LENGTH=16;

    private static final String C_START_BITS="111";
    private static final String NO_JUMP="000";
    //the destination registers
    private static final String D_REGISTER= "D";
    private static final String A_REGISTER= "A";
    private static final String M_REGISTER= "M";
    private static final String MD_REGISTER= "MD";
    private static final String AM_REGISTER= "AM";
    private static final String AD_REGISTER= "AD";
    private static final String AMD_REGISTER= "AMD";

    // the binary representation of the destination registers
    private static final String M_BINARY_REPR="001";
    private static final String D_BINARY_REPR="010";
    private static final String MD_BINARY_REPR="011";
    private static final String A_BINARY_REPR="100";
    private static final String AM_BINARY_REPR="101";
    private static final String AD_BINARY_REPR="110";
    private static final String AMD_BINARY_REPR="111";

    // the jump instruction
    private static final String JGT="JGT";
    private static final String JEQ="JEQ";
    private static final String JGE="JGE";
    private static final String JLT="JLT";
    private static final String JNE="JNE";
    private static final String JLE="JLE";
    private static final String JMP="JMP";

    // the binary representation of the jump instruction
    private static final String JGT_BINARY_REPR="001";
    private static final String JEQ_BINARY_REPR="010";
    private static final String JGE_BINARY_REPR="011";
    private static final String JLT_BINARY_REPR="100";
    private static final String JNE_BINARY_REPR="101";
    private static final String JLE_BINARY_REPR="110";
    private static final String JMP_BINARY_REPR="111";

    // the compute instruction
    private static final String CONSTANT_ZERO="0";
    private static final String CONSTANT_ONE="1";
    private static final String CONSTANT_MINUS_ONE="-1";
    private static final String D="D";
    private static final String NOT_D="!D";
    private static final String NOT_A="!A";
    private static final String A="A";
    private static final String MINUS_D="-D";
    private static final String MINUS_A="-A";
    private static final String D_PLUS_ONE="D+1";
    private static final String A_PLUS_ONE="A+1";
    private static final String D_MINUS_ONE="D-1";
    private static final String A_MINUS_ONE="A-1";
    private static final String D_PLUS_A="D+A";
    private static final String D_MINUS_A="D-A";
    private static final String A_MINUS_D="A-D";
    private static final String D_AND_A="D&A";
    private static final String D_OR_A="D|A";
    private static final String M="M";
    private static final String NOT_M="!M";
    private static final String MINUS_M="-M";
    private static final String M_PLUS_ONE="M+1";
    private static final String M_MINUS_ONE="M-1";
    private static final String D_PLUS_M="D+M";
    private static final String D_MINUS_M="D-M";
    private static final String M_MINUS_D="M-D";
    private static final String D_AND_M="D&M";
    private static final String D_OR_M="D|M";

    // the binary representation of the compute instructions
    private static final String CONSTANT_ZERO_BINARY="0101010";
    private static final String CONSTANT_ONE_BINARY="0111111";
    private static final String CONSTANT_MINUS_ONE_BINARY="0111010";
    private static final String D_BINARY="0001100";
    private static final String A_BINARY="0110000";
    private static final String NOT_D_BINARY="0001101";
    private static final String NOT_A_BINARY="0110001";
    private static final String MINUS_D_BINARY="0001111";
    private static final String MINUS_A_BINARY="0110011";
    private static final String D_PLUS_ONE_BINARY="0011111";
    private static final String A_PLUS_ONE_BINARY="0110111";
    private static final String D_MINUS_ONE_BINARY="0001110";
    private static final String A_MINUS_ONE_BINARY="0110010";
    private static final String D_PLUS_A_BINARY="0000010";
    private static final String D_MINUS_A_BINARY="0010011";
    private static final String A_MINUS_D_BINARY="0000111";
    private static final String D_AND_A_BINARY="0000000";
    private static final String D_OR_A_BINARY="0010101";
    private static final String M_BINARY="1110000";
    private static final String NOT_M_BINARY="1110001";
    private static final String MINUS_M_BINARY="1110011";
    private static final String M_PLUS_ONE_BINARY="1110111";
    private static final String M_MINUS_ONE_BINARY="1110010";
    private static final String D_PLUS_M_BINARY="1000010";
    private static final String D_MINUS_M_BINARY="1010011";
    private static final String M_MINUS_D_BINARY="1000111";
    private static final String D_AND_M_BINARY="1000000";
    private static final String D_OR_M_BINARY="1010101";








    /** the text written in binary language*/
    private ArrayList<String> binaryLines;
    /** the current binary line*/
    private String curLine;

    public BinaryConverter(){
        this.binaryLines= new ArrayList<>();
    }

    public ArrayList<String> getBinaryLines(){
        return this.binaryLines;
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

    /**
     * convert the c instruction written in asm into binary code
     * @param register a representation of the dest registers
     * @param instruction a representation of the compute instruction
     */
    public void convertCInstruction(String register, String instruction,String jump){
        this.curLine+=C_START_BITS; // add the start of the c instruction in bits
        convertComputeToBits(instruction); // todo i need to understand if the compute bits are zero when jump occurs
        convertDestToBits(register); // convert the dest registers into binary code
        if(jump!=null){
            convertJumpToBits(jump); //convert the jump instructions into binary code
        }else{
            this.curLine+=NO_JUMP; //add 000 to the end of the c assignment instruction
        }
        this.binaryLines.add(this.curLine); // add to the binary code output
    }

    /**
     * convert the dest registers into binary code
     * @param register the dest register/s
     */
    private void convertDestToBits(String register){
        //todo i didn't address to the null situation
        if(register.equals(M_REGISTER)){
            this.curLine+= M_BINARY_REPR;
        }else if(register.equals(D_REGISTER)){
            this.curLine+=D_BINARY_REPR;
        }else if(register.equals(MD_REGISTER)){
            this.curLine+=MD_BINARY_REPR;
        }else if(register.equals(A_REGISTER)){
            this.curLine+=A_BINARY_REPR;
        }else if(register.equals(AM_REGISTER)){
            this.curLine+=AM_BINARY_REPR;
        }else if(register.equals(AD_REGISTER)){
            this.curLine+=AD_BINARY_REPR;
        }else if(register.equals(AMD_REGISTER)){
            this.curLine+=AMD_BINARY_REPR;
        }
    }

    /**
     * convert the jump instructions into binary code
     * @param jump a representation of the jump instruction in asm code
     */
    private void convertJumpToBits(String jump){
        if(jump.equals(JGT)){
            this.curLine+=JGT_BINARY_REPR;
        }else if (jump.equals(JEQ)){
            this.curLine+=JEQ_BINARY_REPR;
        }else if (jump.equals(JGE)){
            this.curLine+=JGE_BINARY_REPR;
        }else if (jump.equals(JLT)){
            this.curLine+=JLT_BINARY_REPR;
        }else if (jump.equals(JNE)){
            this.curLine+=JNE_BINARY_REPR;
        }else if (jump.equals(JLE)){
            this.curLine+=JLE_BINARY_REPR;
        }else if (jump.equals(JMP)){
            this.curLine+=JMP_BINARY_REPR;
        }
    }

    /**
     * convert the compute instructions into binary code
     * @param instruction a representation of the compute instruction in asm code
     */
    private void convertComputeToBits(String instruction){
        if(instruction.equals(CONSTANT_ZERO)){
            this.curLine+=CONSTANT_ZERO_BINARY;
        }else if(instruction.equals(CONSTANT_ONE)){
            this.curLine+=CONSTANT_ONE_BINARY;
        }else if(instruction.equals(CONSTANT_MINUS_ONE)){
            this.curLine+=CONSTANT_MINUS_ONE_BINARY;
        }else if(instruction.equals(D)){
            this.curLine+=D_BINARY;
        }else if(instruction.equals(A)){
            this.curLine+=A_BINARY;
        }else if(instruction.equals(NOT_D)){
            this.curLine+=NOT_D_BINARY;
        }else if(instruction.equals(NOT_A)){
            this.curLine+=NOT_A_BINARY;
        }else if(instruction.equals(MINUS_D)){
            this.curLine+=MINUS_D_BINARY;
        }else if(instruction.equals(MINUS_A)){
            this.curLine+=MINUS_A_BINARY;
        }else if(instruction.equals(D_PLUS_ONE)){
            this.curLine+=D_PLUS_ONE_BINARY;
        }else if(instruction.equals(A_PLUS_ONE)){
            this.curLine+=A_PLUS_ONE_BINARY;
        }else if(instruction.equals(D_MINUS_ONE)){
            this.curLine+=D_MINUS_ONE_BINARY;
        }else if(instruction.equals(A_MINUS_ONE)){
            this.curLine+=A_MINUS_ONE_BINARY;
        }else if(instruction.equals(D_PLUS_A)){
            this.curLine+=D_PLUS_A_BINARY;
        }else if(instruction.equals(D_MINUS_A)){
            this.curLine+=D_MINUS_A_BINARY;
        }else if(instruction.equals(A_MINUS_D)){
            this.curLine+=A_MINUS_D_BINARY;
        }else if(instruction.equals(D_AND_A)){
            this.curLine+=D_AND_A_BINARY;
        }else if(instruction.equals(D_OR_A)){
            this.curLine+=D_OR_A_BINARY;
        }else if(instruction.equals(M)){
            this.curLine+=M_BINARY;
        }else if(instruction.equals(NOT_M)){
            this.curLine+=NOT_M_BINARY;
        }else if(instruction.equals(MINUS_M)){
            this.curLine+=MINUS_M_BINARY;
        }else if(instruction.equals(M_PLUS_ONE)){
            this.curLine+=M_PLUS_ONE_BINARY;
        }else if(instruction.equals(M_MINUS_ONE)){
            this.curLine+=M_MINUS_ONE_BINARY;
        }else if(instruction.equals(D_PLUS_M)){
            this.curLine+=D_PLUS_M_BINARY;
        }else if(instruction.equals(D_MINUS_M)){
            this.curLine+=D_MINUS_M_BINARY;
        }else if(instruction.equals(M_MINUS_D)){
            this.curLine+=M_MINUS_D_BINARY;
        }else if(instruction.equals(D_AND_M)){
            this.curLine+=D_AND_M_BINARY;
        }else if(instruction.equals(D_OR_M)){
            this.curLine+=D_OR_M_BINARY;
        }

    }
}


