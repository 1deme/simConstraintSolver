package constraintElements;

import relations.relationCollection;

public class FunctionSymbol implements Element{
    char name;

    public FunctionSymbol (char name){
        this.name = name;
    }

    public String toString(){
        return Character.toString(name);
    }

    @Override
    public Element map(char from, Element to) {
        if(this.name == from){
            return to;
        }
        return this;
    }

    @Override
    public String getType() {
        return "Fs";
    }

    @Override
    public boolean contains(Element el) {
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public char getName() {
        return name;
    }

    @Override
    public FunctionSymbol createCopy(){
        return new FunctionSymbol(name);
    }


}
