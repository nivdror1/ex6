import java.io.*;
import java.util.ArrayList;

/** the purpose of this class is to read and write the IO*/
public class IOReader {
    private static final String IO_ERROR_MESSAGE= "IO error has happened";
    private static final String FILE_NOT_EXISTS="enter another file, since the path was wrong";

    /** the main method which control the progress of the assembler*/
    public static void main(String[] args)
    {
        //todo check what is the input( xxx.asm or dir or another file)
        File file= new File(args[0]);
        if(file.exists()) // check if the file exists
        {
            try (FileReader commandFile = new FileReader(file);// define the BufferReader
                BufferedReader reader =new BufferedReader(commandFile)){

                ArrayList<String> lines; //define the container for the input
                Parser parser= new Parser(); //define a new parser
                String text;

                while((text= reader.readLine())!=null) // add the lines to the container
                {
                    parser.getAsmLines().add(text);
                }
                parser.parseAsmFile(); // parse the asm text
                parser.getBinaryOutput(); // get the binary text
                //todo output the binary text

            }
            catch(IOException e){
                System.out.println(IO_ERROR_MESSAGE);
            }
        }
        else{
            System.out.println(FILE_NOT_EXISTS);
        }
    }
}
