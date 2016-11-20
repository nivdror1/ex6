import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final String AT = "^@{1}";
    private static final String ONE_LINER_COMMENT ="^/{2}";
    private static final String EMPTY_LINE= "^\\s*+$";
    private static final String DIGITS= "^\\d++";
    private static final String REGISTERS= "(A|D|M|MD|AM|AD|AMD)={1}"; //todo missing null(000) could it be reverse?
    private static final String JUMP_REGISTERS= "(0|D|M);{1}";
    private static final String COMPUTE = "0|1|-1|D|A|!D|!A|-D|-A|D\\+1|A\\+1|D-1|A-1|D\\+A|D-A|A-D|D&A|" +
            "D\\|A|M|!M|-M|M\\+1|M-1|D\\+M|D-M|M-D|D&M|D\\|M";
    private static final String JUMP= "JGT|JEQ|JGE|JLT|JNE|JLE|JMP";
    private static final Pattern COMPUTE_PATTERN = Pattern.compile(COMPUTE);
    private static final Pattern JUMP_REGISTERS_PATTERN= Pattern.compile(JUMP_REGISTERS);
    private static final Pattern JUMP_PATTERN= Pattern.compile(JUMP);
    private static final Pattern REGISTERS_PATTERN= Pattern.compile(REGISTERS);
    private static final Pattern DIGITS_PATTERN= Pattern.compile(DIGITS);
    private static final Pattern EMPTY_LINE_PATTERN= Pattern.compile(EMPTY_LINE);
    private static final Pattern AT_PATTERN= Pattern.compile(AT);
    private static final Pattern COMMENT_PATTERN= Pattern.compile(ONE_LINER_COMMENT);

    private static final String ASSIGNMENT="assignment";
    private static final String JUMP_INSTRUCTION="JUMP";

    /** the text written in hack assembly language*/
    private ArrayList<String> asmLines;
    /** the current matcher*/
    private Matcher curMatcher;
    /** the current destination register for the C instruction*/
    private String curRegisters;
    /** the current compute instruction*/
    private String curInstruction;
    /** the current jump instruction*/
    private String curJump;
    /** an object which convert an asm line to binary line*/
    private BinaryConverter convert;
    /**
     * a constructor
     */
    public Parser(ArrayList<String> asmLines){
        this.asmLines= asmLines;
        this.convert= new BinaryConverter();
    }

    /**
     * get the input text
     * @return the asmLines
     */
    public ArrayList<String> getAsmLines(){
        return this.asmLines;
    }

    /**
     * parse the asm file - that include the first and second pass
     * @return
     */
    public void parseAsmFile()
    {
        //todo first pass (deal with labels)
        // the second pass
        for(int i=0;i<asmLines.size();i++)
        {
            if(deleteBlankLines(asmLines.get(i))) //check for a blank line
            {
                continue;
            }else if(deleteOneLinerComment(asmLines.get(i))) // check for a comment
            {
                continue;
            }else if(dealWithAInstruction(asmLines.get(i))) // check for a A instruction
            {
                continue;
            }
            else{
                dealWithCInstruction(asmLines.get(i));
            }

        }
        //todo a instruction, c instruction
    }


    /**
     * check if the line is blank
     * @param line a string that represent the specific line in the asm file
     * @return return true if it was a blank line, false otherwise
     */
    private boolean deleteBlankLines(String line){
        Matcher m=EMPTY_LINE_PATTERN.matcher(line);
        return m.find(); // check for an empty line

    }

    /**
     * check if the line is a comment
     * @param line a string that represent the specific line in the asm file
     * @return return true if it was a comment, false otherwise
     */
    private boolean deleteOneLinerComment(String line)
    {
        Matcher m= COMMENT_PATTERN.matcher(line);
        return m.lookingAt();
    }

    /**
     * parse A instruction
     * @param line a string that represent the specific line in the asm file
     * @return true if it was a A instruction , false otherwise
     */
    private boolean dealWithAInstruction(String line){
        Matcher m= AT_PATTERN.matcher(line);
        if(m.lookingAt()) //check if the line starts with the char '@'
        {
            line=line.substring(m.end()); // delete the char '@'
            m=DIGITS_PATTERN.matcher(line);
            if(m.lookingAt()) // check for digits that represent a location in the memory
            {
                line=line.substring(0,m.end()); //delete the comment if it exists after the number
                convert.convertAInstruction(line); //convert to binary
                return true;
            }
            //todo to address the label/symbol problem
        }
        return false;
    }

    private void dealWithCInstruction(String line){
        this.curMatcher= REGISTERS_PATTERN.matcher(line);
        if(this.curMatcher.find()) //check for the destination registers
        {
            this.curRegisters=line.substring(0,curMatcher.end()-2); //the registers string
            line=line.substring(curMatcher.end()); // advance to the compute instruction
            this.curMatcher= COMPUTE_PATTERN.matcher(line);
            if(this.curMatcher.lookingAt()) //check for the compute instruction
            {
                this.curInstruction=line.substring(0,this.curMatcher.end()-1);
                convert.convertCInstruction(curRegisters,curInstruction,ASSIGNMENT);
                //todo send curRegister and curInstruction to convert to binary
            }
        }else
            {
            this.curMatcher= JUMP_REGISTERS_PATTERN.matcher(line);
            if(this.curMatcher.find())
            {
                this.curRegisters=line.substring(0,curMatcher.end()-1);
                line=line.substring(curMatcher.end()); // advance to the jump instruction
                this.curMatcher= JUMP_PATTERN.matcher(line);
                if(this.curMatcher.lookingAt()){
                    this.curJump=line.substring(0,this.curMatcher.end()-1);
                    //todo send curRegister and curInstruction to convert to binary
                }
                //todo suppose to find here the jump bits-maybe do a lot of switch instead of regex
            }
        }

    }
}
