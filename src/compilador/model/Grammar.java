package compilador.model;

import java.util.*;

public class Grammar {

    public static final String END_MARKER = "$";
    List<String> terminals;
    Map<String, NonTerminal> nonTerminals;
    Map<NonTerminal, List<Production>> productions;
    NonTerminal startSymbol;

    public Grammar(){
        terminals = new ArrayList<>();
        nonTerminals = new HashMap<>();
        productions = new HashMap<>();
    }

    private void addTerminal(String terminal){
        terminals.add(terminal);
    }

    private void mergeTerminals(List<String> terminals){
        for(String terminal: terminals){
            if(!this.terminals.contains(terminal)){
                this.terminals.add(terminal);
            }
        }
    }

    private void addNonTerminal(NonTerminal nonTerminal){
        if(!this.nonTerminals.containsKey(nonTerminal.id)){
            nonTerminals.put(nonTerminal.id, nonTerminal);
        }
    }

    public void addProduction(Production production){
        if(this.startSymbol == null){
            this.startSymbol = production.head;
        }
        if (!this.productions.containsKey(production.head)){
            ArrayList<Production> productions = new ArrayList<>();
            productions.add(production);
            this.productions.put(production.head, productions);
        }else{
            List<Production> productions = this.productions.get(production.head);
            productions.add(production);
        }
    }


    public void generateTerminalsAndNon() {
        for(NonTerminal nonTerminal: productions.keySet()){
            addNonTerminal(nonTerminal);
            for(Production production: this.productions.get(nonTerminal)){
                this.mergeTerminals(production.getTerminals());
            }
        }
    }

    //professora pediu pra ser recursivo, mas não entendi muito bem como ...
    public HashMap<NonTerminal, Set<String>> calculateFirsts() {
        HashMap<NonTerminal, Set<String>> result = new HashMap<>();

        //passar por cada não terminal e ver se todas as suas produções começam com terminais e calcula first delas.
        for(String id: nonTerminals.keySet()) { // itera sobre todos os não terminais da gramática
            NonTerminal nonTerminal =  this.nonTerminals.get(id);
            if(nonTerminal.hasAllFirstAsTerminals()){
                result.put(nonTerminal, nonTerminal.getFirsts(this));
            }
        }

        for(String id: nonTerminals.keySet()) { // itera sobre todos os não terminais da gramática
            NonTerminal nonTerminal =  this.nonTerminals.get(id);
            result.put(nonTerminal, nonTerminal.getFirsts(this));
        }
        return result;
    }

    public HashMap<NonTerminal, Set<String>> calculateFollows(){
        HashMap<NonTerminal, Set<String>> result = new HashMap<>();
        Set<String> followStartSymbol = new HashSet<>();
        followStartSymbol.add(END_MARKER);
        result.put(startSymbol, followStartSymbol);
        this.calculateFollowsWithoutEpsilon(result);

        this.calculateFollowsOfEp(result);
        return result;
    }

    private void calculateFollowsOfEp(HashMap<NonTerminal, Set<String>> result) {
        for (NonTerminal nonTerminal : this.nonTerminals.values()) {
            if (!nonTerminal.isFollowCalculated) {
                for (List<Production> productions : this.productions.values()) {
                    for (Production production : productions) {
                        Set<String> fo = production.getFollowOfHead();
                        if (result.containsKey(nonTerminal)) {
                            fo.addAll(result.get(nonTerminal));
                        }
                        nonTerminal.addToFollow(fo);
                        nonTerminal.isFollowCalculated = true;
                        result.put(nonTerminal, fo);
                    }
                }
            }
        }
    }

    private void calculateFollowsWithoutEpsilon(HashMap<NonTerminal, Set<String>> result){
        for (NonTerminal nonTerminal : this.nonTerminals.values()) {
            if (!nonTerminal.isFollowCalculated) {
                for (List<Production> productions : this.productions.values()) {
                    for (Production production : productions) {
                        if (production.hasNonTerminal(nonTerminal)) {
                            Set<String> fo = production.getFollowOfB(nonTerminal, this);
                            if (result.containsKey(nonTerminal)) {
                                fo.addAll(result.get(nonTerminal));
                            }
                            nonTerminal.addToFollow(fo);
                            if (!production.isBetaEp(nonTerminal)) {
                                nonTerminal.isFollowCalculated = true;
                            }
                            result.put(nonTerminal, fo);
                        }
                    }
                }
            }
        }
    }

    private boolean isEvereyFollowCalculated() {
        boolean result = true;
        for(NonTerminal nonTerminal: this.nonTerminals.values()){
            if(!nonTerminal.isFollowCalculated){
                result = false;
            }
        }
        return  result;
    }

    public NonTerminal findNonTerminal(String id) {
        return this.nonTerminals.get(id);
    }
}



/*
        HashMap<NonTerminal, Set<String>> result = new HashMap<>();
        for(int i = 0; i < this.nonTerminals.size(); i++){ // itera sobre todos os não terminais da gramática
            NonTerminal nonTerminal = this.nonTerminals.get(i); // pega o nao terminal em específico
            Set<String> firsts = new HashSet<>(); // cria o conjunto dos firsts
            for(int j = 0; j < this.productions.get(nonTerminal).size(); j++){ // para todas as produções do não terminal
                Production production = this.productions.get(nonTerminal).get(j);
                if(production.isFirstSymbolTerminal()){ // verifica se o primeiro símbolo da produção é terminal,
                    firsts.add(production.getFirstTerminal()); // se for terminal, podemos adicionar ao conjunto de firsts do não terminal que é cabeça da produção.
                }
            }
            if(result.containsKey(nonTerminal)){
                firsts.addAll(result.get(nonTerminal));
            }
            result.put(nonTerminal, firsts); // salva os firsts do não terminal num mapa para utilizar futuramente
        }

        //tendo calculado os firsts mais fáceis, podemos então verificar as produções que são

        for(int i = 0; i < this.nonTerminals.size(); i++){ // itera sobre todos os não terminais da gramática
            NonTerminal nonTerminal = this.nonTerminals.get(i); // pega o nao terminal em específico
            Set<String> firsts = new HashSet<>(); // cria o conjunto dos firsts
            for(int j = 0; j < this.productions.get(nonTerminal).size(); j++){ // para todas as produções do não terminal
                Production production = this.productions.get(nonTerminal).get(j);
                if(!production.isFirstSymbolTerminal()){
                    production.getNonTerminals();
                    firsts.addAll(production.getFirsts());
                }
            }
            if(result.containsKey(nonTerminal)){
                firsts.addAll(result.get(nonTerminal));
            }
            result.put(nonTerminal, firsts); // salva os firsts do não terminal num mapa para utilizar futuramente
        }

        return result;*/
