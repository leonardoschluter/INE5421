package compilador.model;

import com.sun.corba.se.impl.oa.toa.TOA;

import java.util.*;

public class ParsingTable {

    private final Grammar grammar;
    Stack<String> stack;
    Stack<Token> input;
    Map<String, Map< NonTerminal, Production>> table; // M[a, X]

    public ParsingTable(Grammar grammar){
        input = new Stack<>();
        table = new HashMap<>();
        stack = new Stack<>();
        this.grammar = grammar;
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

    public void printParsingTable(){
        Set<String> symbols = table.keySet();
        for(String symbol: symbols){
            System.out.println( " ");
            System.out.println("### " + symbol);
            for(NonTerminal nonTerminal: this.table.get(symbol).keySet()){
                Production production = this.table.get(symbol).get(nonTerminal);
                System.out.print(production.getReadableProduction());
                System.out.print( " |  ");
            }
        }


    }

    public void parse(List<Token> tokens, SymbolTable symbolTable){
        //TODO criar token de final de string "$" e por na pilha de input por primeiro.
        for(int i = tokens.size()- 1; i>=0; i--){
            input.push(tokens.get(i));
        }
        stack.removeAllElements();
        stack.push(Grammar.END_MARKER);
        stack.push(grammar.startSymbol.id);
        boolean hasGoneBad = false;
        while(!input.empty() && !hasGoneBad){ // se a pilha nao estiver vazia
            Token token = input.peek(); // a
            String symbol = stack.peek(); // X
            if(symbol.equals(token.type.getCmd())){ // se o topo da pilha for igual ao topo da entrada
                input.pop();
                stack.pop();
                System.out.print("fooooooi");
            }else if(this.grammar.isSymbolTerminal(symbol)){ // se o topo da pilha for um terminal, achamos um erro
                System.out.print("ERROR");
                hasGoneBad = true;
                // TODO lidar com errors aqui.
            }else if(this.table.containsKey(token.type.getCmd())){ // se tivermos entrada na tabela de parse com esse input
                NonTerminal X = grammar.nonTerminals.get(symbol);
                if(this.table.get(token.type.getCmd()).containsKey(X)){ // e tivermos uma produção com o topo da pilha de símbolos
                    Production production = this.table.get(token.type.getCmd()).get(X); // pegamos a produção
                    System.out.println(production.getReadableProduction());
                    stack.pop(); // retiramos o topo da pilha
                    if(!production.isEpProduction()) {
                        for (int i = production.tail.size() - 1; i >= 0; i--) {
                            stack.push(production.tail.get(i));
                        }
                    }
                }else if(X.hasEpProduction()){
                    stack.pop();
                }
            }else{
                System.out.print("ERROR");
                hasGoneBad = true;
            }
        }
    }
}
