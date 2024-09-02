package constraintElements;

import java.util.Arrays;

public class FunctionApplication implements Term{

    public FunctionSymbol functionSymbol;
    public Term[] args;

    public FunctionApplication(FunctionSymbol functionSymbol, Term[] args){
        this.functionSymbol = functionSymbol;
        this.args = args;
    }

    @Override
    public Element map(char from, Element to) {
        FunctionSymbol newFunctionSymbol = (FunctionSymbol) functionSymbol.map(from, to);
        Term[] newArgs = new Term[args.length];
        for(int i = 0; i < args.length; i++){
            newArgs[i] = (Term) args[i].map(from, to);            
        }
        return new FunctionApplication(newFunctionSymbol, newArgs);
    }

    @Override
    public String toString(){
        return functionSymbol + "(" + String.join(", ", Arrays.stream(args).map(Term::toString).toArray(String[]::new)) + ")"; 
    }

    @Override
    public String getType() {
        return "Fa";
    }    

    @Override
    public boolean contains(Element el) {
        if(functionSymbol.contains(el)){
            return true;
        }
        for(int i = 0; i < args.length; i++){
            if(args[i].contains(el)){
                return true;
            }
        }
        return false;
    }

    
    @Override
    public char getName() {
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    
}
