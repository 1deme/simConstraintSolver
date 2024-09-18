package predicates;

import constraintElements.*;

public class SimilarityPredicate extends PrimitiveConstraint{

    public int RelationId;
    public float CutValue;

    public SimilarityPredicate(Element el1, Element el2, int RelationId, float CutValue){
        super(el1, el2);
        this.RelationId = RelationId;
        this.CutValue = CutValue;
    }

    public PrimitiveConstraint map(char from, Element to) {
        return new SimilarityPredicate(el1.map(from, to), el2.map(from, to), RelationId, CutValue);
    }

    @Override
    public SimilarityPredicate createCopy(){
        return new SimilarityPredicate(el1.createCopy(), el2.createCopy(), RelationId, CutValue);
    }

    @Override
    public String toString(){
        return el1.toString() + " ~= " + el2.toString();
    }
    
}
