package compilador.util;

import compilador.model.Grammar;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class GrammarScanner {

    public GrammarScanner(){}

    public Grammar readGrammarTxt()throws Exception{
        URL url = getClass().getResource("../data/grammar.txt");
        File file = new File(url.getPath());
        Scanner input = new Scanner(file);

        while (input.hasNext()){
            String[] line = input.nextLine().split("::=");
            
        }

        return null;
    }
}
