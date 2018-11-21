package compilador.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class CodeReader {

    public CodeReader(){}

    public String getCodeToCompile() throws IOException {
        String code = "";
        URL url = getClass().getResource("../data/code.txt");
        File file = new File(url.getPath());
        Scanner input = new Scanner(file);
        while(input.hasNextLine()){
            code = code + input.nextLine().replaceAll("\t", " ").replaceAll("\n"," ");
        }
        return code;
    }
}
