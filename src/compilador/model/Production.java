package compilador.model;

public class Production {

    String head;
    String[] tail;

    public Production(String head, String[] tail){
        this.head = head;
        this.tail = tail;
    }
}
