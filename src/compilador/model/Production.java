package compilador.model;

import java.util.ArrayList;
import java.util.List;

public class Production {

    String head;
    List<String> tail;

    public Production(String head){
        this.head = head;
        this.tail = new ArrayList<>();
    }

    public void addSymbolToTail(String symbol){
        tail.add(symbol);
    }
}
