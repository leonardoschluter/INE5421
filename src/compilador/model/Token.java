package compilador.model;

public class Token {

    public TokenType type;
    public String text;

    public Token(TokenType type, String text){
        this.text = text;
        this.type = type;
    }

}
