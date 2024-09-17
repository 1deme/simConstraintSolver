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

    public void Sim(){
        disjunction.removeIf(consj -> !consj.Sim());
    }

    public void Unif(){
        disjunction.removeIf(consj -> !consj.Unif());
    }

    public void Mix(){
        int i = 0;
        while (i < disjunction.size()) {
            int memo = disjunction.size();
            transformations.Mix.mix(this, disjunction.removeFirst().conjunction);
            if(memo == disjunction.size()){
                i++;
            }
            else{
                i = 0;
            }
        }
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

    
}
