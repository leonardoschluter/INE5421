package compilador.util;

import compilador.model.Automato;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AutomatoScanner {

    public static final String HEADER_STR = "STATES";

    public AutomatoScanner(){

    }

    public Automato readFileTxt(String nameOfFile) throws Exception{
        File file = new File(nameOfFile);

        Scanner input = new Scanner(file);
        String firstState = "";
        Set<String> endStates = new HashSet<>();

        String[] line = input.nextLine().trim().split(":");
        if( line.length != 0 ){
            if( line[0].trim().equals(HEADER_STR)){
                firstState = this.findFirstState(line);
                endStates = this.findEndStates(line);
            }
        }
        String[] states = removeIdentifierChars(line);
        Automato automato = new Automato(firstState, endStates);
        while (input.hasNextLine()) {
            String[] transitions = input.nextLine().replaceAll("#", "").replaceAll("\\*", "").trim().split(":");
            String symbol = transitions[0].trim();
           if(!symbol.isEmpty() && !symbol.equals(HEADER_STR)){
               for(int i = 1;i < transitions.length; i++){
                   String transition = transitions[i].trim();
                   if(!transition.equals("")) {
                       automato.addCell(states[i], symbol,transition);
                       System.out.println(states[i] + " | Simbolo: " + symbol + " | transição" + transition);
                   }
               }
           }
        }
        return automato;
    }

    private String[] removeIdentifierChars(String[] line) {
        String[] newStates = new String[line.length];
        for(int i = 0; i < line.length; i++){
            newStates[i] = line[i].replace("#", "").replace("*", "").trim();
        }
        return newStates;
    }

    private Set<String> findEndStates(String[] line) {
        Set<String> endStates = new HashSet<>();
        for(String state: line){
            if (state.contains("*")){
                endStates.add(state.replace("*", ""));
            }
        }
        return endStates;
    }

    private String findFirstState(String[] line) {
        String firstState = "";
        for( String state: line){
            if (state.contains("#")){
                firstState =  state.replace("#", "");
            }
        }
        return firstState;
    }

}
