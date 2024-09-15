package dnf;
import java.util.List;

import predicates.PrimitiveConstraint;

import java.util.ArrayList;


public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();

    public Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

}
