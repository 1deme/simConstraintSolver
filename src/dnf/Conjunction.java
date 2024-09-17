package dnf;
import java.util.List;

import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;

import java.util.ArrayList;


public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();

    public Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

    public boolean Sim(){
        return transformations.Sim.sim(conjunction);
    }

    public boolean Unif(){
        return transformations.Unif.unif(conjunction);
    }

    public boolean apprSolvedForm(){

        for(int i = 0; i < conjunction.size(); i++){
            PrimitiveConstraint curr = conjunction.get(i);

            if(curr instanceof SimilarityPredicate && curr.el1.isVariable() && curr.el2.isVariable() ){
                for(int j = 0; j < conjunction.size(); j++){
                    PrimitiveConstraint other = conjunction.get(j);
                    if(curr instanceof SimilarityPredicate && curr.el1.isVariable() && curr.el2.isVariable() ){
                        continue;
                    }
                    if(j != i && (other.el1.contains(curr.el1) || other.el1.contains(curr.el2))){
                        return false;
                    }
                }
                continue;
            }

            for(int j = i + 1; j < conjunction.size(); j++){
                PrimitiveConstraint next = conjunction.get(j);
                if(curr.el1.getType() != "Fv" || curr.el1.getType() != "Tv"){
                    return false;
                }
                if(curr.el2.contains(curr.el1) || next.el1.contains(curr.el1) || next.el2.contains(curr.el1)){
                    return false;
                }

            }

        }
        return true;
    }

}
