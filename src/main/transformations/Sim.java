package transformations;
import java.util.List;
import constraintElements.FunctionApplication;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import relations.relationCollection;

public class Sim {

    static relationCollection relationCollection = new relationCollection();

    public static boolean sim(List<PrimitiveConstraint> conjunction){
        
        for(int i = 0; i < conjunction.size(); i++){
            if(conjunction.getFirst() instanceof SimilarityPredicate){
                SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.removeFirst();
                if(delSimCond(similarityPredicate)){
                    i = -1;
                    continue;
                }
                if(Unif.decEqCond(similarityPredicate)){
                    decSimOp((FunctionApplication) similarityPredicate.el1, (FunctionApplication) similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction);
                    i = -1;
                    continue;
                }
                if(Unif.oriEqCond(similarityPredicate)){
                    oriSimOp(similarityPredicate, conjunction);
                    i = -1;
                    continue;
                }
                if(elimSimCond(similarityPredicate, conjunction)){
                    elimSimOp(similarityPredicate, conjunction);
                    i = -1;
                    continue;
                }
                if(conflSimCond(similarityPredicate) || Unif.mismEqCond(similarityPredicate) || Unif.occEqCond(similarityPredicate)){
                    conjunction.clear();
                    return false;
                }
                conjunction.addLast(similarityPredicate);
            }
    
        }
        return true;
    }

    private static boolean delSimCond(SimilarityPredicate similarityPredicate) {
        return Unif.delEqCond(similarityPredicate) 
         && relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) >= similarityPredicate.CutValue;
    }

    private static void decSimOp(FunctionApplication f1, FunctionApplication f2, int relId, float cutVal, List<PrimitiveConstraint> conjunction) {
         for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new SimilarityPredicate(f1.args[i], f2.args[i], relId, cutVal));
        }
        conjunction.add(new SimilarityPredicate(f1.functionSymbol, f2.functionSymbol, relId, cutVal));
    }

    private static void oriSimOp(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        conjunction.add(
            new SimilarityPredicate(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue)
        );
    }

    private static boolean elimSimCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        boolean notInEl2 = similarityPredicate.el2.contains(similarityPredicate.el1);
        if(notInEl2 == true){
            return false;
        }
        for(PrimitiveConstraint pc : conjunction){
            if(pc instanceof SimilarityPredicate){
                SimilarityPredicate sp = (SimilarityPredicate) pc;
                if(
                    sp.RelationId == similarityPredicate.RelationId &&
                    sp.CutValue == similarityPredicate.CutValue &&
                    (pc.el1.contains(similarityPredicate.el1) || pc.el2.contains(similarityPredicate.el1))){
                    return true;
                }
            }
            
        }
        return false;
    }
    
    private static void elimSimOp(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        conjunction.replaceAll(x -> 
        {
            if(x instanceof SimilarityPredicate && ((SimilarityPredicate)x).CutValue == similarityPredicate.CutValue && ((SimilarityPredicate)x).RelationId == similarityPredicate.RelationId)
                return new PrimitiveConstraint(
                    x.el1.map(similarityPredicate.el1.getName(), similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1.getName(), similarityPredicate.el2)
                );
            else{
                return x;
            }
        }
        );
        conjunction.addLast(similarityPredicate);
    }

    private static boolean conflSimCond(SimilarityPredicate similarityPredicate) {
        return Unif.delEqCond(similarityPredicate) &&
        relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) < similarityPredicate.CutValue;
    }

}
