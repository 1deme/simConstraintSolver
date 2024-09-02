package dnf;
import java.util.Iterator;
import java.util.List;

import predicates.PrimitiveConstraint;
import transformations.Sim;
import transformations.Unif;

import java.util.ArrayList;


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
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator<PrimitiveConstraint> iterator = conjunction.iterator();

        while (iterator.hasNext()) {
            result.append(iterator.next().toString());
            if (iterator.hasNext()) {
                result.append(" ^ ");
            }
        }

        return result.toString();
    }
    
}
