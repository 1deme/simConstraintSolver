package constraintElements;

public class FunctionVariable extends FunctionSymbol {

    public FunctionVariable(char name) {
        super(name);
    }

    public Element map(char from, Element to){
        if(this.name == from){
            return to;
        }
        return this;
    }

    @Override
    public String getType() {
        return "Fv";
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }
    
}
