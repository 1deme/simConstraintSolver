package constraintElements;

public class FunctionVariable extends FunctionSymbol {

    public FunctionVariable(char name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Fv";
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }

    @Override
    public boolean isVariable(){return true;}
    
}
