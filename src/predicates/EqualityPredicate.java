package predicates;

import constraintElements.*;

public class EqualityPredicate extends PrimitiveConstraint{

    public EqualityPredicate(Element el1, Element el2){
        super(el1, el2);
    }

    public EqualityPredicate map(char from, Element to){
        return new EqualityPredicate(el1.map(from, to), el2.map(from, to));
    }


    
}
