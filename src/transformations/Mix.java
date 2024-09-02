package transformations;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import java.util.List;
import constraintElements.*;
import dnf.*;
import utils.*;

public class Mix {

    public static boolean mix(Disjunction disjunction, List<PrimitiveConstraint> conjunction){
        if(conjunction.getFirst() instanceof SimilarityPredicate){
            SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.removeFirst();

            if(MismMixCond(similarityPredicate, conjunction) || occMixCond(conjunction)){
                conjunction.clear();
                return false;
            }
            if(TVEMixCond(conjunction)){
                TVEMixop(similarityPredicate, conjunction);
            }
            if(FVEMixCond(similarityPredicate, conjunction)){
                FVEMixOp();
            }
        }
        return false;
    }

    private static boolean occMixCond(List<PrimitiveConstraint> conjunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'occMixCond'");
    }


    private static boolean MismMixCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        if(!(similarityPredicate.el1 instanceof TermVariable || similarityPredicate.el2 instanceof FunctionApplication)){
            return false;
        }
        for(PrimitiveConstraint pc : conjunction){
            if(pc instanceof SimilarityPredicate && pc.el1 instanceof TermVariable && pc.el2 instanceof FunctionApplication){
                SimilarityPredicate sp = (SimilarityPredicate) pc;
                if(sp.RelationId != similarityPredicate.RelationId && ( (FunctionApplication) sp.el2).args.length != ( (FunctionApplication) similarityPredicate.el2).args.length){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean TVEMixCond(List<PrimitiveConstraint> conjunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TVEMixCond'");
    }

    private static void TVEMixop(PrimitiveConstraint similarityPredicate, List<PrimitiveConstraint> conjunction) {
        FunctionApplication old = (FunctionApplication) similarityPredicate.el2;
        FunctionApplication renamedFunctionApplication = (FunctionApplication) renamingFunction((Term) old);
        conjunction.replaceAll(x -> 
        {
            if(x instanceof EqualityPredicate)
                return new EqualityPredicate(
                    x.el1.map(similarityPredicate.el1.getName(), similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1.getName(), similarityPredicate.el2)
                );
            else{
                return new SimilarityPredicate(
                    x.el1.map(similarityPredicate.el1.getName(), similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1.getName(), similarityPredicate.el2),( (SimilarityPredicate) x).RelationId , (( SimilarityPredicate) x).CutValue);

            }
        }
        );

        for(int i = 0; i < renamedFunctionApplication.args.length; i++){
            conjunction.add(new SimilarityPredicate(renamedFunctionApplication.args[i], old.args[i], ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
        }
        conjunction.add(new SimilarityPredicate(old.functionSymbol, renamedFunctionApplication.functionSymbol, ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
        conjunction.add(new SimilarityPredicate(similarityPredicate.el1, renamedFunctionApplication, ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
    }


    private static boolean FVEMixCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        if(similarityPredicate.el1 instanceof FunctionVariable && similarityPredicate.el2 instanceof FunctionConstant){
            return conjunction.stream().anyMatch(x -> x.el1.contains(similarityPredicate.el1) || x.el2.contains(similarityPredicate.el1));
        }
        return false;
    }

    private static void FVEMixOp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FVEMixOp'");
    }

    public static Term renamingFunction(Term trm){
        char newName;
        if(trm instanceof TermVariable){
            newName = NameGenerator.getNewTermVarName();
            NameGenerator.usedNames.add(newName);
            return new TermVariable(newName);
        }

        FunctionApplication fa = (FunctionApplication) trm;
        newName = NameGenerator.getNewFunVarName();
        NameGenerator.usedNames.add(newName);
        FunctionSymbol newFs = new FunctionVariable(newName);
        Term[] newArgs = new Term[fa.args.length];

        for(int i = 0; i < fa.args.length; i++){
            newArgs[i] = renamingFunction(fa.args[i]);
        }
            
        return new FunctionApplication(newFs, newArgs);
    }

}
