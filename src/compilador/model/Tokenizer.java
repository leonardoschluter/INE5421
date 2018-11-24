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
            URL url = getClass().getResource("../data/bigTable1.txt");
            File file = new File(url.getPath());
            this.automato = scanner.readFileTxt(file.getPath());
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Token getNextToken(CustomBuffer text, SymbolTable symbolTable) {
        boolean didAccept = false;
        boolean done = false;
        while(text.hasNext() && !done && !text.isSpace()){ // se tiver caracteres e o automato não tiver terminado e o próximo caracter não for espaço,
            char next = text.getNext(); // pega o proximo caracter
            automato.compute(Character.toString(next)); // manda para o automato computar
            if(automato.isAtFinalState()){ // se o automato chegou à um estado final
                didAccept = true; // guarda a informação de que o automato já aceitou uma vez
            }else if(didAccept && automato.isAtErrorState()){ // se o automato já aceito alguma vez e foi para um estado de erro, quer dizer que ele terminou
                done = true;
            }else {
                didAccept = false; // se não, o automato ainda não aceitou a entrada
            }
        }

        // TODO se tiver tempo, arrumar a lógica desse método.
        if(done && automato.isAtErrorState()){
            // TODO aqui devemos tratar o erro léxico !
            System.out.println(automato.getFinalStateType().name());
        }else if(done) {
            text.goBack(2);
            automato.goBack();
        }else if(!text.isSpace()){
            text.goBack(1);
        }

        Token token;
        if(symbolTable.lookUp(text.getLimitedText())){
            token = symbolTable.getToken(text.getLimitedText());
        }else{
            token = new Token(automato.getFinalStateType(), text.getLimitedText());
        }
        text.moveFoward();
        return token;
    }

    public void resetAutomato() {
        automato.reset();
    }
}
