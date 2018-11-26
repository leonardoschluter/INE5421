package compilador.model;

public enum TokenType {
    OPEN_BLOCK("{"),
    CLOSE_BLOCK("}"),
    END_OF_LINE(";"),
    ID("id"),
    TYPE("basic"),
    SIZE_ARRAY("[num]"),
    OPEN_PAR("("),
    CLOSE_PAR(")"),
    WHILE("while"),
    DO("do"),
    BREAKE("break"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    DIF_OP("!="),
    EQUAL_OP("=="),
    GREAT_OP(">"),
    GREAT_EQUAL_OP(">="),
    LESS_EQUAL_OP("<="),
    LESS_OP("<"),
    ADD_OP("+"),
    MINUS_OP("−"),
    MULT_OP("∗"),
    DIV_OP("/"),
    NEG_OP("!"),
    INT("int"),
    REAL("real"),
    TRUE("true"),
    ERROR(""),
    FALSE("false"),
    EPSILON("ε");

    private String cmd;

    TokenType(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return this.cmd;
    }

    public static TokenType fromString(String cmd) {
        for (TokenType b : TokenType.values()) {
            if (b.cmd.equalsIgnoreCase(cmd)) {
                return b;
            }
        }
        return ERROR;
    }
}
