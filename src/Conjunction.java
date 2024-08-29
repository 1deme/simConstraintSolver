import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import constraintElements.FunctionApplication;

public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();

    Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

    public boolean unif(){
        for(int i = 0; i < conjunction.size(); i++){
            PrimitiveConstraint primitiveConstraint = conjunction.removeFirst();
            if(delEqCond(primitiveConstraint)){
                i = 0;
                continue;
            }
            if(decEqCond(primitiveConstraint)){
                decEqOp((FunctionApplication) primitiveConstraint.el1, (FunctionApplication) primitiveConstraint.el2);
                i = 0;
                continue;
            }
            if(oriEqCond(primitiveConstraint)){
                oriEqOp(primitiveConstraint);
                i = 0;
                continue;
            }
            if(elimEqCond(primitiveConstraint)){
                elimEqOp(primitiveConstraint);
                i = 0;
                continue;
            }
            if(conflEqCond(primitiveConstraint) || mismEqCond(primitiveConstraint) || occEqCond(primitiveConstraint)){
                conjunction.clear();
                return false;
            }
            conjunction.addLast(primitiveConstraint);
        }
        return false;
    }

    public boolean delEqCond(PrimitiveConstraint primitiveConstraint){
        return (   primitiveConstraint.el1.getType() == "Fc" ||
                    primitiveConstraint.el1.getType() == "Fv" || 
                    primitiveConstraint.el1.getType() == "Tv"
                ) &&
                primitiveConstraint.el1 == primitiveConstraint.el2;
    }

    public boolean decEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.getType() == "Fa" &&
        primitiveConstraint.el2.getType() == "Fa" &&
       ((FunctionApplication) primitiveConstraint.el1).args.length == ((FunctionApplication) primitiveConstraint.el2).args.length &&
       ((FunctionApplication) primitiveConstraint.el2).args.length >= 1;
    }

    public void decEqOp(FunctionApplication f1, FunctionApplication f2){
        for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new EqualityPredicate(f1.args[i], f2.args[i]));
        }
        conjunction.add(new EqualityPredicate(f1.functionSymbol, f2.functionSymbol));
    }


    public boolean oriEqCond(PrimitiveConstraint primitiveConstraint){
        return (primitiveConstraint.el2.getType() == "Fv" || primitiveConstraint.el2.getType() == "Tv")
        && (primitiveConstraint.el1.getType() != "Fv" && primitiveConstraint.el1.getType() != "Tv");
    }

    public void oriEqOp(PrimitiveConstraint primitiveConstraint){
        conjunction.add(new EqualityPredicate(primitiveConstraint.el2, primitiveConstraint.el1));
    }

    public boolean elimEqCond(PrimitiveConstraint primitiveConstraint){
        boolean notInEl2 = primitiveConstraint.el2.contains(primitiveConstraint.el1);
        if(notInEl2 == true){
            return false;
        }
        for(PrimitiveConstraint pc : conjunction){
            if(pc.el1.contains(primitiveConstraint.el1) || pc.el2.contains(primitiveConstraint.el1)){
                return true;
            }
        }
        return false;
    }

    public void elimEqOp(PrimitiveConstraint primitiveConstraint){
        
        // conjunction = conjunction.stream()
        //             .map(x -> 
        //             {return new PrimitiveConstraint(
        //                     x.el1.map(primitiveConstraint.el1.getName(),primitiveConstraint.el2),
        //                     x.el2.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2)
        //                  );}
        //             )
        //             .collect(Collectors.toCollection(ArrayList::new));
        conjunction.replaceAll(x -> new PrimitiveConstraint(
            x.el1.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2),
            x.el2.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2)
        ));
        conjunction.addLast(primitiveConstraint);
    }

    public boolean conflEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.getType() == "Fc" && primitiveConstraint.el2.getType() == "Fc";
    }

    public boolean mismEqCond(PrimitiveConstraint primitiveConstraint){
        if(primitiveConstraint.el1.getType() == "Fa" && primitiveConstraint.el2.getType() == "Fa")
            return ((FunctionApplication )primitiveConstraint.el1).args.length != ((FunctionApplication )primitiveConstraint.el2).args.length;
        return false;
    }

    public boolean occEqCond(PrimitiveConstraint primitiveConstraint){
        if(primitiveConstraint.el1.getType() != "Tv"){
            return false;
        }
        return primitiveConstraint.el1 != primitiveConstraint.el2 && primitiveConstraint.el2.contains(primitiveConstraint.el1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator<PrimitiveConstraint> iterator = conjunction.iterator();

        while (iterator.hasNext()) {
            result.append(iterator.next().toString());
            if (iterator.hasNext()) {
                result.append(" ^ ");
            }
        }

        return result.toString();
    }
    
    
}
