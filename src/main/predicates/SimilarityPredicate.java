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

    public PrimitiveConstraint setRelationId(int relationId){
        this.RelationId = relationId;
        return this;
    }

    public PrimitiveConstraint setCutValue(float CutValue){
        this.CutValue = CutValue;
        return this;
    }

    public PrimitiveConstraint map(char from, Element to) {
        return setEl1(el1.map(from, to)).setEl2(el2.map(from, to));
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
