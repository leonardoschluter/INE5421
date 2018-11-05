package compilador.model;

import compilador.util.AutomatoScanner;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AutomatoUnion {
    List<Automato> automatos;

    public void generateAllAutomatos(){
        automatos = new ArrayList<>();
        AutomatoScanner scanner = new AutomatoScanner();
        try {
            URL url = getClass().getResource("../data");
            File f = new File(url.getPath());
            FilenameFilter textFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            };

            File[] files = f.listFiles(textFilter);
            for (File file : files) {
                if (!file.isDirectory()) {
                    Automato automato = scanner.readFileTxt(file.getPath());
                    automatos.add(automato);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
