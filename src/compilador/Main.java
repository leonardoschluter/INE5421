package compilador;

import compilador.model.Grammar;
import compilador.model.SymbolTable;
import compilador.model.Tokenizer;
import compilador.model.Token;
import compilador.util.CustomBuffer;
import compilador.util.GrammarScanner;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.print("Hello World !");
        SymbolTable symbolTable = new SymbolTable();
        Tokenizer bigTable = new Tokenizer();
        bigTable.generateAutomato();
        CustomBuffer cb = new CustomBuffer("123.4 abcccab { } ;$");

        ArrayList<Token> tokens = new ArrayList<Token>();

        while(cb.hasNext() && !cb.isEndOFFile()){
            if(cb.isSpace()){
                cb.skipSpace();
            }else{
                bigTable.resetAutomato();
                tokens.add(bigTable.getNextToken(cb, symbolTable));
            }
        }

        for(Token token: tokens){
            System.out.println(token.text + " - " + token.type.name());
        }

        GrammarScanner grammarScanner = new GrammarScanner();
        Grammar grammar = new Grammar();
        try{
            grammar = grammarScanner.readGrammarTxt();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(grammar.toString());
    }
}
