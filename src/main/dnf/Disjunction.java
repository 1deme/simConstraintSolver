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

    public void add(Conjunction c){
        disjunction.add(c);
    }

    public boolean apprSolvedForm(){
        return disjunction.stream().allMatch(x -> x.apprSolvedForm());
    }

    public void Sim(){
        disjunction.removeIf(consj -> !consj.Sim());
    }

    public void Unif(){
        disjunction.removeIf(consj -> !consj.Unif());
    }

    public void Mix(){
        transformations.Mix.mix(this, disjunction.removeFirst());
        //transformations.Mix.mix(this, disjunction.removeFirst().conjunction);

        
    }

    public void solve(){
        
        while (!apprSolvedForm()) {

            this.Unif();
            if(disjunction.size() == 0) return;
            
            this.Sim();
            if(disjunction.size() == 0) return;
            
            this.Mix();
            if(disjunction.size() == 0) return;
            
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < disjunction.size(); i++) {
            sb.append(disjunction.get(i));
            if (i < disjunction.size() - 1) {
                sb.append(" ∨ "); 
            }
        }
        return sb.toString();
    }

    
}
