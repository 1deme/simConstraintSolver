package transformations;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;

import java.util.List;

import constraintElements.*;
import dnf.*;

public class Mix {

    public static boolean mix(Disjunction disjunction, List<PrimitiveConstraint> conjunction){
        if(conjunction.getFirst() instanceof SimilarityPredicate){
            SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.removeFirst();

            if(MismMixCond(similarityPredicate, conjunction) || occMixCond(conjunction)){
                conjunction.clear();
                return false;
            }
            if(TVEMixCond(conjunction)){
                TVEMixop();
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

    private static void TVEMixop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TVEMixop'");
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

}
