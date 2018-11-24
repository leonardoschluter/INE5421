package compilador.model;

public enum TokenType {
    OPEN_BLOCK(""),
    CLOSE_BLOCK(""),
    END_OF_LINE(""),
    ID(""),
    TYPE(""),
    SIZE_ARRAY(""),
    OPEN_PAR(""),
    CLOSE_PAR(""),
    WHILE(""),
    DO(""),
    BREAK(""),
    IF(""),
    THEN(""),
    ELSE(""),
    DIF_OP(""),
    EQUAL_OP(""),
    GREAT_OP(""),
    GREAT_EQUAL_OP(""),
    LESS_EQUAL_OP(""),
    LESS_OP(""),
    ADD_OP(""),
    MINUS_OP(""),
    MULT_OP(""),
    DIV_OP(""),
    INT(""),
    REAL(""),
    TRUE(""),
    ERROR(""),
    FALSE("");

    private String cmd;

    TokenType(String cmd) {
        this.cmd = cmd;
    }

}
