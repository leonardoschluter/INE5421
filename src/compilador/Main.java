package compilador;

import compilador.model.*;
import compilador.util.CodeReader;
import compilador.util.CustomBuffer;
import compilador.util.GrammarScanner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String code = "";
        CodeReader codeReader = new CodeReader();
        try {
            code = codeReader.getCodeToCompile();
        }catch (IOException e){
            e.printStackTrace();
        }

        SymbolTable symbolTable = new SymbolTable();
        Tokenizer bigTable = new Tokenizer();
        bigTable.generateAutomato();


        CustomBuffer cb = new CustomBuffer(code);

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
        grammar.generateTerminalsAndNon();

        HashMap<NonTerminal, Set<String>> firsts =  grammar.calculateFirsts();

        grammar.calculateFollows();
        grammar.createParsingTable();


        //TODO compute first and follows
        System.out.println(grammar.toString());
    }
}
