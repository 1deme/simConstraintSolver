import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();
    
    Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

    public boolean unif(){
        return Unif.unif(conjunction);
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
