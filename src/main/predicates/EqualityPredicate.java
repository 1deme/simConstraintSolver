package predicates;

import constraintElements.*;

public class EqualityPredicate extends PrimitiveConstraint{

    public EqualityPredicate(Element el1, Element el2){
        super(el1, el2);
    }

    public EqualityPredicate map(char from, Element to){
        return (EqualityPredicate) setEl1(el1.map(from, to)).setEl2(el2.map(from, to));
    }

    @Override
    public EqualityPredicate createCopy(){
        return new EqualityPredicate(el1.createCopy(), el2.createCopy());
    }
    
}
