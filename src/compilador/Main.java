package compilador;

import compilador.model.SymbolTable;
import compilador.model.Tokenizer;
import compilador.model.Token;
import compilador.util.CodeReader;
import compilador.util.CustomBuffer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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


    }
}
