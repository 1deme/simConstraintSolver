package dnf;
import java.util.List;

import predicates.PrimitiveConstraint;

public class Disjunction {

    public List<Conjunction> conjunctions;

    public Disjunction(List<Conjunction> disjunction){
        this.conjunctions = disjunction;
    }

    public void add(List<PrimitiveConstraint> pc){
        conjunctions.add(new Conjunction(pc));
    }

    public void add(Conjunction c){
        conjunctions.add(c);
    }

    public boolean apprSolvedForm(){
        return conjunctions.stream().allMatch(x -> x.apprSolvedForm());
    }

    public void Sim(){
        conjunctions.removeIf(consj -> !consj.Sim());
    }

    public void Unif(){
        conjunctions.removeIf(consj -> !consj.Unif());
    }

    public void Mix(){
        transformations.Mix.mix(this, conjunctions.removeFirst());
        //transformations.Mix.mix(this, disjunction.removeFirst().conjunction);

        
    }

    public void solve(){
        
        while (!apprSolvedForm()) {

            this.Unif();
            if(conjunctions.size() == 0) return;
            
            this.Sim();
            if(conjunctions.size() == 0) return;
            
            this.Mix();
            if(conjunctions.size() == 0) return;
            
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < conjunctions.size(); i++) {
            sb.append(conjunctions.get(i));
            if (i < conjunctions.size() - 1) {
                sb.append(" ∨ "); 
            }
        }
        return sb.toString();
    }

    
}
