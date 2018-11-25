package compilador.util;

import compilador.model.Grammar;
import compilador.model.NonTerminal;
import compilador.model.Production;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class GrammarScanner {

    public GrammarScanner(){}

    public Grammar readGrammarTxt()throws Exception{
        URL url = getClass().getResource("../data/grammar.txt");
        File file = new File(url.getPath());
        Scanner input = new Scanner(file);
        Grammar grammar = new Grammar();
        while (input.hasNext()){
            String[] line = input.nextLine().split("::=");
            NonTerminal head = new NonTerminal(line[0]);
            String[] productions = line[1].split("\\| ");
            for(int i = 0; i< productions.length; i++){
                Production production = this.scanProduction(productions[i], head);
                head.addProduction(production);
                grammar.addProduction(production);
            }
        }

        return grammar;
    }

    private Production scanProduction(String text, NonTerminal head) {
        String[] chars = text.split(" ");
        Production production = new Production(head);
        for(String c: chars){
            if(!c.isEmpty()) {
                production.addSymbolToTail(c);
            }
        }
        return production;
    }
}
