package transformations;
import constraintElements.FunctionApplication;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;

import java.util.List;

public class Unif {

    public static boolean unif(List<PrimitiveConstraint> conjunction){
        

        for(int i = 0; i < conjunction.size(); i++){
            PrimitiveConstraint primitiveConstraint = conjunction.removeFirst();
            if(delEqCond(primitiveConstraint)){
                i = -1;
                continue;
            }
            if(decEqCond(primitiveConstraint)){
                decEqOp((FunctionApplication) primitiveConstraint.el1, (FunctionApplication) primitiveConstraint.el2, conjunction);
                i = -1;
                continue;
            }
            if(oriEqCond(primitiveConstraint)){
                oriEqOp(primitiveConstraint, conjunction);
                i = -1;
                continue;
            }
            if(elimEqCond(primitiveConstraint, conjunction)){
                elimEqOp(primitiveConstraint, conjunction);
                i = -1;
                continue;
            }
            if(conflEqCond(primitiveConstraint) || mismEqCond(primitiveConstraint) || occEqCond(primitiveConstraint)){
                conjunction.clear();
                return false;
            }
            conjunction.addLast(primitiveConstraint);
        }
        return true;
    }

    public static boolean delEqCond(PrimitiveConstraint primitiveConstraint){
        return (   primitiveConstraint.el1.getType() == "Fc" ||
                    primitiveConstraint.el1.getType() == "Fv" || 
                    primitiveConstraint.el1.getType() == "Tv"
                ) &&
                primitiveConstraint.el1 == primitiveConstraint.el2;
    }

    public static boolean decEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.getType() == "Fa" &&
        primitiveConstraint.el2.getType() == "Fa" &&
       ((FunctionApplication) primitiveConstraint.el1).args.length == ((FunctionApplication) primitiveConstraint.el2).args.length &&
       ((FunctionApplication) primitiveConstraint.el2).args.length >= 1;
    }

    public static void decEqOp(FunctionApplication f1, FunctionApplication f2, List<PrimitiveConstraint> conjunction){
        for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new EqualityPredicate(f1.args[i], f2.args[i]));
        }
        conjunction.add(new EqualityPredicate(f1.functionSymbol, f2.functionSymbol));
    }


    public static boolean oriEqCond(PrimitiveConstraint primitiveConstraint){
        return (primitiveConstraint.el2.getType() == "Fv" || primitiveConstraint.el2.getType() == "Tv")
        && (primitiveConstraint.el1.getType() != "Fv" && primitiveConstraint.el1.getType() != "Tv");
    }

    public static void oriEqOp(PrimitiveConstraint primitiveConstraint, List<PrimitiveConstraint> conjunction){
        conjunction.add(new EqualityPredicate(primitiveConstraint.el2, primitiveConstraint.el1));
    }

    public static boolean elimEqCond(PrimitiveConstraint primitiveConstraint, List<PrimitiveConstraint> conjunction){
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

    public static void elimEqOp(PrimitiveConstraint primitiveConstraint, List<PrimitiveConstraint> conjunction){
        conjunction.replaceAll(x -> new PrimitiveConstraint(
            x.el1.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2),
            x.el2.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2)
        ));
        conjunction.addLast(primitiveConstraint);
    }

    public static boolean conflEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.getType() == "Fc" && primitiveConstraint.el2.getType() == "Fc" && primitiveConstraint.el1 != primitiveConstraint.el2;
    }

    public static boolean mismEqCond(PrimitiveConstraint primitiveConstraint){
        if(primitiveConstraint.el1.getType() == "Fa" && primitiveConstraint.el2.getType() == "Fa")
            return ((FunctionApplication )primitiveConstraint.el1).args.length != ((FunctionApplication )primitiveConstraint.el2).args.length;
        return false;
    }

    public static boolean occEqCond(PrimitiveConstraint primitiveConstraint){
        if(primitiveConstraint.el1.getType() != "Tv"){
            return false;
        }
        return primitiveConstraint.el1 != primitiveConstraint.el2 && primitiveConstraint.el2.contains(primitiveConstraint.el1);
    }

}
