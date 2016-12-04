import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** the purpose of this class is to read and write the IO*/
public class IOReader {
    private static final String IO_ERROR_MESSAGE= "IO error has happened";
    private static final String FILE_NOT_EXISTS="enter another file, since the path was wrong";
    private static final String HACK="hack";
    private static final String DOT="\\w++\\.";
    private static final Pattern DOT_PATTERN= Pattern.compile(DOT);
    private static final String ASM= DOT+"asm";
    private static final Pattern ASM_PATTERN =Pattern.compile(ASM);


    /** the main method which control the progress of the assembler*/
    public static void main(String[] args)
    {
        File file= new File(args[0]);
        if(file.exists()) // check if the file exists
        {
            //check if it is a file or a directory and get all the appropriate files
            ArrayList<File> asmList = fileOrDirectory(file);
            for (int j = 0; j < asmList.size(); j++) {
                dealWithASingleFile(asmList.get(j)); // read, parse an asm file and write to hack file
            }
        }
        else{
            System.out.println(FILE_NOT_EXISTS);
        }
    }

    /**
     * get the name of the asm file and exchange it to hack file
     * @param inputFileName the input file name
     * @return the file name but with a suffix of a hack file
     */
    private static String setOutputFileName(String inputFileName){
        //return inputFileName.substring(0, inputFileName.length()-3) + HACK;
        Matcher m= DOT_PATTERN.matcher(inputFileName);
        if(m.lookingAt()){ // find the dot char
            inputFileName= inputFileName.substring(0,m.end()); // delete the asm suffix
            return inputFileName+HACK; //add the hack suffix
        }
        return "didn't find the dot"; //todo maybe do an exception if not write something meaningful

    }

    /**
     * read and parse a specific file
     * @param reader a bufferReader
     * @param parser a parser
     * @throws IOException
     */
    private static void readAndParse(BufferedReader reader, Parser parser) throws IOException {
        String text;
        while ((text = reader.readLine()) != null) // add the lines to the container
        {
            parser.getAsmLines().add(text);
        }
        parser.parseAsmFile(); // parse the vm text
    }

    /**
     * check if the input file is an actual file or a directory - filter only the files that ends with ".asm"
     * @param file a file
     * @return a array list of the files that were found
     */
    private static ArrayList<File> fileOrDirectory(File file) {
        File[] filesArray;
        ArrayList<File> asmFiles = new ArrayList<>();
        // check if it a file
        if (file.isFile())
        {
            asmFiles.add(file);
        } else if (file.isDirectory()) //check for a directory
        {
            //create a filter that specifies the suffix of the file
            filesArray = file.listFiles(); // create an array of all the files in the directory

            for (int i = 0; i < filesArray.length; i++) {
                Matcher m = ASM_PATTERN.matcher(filesArray[i].getName());
                if (m.lookingAt()) { // find the dot char
                    asmFiles.add(filesArray[i]);
                }
            }
        }
        return asmFiles;
    }

    /**
     * read from a specific file and write to another
     *@param file an asm file
     */
    private static void dealWithASingleFile(File file){
        String outputFileName = setOutputFileName(file.getName());

        try (FileReader asmFile = new FileReader(file);// define the BufferReader and BufferWriter
             BufferedReader reader = new BufferedReader(asmFile);
             FileWriter hackFile = new FileWriter((outputFileName));
             BufferedWriter writer = new BufferedWriter(hackFile)) {

            Parser parser = new Parser(); //define a new parser
            //read the file and parse it
            readAndParse(reader, parser);
            //write the binary code to an output file
            for (int i = 0; i < parser.getBinaryOutput().size(); i++) {
                writer.write(parser.getBinaryOutput().get(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
        }
    }
}
