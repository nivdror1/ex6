import java.util.ArrayList;

public class Parser {

    private static final char AT = '@';
    private static final char EQUAL = '=';

    private ArrayList<String> asmLines;

    public Parser(ArrayList<String> asmLines){
        this.asmLines= asmLines;
    }

    public ArrayList<String> getAsmLines(){
        return this.asmLines;
    }
}
