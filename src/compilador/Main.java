package compilador;

import compilador.model.Tokenizer;
import compilador.model.Token;
import compilador.util.CustomBuffer;

public class Main {

    public static void main(String[] args) {
        System.out.print("Hello World !");
        Tokenizer bigTable = new Tokenizer();
        bigTable.generateAutomato();
        CustomBuffer cb = new CustomBuffer("teste");
        Token token = bigTable.getNextToken(cb);
    }
}
