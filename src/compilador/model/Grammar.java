package compilador.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

    List<String> terminals;
    List<String> nonTerminals;
    Map<String, List<Production>> productions;

    public Grammar(){
        terminals = new ArrayList<>();
        nonTerminals = new ArrayList<>();
        productions = new HashMap<>();
    }

    public void addTerminal(String terminal){
        terminals.add(terminal);
    }

    public void addNonTerminal(String nonTerminal){
        nonTerminals.add(nonTerminal);
    }

    public void addProduction(Production production){
        if (!this.productions.containsKey(production.head)){
            ArrayList<Production> productions = new ArrayList<>();
            productions.add(production);
            this.productions.put(production.head, productions);
        }else{
            List<Production> productions = this.productions.get(production.head);
            productions.add(production);
        }
    }



}
