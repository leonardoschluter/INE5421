package compilador.model;

import compilador.util.AutomatoScanner;
import compilador.util.CustomBuffer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    Automato automato;

    public void generateAutomato(){
        AutomatoScanner scanner = new AutomatoScanner();
        try {
            URL url = getClass().getResource("../data/bigTable.txt");
            File file = new File(url.getPath());
            this.automato = scanner.readFileTxt(file.getPath());
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Token getNextToken(CustomBuffer text) {
        boolean didAccept = false;
        boolean done = false;
        while(text.hasNext() && !done){
            char next = text.getNext();
            automato.compute(Character.toString(next));
            if(automato.isAtFinalState()){
                didAccept = true;
            }else if(didAccept){
                text.goBack();
                done = true;
                automato.goBack();
            }
        }
        automato.getFinalStateType(text.getLastChar());
        return new Token();
    }
}
