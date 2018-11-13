package compilador.util;

import compilador.model.Grammar;
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
            String head = line[0];
            String[] productions = line[1].split("\\|");
            for(int i = 0; i< productions.length; i++){
                Production production = this.scanProduction(productions[i], head);
                grammar.addProduction(production);
            }
        }

        return grammar;
    }

    private Production scanProduction(String text, String head) {
        text = text.replaceAll(" ","");
        Production production = new Production(head);
        char[] chars = text.toCharArray();
        boolean isReadingNonTerminal = false;
        String nonTerminal = "";
        String terminal = "";
        for(char c: chars){
            if(c == '<'){
                isReadingNonTerminal = true;
                if(!terminal.isEmpty()){
                    production.addSymbolToTail(terminal);
                    terminal = "";
                }
            }else if(c == '>'){
                isReadingNonTerminal = false;
                production.addSymbolToTail("<"+nonTerminal+">");
                nonTerminal = "";
            }else if(isReadingNonTerminal){
                nonTerminal = nonTerminal.concat(Character.toString(c));
            }else{
                terminal = terminal.concat(Character.toString(c));
            }
        }
        if(!terminal.isEmpty()){
            production.addSymbolToTail(terminal);
        }
        return production;
    }
}
