package compilador;

import compilador.model.Tokenizer;
import compilador.model.Token;
import compilador.util.CustomBuffer;

public class Main {

    public static void main(String[] args) {
        System.out.print("Hello World !");
        Tokenizer bigTable = new Tokenizer();
        bigTable.generateAutomato();
        CustomBuffer cb = new CustomBuffer("123.4");
        Token token = bigTable.getNextToken(cb);
        if (token.text.isEmpty()){
            System.out.print("ETAAAA");
        }else{
            System.out.print("foiii");
        }
    }
}
