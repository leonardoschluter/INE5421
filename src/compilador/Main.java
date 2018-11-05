package compilador;

import compilador.model.AutomatoUnion;
import compilador.model.Token;

public class Main {

    public static void main(String[] args) {
        System.out.print("Hello World !");
        AutomatoUnion bigTable = new AutomatoUnion();
        bigTable.generateAllAutomatos();
        Token token = bigTable.getNextToken();
    }
}
