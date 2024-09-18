package constraintElements;

public class FunctionConstant extends FunctionSymbol {

    public FunctionConstant(char name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Fc";
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }

}