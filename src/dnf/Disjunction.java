package dnf;
import java.util.List;

import predicates.PrimitiveConstraint;

public class Disjunction {

    public List<Conjunction> disjunction;

    public Disjunction(List<Conjunction> disjunction){
        this.disjunction = disjunction;
    }

    public void add(List<PrimitiveConstraint> pc){
        disjunction.add(new Conjunction(pc));
    }

    public boolean apprSolvedForm(){
        return disjunction.stream().allMatch(x -> x.apprSolvedForm());
    }

    
}
