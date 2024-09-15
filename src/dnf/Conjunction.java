package dnf;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintElements.Element;
import predicates.PrimitiveConstraint;
import transformations.Sim;
import transformations.Unif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();

    public Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

    public boolean unif(){
        return Unif.unif(conjunction);
    }

    public boolean sim(){
        return Sim.sim(conjunction);
    }


}
