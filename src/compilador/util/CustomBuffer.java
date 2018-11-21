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

    public void goBack(Integer quantity){
        end = end-quantity;
    }

    public boolean hasNext(){
        return end < this.text.length;
    }

    public boolean isSpace(){
        char space = ' ';
        if(this.hasNext()){
            return text[end] == space;
        }else{
            return false;
        }
    }

    public String getLimitedText(){
        StringBuffer text =  new StringBuffer("");
        for(int i = begin; i <= end; i ++) {
            if (this.text[i] != '$'){
                text.append(this.text[i]);
            }
        }
        return text.toString();
    }

    public char getLastChar(){
        return text[end];
    }

    public void moveFoward() {
        this.begin = this.end;
    }

    public void skipSpace() {
        if(isSpace()){
            end++;
            this.moveFoward();
        }
    }

    public boolean isEndOFFile() {
        char endOfFile = '$';
        return endOfFile == text[end];
    }
}
