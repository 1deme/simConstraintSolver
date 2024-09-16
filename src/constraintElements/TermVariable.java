package constraintElements;

public class TermVariable implements Term {
    char name;

    public TermVariable(char name){
        this.name = name;
    }

    @Override
    public String toString(){
        return Character.toString(name);
    }

    @Override
    public Element map(char from, Element to){
        if(this.name == from){
            return to;
        }
        return this;
    }

    @Override
    public String getType() {
        return "Tv";
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }

    @Override
    public char getName() {
        return name;
    }

    @Override
    public Element createCopy() {
        return new TermVariable(name);
    }

    @Override
    public boolean isVariable(){return true;}
    
}
