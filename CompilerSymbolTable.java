
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class CompilerSymbolTable {
    //global variable for count the total token class
    public static int countKeyword = 0;
    public static int countIdentifier = 0;
    public static int countSpecial = 0;
    public static int countInteger = 0;
    public static int countReal = 0;
    //two hash map: symbolTable->each symbol with time occurs
    //  Token class -> each symbol belong to which Token class
    public static Map<String, Integer> symbolTable = new LinkedHashMap<>();
    public static Map<String, String> tokenClass = new LinkedHashMap<>();

    /******************************************************
     * function: read file function
     * ***************************************************/
    public static void readFile() {
        File readFile = new File("Input.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
            String readLines;
            while ((readLines = bufferedReader.readLine()) != null) {
                String[] word = readLines.split(" ");  //spilt one line string with space
                for (int i = 0; i < word.length; i++) { //go through each words and send them to match pattern method
                    word[i] = word[i].trim();    //get rid of the white space using trim()
                    if (!word[i].isEmpty()) {
                        //here split with the space first,
                        //and then split each token individual pass to the function patternCheck
                        Pattern p = Pattern.compile("(?<=\\w)(?=[^\\w.])|(?<=[^\\w.])(?=\\w)|(?<=\\W)(?=\\W)");
                        // Pattern p = Pattern.compile("(?<=\\w)(?=[\\(|\\)|\\[|\\]|\\+|\\-|\\=|\\,|\\;])|(?<=[\\(|\\)|\\[|\\]|\\+|\\-|\\=|\\,|\\;])(?=\\w)|(?<=\\W)(?=\\W)");
                        // Pattern p = Pattern.compile("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
                        for (int j = 0; j < p.split(word[i]).length; j++) {
                            //System.out.println(p.split(word[i])[j]);
                            patternCheck(p.split(word[i])[j]);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("File can not find");
        }
    }

    /******************************************************
     * function: check the words belong to which function
     * ***************************************************/
    public static void patternCheck(String splitWord) {
        Pattern keyword = Pattern.compile("(if|then|else|begin|end)");
        Pattern identifier = Pattern.compile("[a-zA-Z]+");
        Pattern integer = Pattern.compile("[0-9]+");
        Pattern real = Pattern.compile("[0-9]+[.][0-9]+");
        Pattern special = Pattern.compile("[(|)|\\[|\\]|+|\\-|=|,|;]");
       /*
       check each token match with the token class use the pattern,
       if match, store them in the hashMap, each time increase the hashMap value for that token(key)
       * */
        if (keyword.matcher(splitWord).find()) {
            if (symbolTable.containsKey(splitWord)) {
                symbolTable.put(splitWord, symbolTable.get(splitWord) + 1);
                tokenClass.put(splitWord, "keyword");
            } else {
                symbolTable.put(splitWord, 1);
                tokenClass.put(splitWord, "keyword");
            }
            countKeyword++;

        } else if (identifier.matcher(splitWord).find()) {
            if (symbolTable.containsKey(splitWord)) {
                symbolTable.put(splitWord, symbolTable.get(splitWord) + 1);
                tokenClass.put(splitWord, "identifier");
            } else {
                symbolTable.put(splitWord, 1);
                tokenClass.put(splitWord, "identifier");
            }
            countIdentifier++;

        } else if (real.matcher(splitWord).find()) {
            if (symbolTable.containsKey(splitWord)) {
                symbolTable.put(splitWord, symbolTable.get(splitWord) + 1);
                tokenClass.put(splitWord, "real");
            } else {
                symbolTable.put(splitWord, 1);
                tokenClass.put(splitWord, "real");
            }
            countReal++;

        } else if (integer.matcher(splitWord).find()) {
            if (symbolTable.containsKey(splitWord)) {
                symbolTable.put(splitWord, symbolTable.get(splitWord) + 1);
                tokenClass.put(splitWord, "integer");
            } else {
                symbolTable.put(splitWord, 1);
                tokenClass.put(splitWord, "integer");
            }
            countInteger++;

        } else if (special.matcher(splitWord).find()) {
            if (symbolTable.containsKey(splitWord)) {
                symbolTable.put(splitWord, symbolTable.get(splitWord) + 1);
                tokenClass.put(splitWord, "special");
            } else {
                symbolTable.put(splitWord, 1);
                tokenClass.put(splitWord, "special");
            }
            countSpecial++;
        }
    }


    /******************************************************
     * main method
     * ***************************************************/
    public static void main(String argc[]) {
        //read the input file
        readFile();
        //printout the symbol table
        System.out.println("*************************************\nSymbolTable\t TimesOccurs\t TokenClass\n*************************************");
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {   //print keys and values
            System.out.printf("%-10s%10d\t\t%s%n", entry.getKey(), entry.getValue(), tokenClass.get(entry.getKey()));
        }
        //print out the total token class
        System.out.println("\n*************************************\nTokenClass\t TimesOccurs\n*************************************");
        System.out.printf("keyword%15d\nidentifier%12d\ninteger%15d\nreal%18d\nspecial%15d\n", countKeyword, countIdentifier, countInteger, countReal, countSpecial);

    }
}
