package transformations;
import java.util.List;
import java.util.function.Predicate;

import constraintElements.FunctionApplication;
import dnf.Conjunction;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import relations.relationCollection;

public class Sim {

    static relationCollection relationCollection = new relationCollection();

    public static boolean sim(Conjunction conjunction){
        
        for(int i = 0; i < conjunction.constraints.size(); i++){
            if(conjunction.constraints.getFirst().isSolved){
                conjunction.constraints.addLast(conjunction.constraints.removeFirst());
                continue;
            }

            if(conjunction.constraints.getFirst() instanceof SimilarityPredicate){
                SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.constraints.removeFirst();
                if(delSimCond(similarityPredicate)){
                    i = -1;
                    continue;
                }
                if(Unif.decEqCond(similarityPredicate)){
                    decSimOp((FunctionApplication) similarityPredicate.el1, (FunctionApplication) similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction.constraints);
                    i = -1;
                    continue;
                }
                if(Unif.oriEqCond(similarityPredicate)){
                    oriSimOp(similarityPredicate, conjunction.constraints);
                    i = -1;
                    continue;
                }
                if(elimSimCond(similarityPredicate, conjunction.constraints)){
                    elimSimOp(similarityPredicate, conjunction);
                    i = -1;
                    continue;
                }
                if(conflSimCond(similarityPredicate) || Unif.mismEqCond(similarityPredicate) || Unif.occEqCond(similarityPredicate)){
                    conjunction.constraints.clear();
                    return false;
                }
                conjunction.constraints.addLast(similarityPredicate);
            }
    
        }
        return true;
    }

    private static boolean delSimCond(SimilarityPredicate similarityPredicate) {
        return Unif.delEqCond(similarityPredicate) 
         && relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) >= similarityPredicate.CutValue;
    }

    private static void decSimOp(FunctionApplication f1, FunctionApplication f2, int relId, double cutVal, List<PrimitiveConstraint> conjunction) {
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
    
    private static void elimSimOp(SimilarityPredicate similarityPredicate, Conjunction conjunction) {
        Predicate<PrimitiveConstraint> p = x -> x instanceof SimilarityPredicate && ((SimilarityPredicate)x).CutValue == similarityPredicate.CutValue && ((SimilarityPredicate)x).RelationId == similarityPredicate.RelationId;
        conjunction.map(similarityPredicate.el1, similarityPredicate.el2, p);
        conjunction.constraints.addLast(similarityPredicate);
    }

    private static boolean conflSimCond(SimilarityPredicate similarityPredicate) {
        return Unif.delEqCond(similarityPredicate) &&
        relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) < similarityPredicate.CutValue;
    }

}
