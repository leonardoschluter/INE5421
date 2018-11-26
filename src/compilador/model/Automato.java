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
    String prevState;

    public Automato(String firstState, Set<String> endStates){
        this.transitionTable = new HashMap<>();
        this.firstState = firstState;
        this.endStates = endStates;
        this.actualState = firstState;
        this.prevState = firstState;
    }

    public void addCell(String state, String symbol, String destination){
        if(!this.transitionTable.containsKey(state)){ // se a tabela ainda não tiver o estado
            this.transitionTable.put(state, new HashMap<>()); // cria um novo map para esse estado
        }
        if(!this.transitionTable.get(state).containsKey(symbol)){ // se o estado ainda não tem uma transição pelo símoblo,
            this.transitionTable.get(state).put(symbol, destination); // cria a transição
        }
    }

    // Throw error when it is a state that has no transition defined for the inputSymbol
    public boolean compute(String inputSymbol) {
        if(this.transitionTable.get(actualState).containsKey(inputSymbol)){
            prevState = actualState;
            actualState = this.transitionTable.get(actualState).get(inputSymbol);
            return true;
        }else{
            return false;
        }
    }

    public void reset(){
        this.prevState = firstState;
        this.actualState = firstState;
    }

    public void goBack(){
        this.actualState = this.prevState;
    }

    public boolean isAtFinalState(){
        return endStates.contains(actualState);
    }

    public boolean isAtErrorState(){
        return TokenType.ERROR.name().equals(actualState);
    }


    public TokenType getFinalStateType() {
        try {
            return TokenType.valueOf(actualState);
        }catch( IllegalArgumentException e){
            return TokenType.ERROR;
        }
    }
}
