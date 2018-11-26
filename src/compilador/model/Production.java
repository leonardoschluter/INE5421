package compilador.model;

import java.util.*;

public class Production {

    public static final String EPSILON = "ε";
    NonTerminal head;
    List<String> tail;
    private Map<String, TokenType> terminals;
    private Map<String, NonTerminal> nonTerminals;

    public Production(NonTerminal head){
        this.head = head;
        this.tail = new ArrayList<>();
        this.nonTerminals = new HashMap<>();
        this.terminals = new HashMap<>();
    }

    public void addSymbolToTail(String symbol){
        tail.add(symbol);
    }

    public Map<String, TokenType> getTerminals(){
        if(terminals.isEmpty()) {
            for (String symbol : this.tail) {
                if (!symbol.contains("<$")) {
                    this.terminals.put(symbol, TokenType.fromString(symbol));
                }
            }
        }

        return this.terminals;

    }

    public Map<String, NonTerminal> getNonTerminals(Grammar grammar){
        if(this.nonTerminals.isEmpty()) {
            List<String> ids = this.tail;
            Map<String, NonTerminal> result = new HashMap<>();
            for (String id : ids) {
                if(!this.isSymbolTerminal(id)){
                    if(!result.containsKey(id)) {
                        result.put(id, grammar.findNonTerminal(id));
                    }
                }
            }
            this.nonTerminals = result;
        }
        return this.nonTerminals;
    }

    public boolean isFirstSymbolTerminal() {
        if(!this.tail.isEmpty() && !this.terminals.isEmpty()) {
            return this.terminals.containsKey(this.tail.get(0));
        }else{
            return false;
        }
    }

    public boolean isSymbolTerminal(String terminal){
        return this.terminals.containsKey(terminal);
    }

    public Set<String> getFirsts(Grammar grammar) {
        if(this.nonTerminals.isEmpty()){
            this.getNonTerminals(grammar);
        }
        Set<String> result = new HashSet<>();
        boolean isDone = false;
        int i = 0;
        while( i< this.tail.size() && !isDone ){ // para todas os símbolos da produção
            String symbol = this.tail.get(i);
            if(this.isSymbolTerminal(symbol)){ // se for terminal, acaba por ai,
               result.add(symbol);
               isDone = true;
            }else { // se for não terminal,
                NonTerminal nonTerminal = this.nonTerminals.get(symbol); // recuperamos ele do mapa
                result.addAll(nonTerminal.getFirsts(grammar)); // e pedimos para ele os firsts dele
                if(!nonTerminal.hasEpProduction()){ // e depois verificamos se esse não terminal tem Eps. transição.
                   isDone = true; // se não tiver, podemos parar por ai
                }
            }
            i++;
        }
        return result;
    }

    public boolean isEpProduction() {
       return this.tail.size() == 1 && this.tail.get(0).trim().equals(EPSILON);
    }

    public boolean isBetaEp(NonTerminal B){
        if(tail.get(tail.size() - 1).equals(B.id)){
            return true;
        }
        int start = tail.size() - 1;
        boolean done = false;
        boolean result = true;
        while (!done && start >= 0){
            String symbol = this.tail.get(start); // pega o ultimo elemento da tail que ainda nao foi analisado
            if(!isSymbolTerminal(symbol)){ // se não for um terminal
                if(this.nonTerminals.get(symbol).equals(B)){ // e for igual a B
                    done = true; // entao achamos o nosso beta.
                }else if(!this.nonTerminals.get(symbol).hasEpInFirsts()){ // se for diferente de B e não tiver Epsilon nos firsts
                    done = true; // terminamos
                    result = false; // e sabemos que não tem Ep no Beta de B
                }
            }else if(symbol != EPSILON){
                done = true; // eh um simbolo terminal,
                result = false; // portanto tem
            }
            start --;
        }

        return result;
    }

    public boolean hasNonTerminal(NonTerminal nonTerminal){
        return this.nonTerminals.containsKey(nonTerminal.id);
    }

    public Set<String> getFollowOfB(NonTerminal B, Grammar grammar) {
        if(tail.get(tail.size() - 1).equals(B.id)){
            return new HashSet<>();
        }
        int start = tail.size() - 1;
        boolean done = false;
        while (!done && start >= 0){
            String symbol = this.tail.get(start); // pega o ultimo elemento da tail que ainda nao foi analisado
            if(!isSymbolTerminal(symbol)){ // se não for um terminal
                if(this.nonTerminals.get(symbol) == null){
                    this.nonTerminals.put(symbol, grammar.nonTerminals.get(symbol));
                    System.out.println("atoamnorcu");
                }
                if(this.nonTerminals.get(symbol).equals(B)){ // e for igual a B
                    done = true; // entao achamos o nosso beta.
                }
            }
            start --;
        }

        Set<String> firstsOfBeta = new HashSet<>();
        if(start == -1){
            start ++;
        }
        for(int i = start; i < this.tail.size(); i ++ ){
            String symbol = this.tail.get(i);
            if(!symbol.equals(EPSILON)){
                if(this.isSymbolTerminal(symbol)){
                    firstsOfBeta.add(symbol);
                }else {
                    firstsOfBeta.addAll(this.nonTerminals.get(symbol).getFirstsWithoutEpsilon(grammar));
                }
            }
        }

        return firstsOfBeta;
    }

    public Set<String> getFollowOfHead() {
        return this.head.getFollows();
    }

    public String getReadableProduction() {
        String result = this.head.id + " :== ";
        for(String terminal: tail){
            result = result + terminal + " ";
        }
        return result;
    }
}
