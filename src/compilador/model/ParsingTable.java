package compilador.model;

import com.sun.corba.se.impl.oa.toa.TOA;

import java.util.*;

public class ParsingTable {

    Stack<String> stack;
    Stack<Token> input;
    Map<String, Map< NonTerminal, Production>> table;

    public ParsingTable(){
        input = new Stack<>();
        table = new HashMap<>();
        stack = new Stack<>();
        stack.push(Grammar.END_MARKER);
    }

    public void addProductionToTable(String symbol, NonTerminal nonTerminal, Production production){
        if(this.table.containsKey(symbol)){
            this.table.get(symbol).put(nonTerminal, production);
        }else{
            Map<NonTerminal, Production> transitions = new HashMap<>();
            transitions.put(nonTerminal, production);
             table.put(symbol, transitions);
        }
    }

    public void parse(List<Token> tokens, SymbolTable symbolTable){
        for(int i = tokens.size()- 1; i>=0; i--){
            input.push(tokens.get(i));
        }
        stack.removeAllElements();
        stack.push(Grammar.END_MARKER);

        while(!input.empty()){
            if(table.containsKey(input.peek())){

            }
        }
    }
}
