package compilador;

import compilador.model.Automato;
import compilador.util.AutomatoScanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Hello World !");
        AutomatoScanner scanner = new AutomatoScanner();
        try {
            Automato automato = scanner.readFileTxt("/home/leonardo/Documents/formais/T2_3-Formais/src/compilador/automato.txt");
        }catch (Exception e){
            e.printStackTrace();
        }

	// write your code here
    }
}
