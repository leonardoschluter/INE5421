package compilador.util;

public class CustomBuffer {
    int begin;
    int end;
    char[] text;

    public CustomBuffer(String text){
        this.begin = 0;
        this.end = 0;
        this.text = text.toCharArray();
    }

    public char getNext(){
        char c = text[end];
        end ++;
        return c;
    }

    public void goBack(){
        end = -2;
    }

    public boolean hasNext(){
        return end < this.text.length ;
    }

    public String getLimitedText(){
        StringBuffer text =  new StringBuffer("");
        for(int i = begin; i < end; i ++){
            text.append(this.text[i]);
        }
        return text.toString();
    }
}
