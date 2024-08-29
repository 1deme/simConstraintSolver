import constraintElements.*;

public class SimilarityPredicate extends PrimitiveConstraint{

    int RelationId;
    float CutValue;

    SimilarityPredicate(Element el1, Element el2, int RelationId, float CutValue){
        super(el1, el2);
        this.RelationId = RelationId;
        this.CutValue = CutValue;
    }

    public PrimitiveConstraint map(char from, Element to) {
        return new SimilarityPredicate(el1.map(from, to), el2.map(from, to), RelationId, CutValue);
    }
    
}
