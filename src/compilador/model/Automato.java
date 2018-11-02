package compilador.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Automato {

    Map<String, Map<String, String>> transitionTable;
    String firstState;
    Set<String> endStates;
    String actualState;

    public Automato(String firstState, Set<String> endStates){
        this.transitionTable = new HashMap<>();
        this.firstState = firstState;
        this.endStates = endStates;
    }

    public void addCell(String state, String symbol, String destination){
        if(!this.transitionTable.containsKey(state)){
            this.transitionTable.put(state, new HashMap<>());
        }
        if(!this.transitionTable.get(state).containsKey(symbol)){
            this.transitionTable.get(state).put(symbol, destination);
        }
    }

    // Throw error when it is a state that has no transition defined for the inputSymbol
    public boolean compute(String inputSymbol) {
        if(this.transitionTable.get(actualState).containsKey(inputSymbol)){
            actualState = this.transitionTable.get(actualState).get(inputSymbol);
            return true;
        }else{
            return false;
        }
    }

    public boolean isAtFinalState(){
        return endStates.contains(actualState);
    }


}