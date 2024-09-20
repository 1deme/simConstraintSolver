package transformations;
import constraintElements.FunctionApplication;
import constraintElements.FunctionConstant;
import constraintElements.TermVariable;
import dnf.Conjunction;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;

import java.util.List;

public class Unif {

    public static boolean unif(Conjunction conjunction){
        
        for(int i = 0; i < conjunction.conjunction.size(); i++){
            PrimitiveConstraint primitiveConstraint = conjunction.conjunction.removeFirst();
            if(delEqCond(primitiveConstraint)){
                i = -1;
                continue;
            }
            if(decEqCond(primitiveConstraint)){
                decEqOp((FunctionApplication) primitiveConstraint.el1, (FunctionApplication) primitiveConstraint.el2, conjunction.conjunction);
                i = -1;
                continue;
            }
            if(oriEqCond(primitiveConstraint)){
                oriEqOp(primitiveConstraint, conjunction.conjunction);
                i = -1;
                continue;
            }
            if(elimEqCond(primitiveConstraint, conjunction)){
                elimEqOp(primitiveConstraint, conjunction.conjunction);
                i = -1;
                continue;
            }
            if(conflEqCond(primitiveConstraint) || mismEqCond(primitiveConstraint) || occEqCond(primitiveConstraint)){
                conjunction.conjunction.clear();
                return false;
            }
            conjunction.conjunction.addLast(primitiveConstraint);
        }
        return true;
    }

    public static boolean delEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.isAtomic() && primitiveConstraint.el1.equals(primitiveConstraint.el2);
    }

    public static boolean decEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1 instanceof FunctionApplication 
            && primitiveConstraint.el2 instanceof FunctionApplication 
            && ((FunctionApplication) primitiveConstraint.el1).args.length == ((FunctionApplication) primitiveConstraint.el2).args.length;
    }

    public static void decEqOp(FunctionApplication f1, FunctionApplication f2, List<PrimitiveConstraint> conjunction){
        for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new EqualityPredicate(f1.args[i], f2.args[i]));
        }
        conjunction.add(new EqualityPredicate(f1.functionSymbol, f2.functionSymbol));
    }


    public static boolean oriEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el2.isVariable() && !primitiveConstraint.el1.isVariable();
    }

    public static void oriEqOp(PrimitiveConstraint primitiveConstraint, List<PrimitiveConstraint> conjunction){
        conjunction.add(new EqualityPredicate(primitiveConstraint.el2, primitiveConstraint.el1));
    }

    public static boolean elimEqCond(PrimitiveConstraint primitiveConstraint, Conjunction conjunction){
        return !primitiveConstraint.el2.contains(primitiveConstraint.el1) && conjunction.containt(primitiveConstraint.el1);
    }

    public static void elimEqOp(PrimitiveConstraint primitiveConstraint, List<PrimitiveConstraint> conjunction){
        conjunction.replaceAll(x -> {
            if(x instanceof EqualityPredicate)
                return new EqualityPredicate(
                    x.el1.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2),
                    x.el2.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2)
                );
            else{
                return new SimilarityPredicate(
                    x.el1.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2),
                    x.el2.map(primitiveConstraint.el1.getName(), primitiveConstraint.el2),( (SimilarityPredicate) x).RelationId , (( SimilarityPredicate) x).CutValue);

            }
        });
        conjunction.addLast(primitiveConstraint);
    }

    public static boolean conflEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1 instanceof FunctionConstant 
            && primitiveConstraint.el2 instanceof FunctionConstant 
            && primitiveConstraint.el1 != primitiveConstraint.el2;
    }

    public static boolean mismEqCond(PrimitiveConstraint primitiveConstraint){
        return (primitiveConstraint.el1 instanceof FunctionApplication 
            && primitiveConstraint.el2 instanceof FunctionApplication) 
            && ((FunctionApplication )primitiveConstraint.el1).args.length != ((FunctionApplication )primitiveConstraint.el2).args.length;   
    }

    public static boolean occEqCond(PrimitiveConstraint primitiveConstraint){
        return !(primitiveConstraint.el1 instanceof TermVariable) 
            && primitiveConstraint.el1 != primitiveConstraint.el2
            && primitiveConstraint.el2.contains(primitiveConstraint.el1);
    }

}
