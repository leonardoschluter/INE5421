package compilador.model;

import java.util.Hashtable;

public class SymbolTable {

    Hashtable<String, Token> table;

    public SymbolTable(){
        table = new Hashtable();
        //TODO adicionar aqui os tokens padrões, como tokens de palavras reservadas
    }

    public void insert(Token token){
        this.table.put(token.text, token);
    }

    public boolean lookUp(String token){
        return this.table.containsKey(token);
    }

    public Token getToken(String limitedText) {
        return this.table.get(limitedText);
    }
}
