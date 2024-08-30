import java.util.List;

import constraintElements.FunctionApplication;

public class Sim {

    List<PrimitiveConstraint> conjunction;

    public boolean sim(List<PrimitiveConstraint> conjunction){
        
        this.conjunction = conjunction;
        for(int i = 0; i < conjunction.size(); i++){
            PrimitiveConstraint primitiveConstraint = conjunction.removeFirst();
            if(delSimCond(primitiveConstraint)){
                i = -1;
                continue;
            }
            if(decSimCond(primitiveConstraint)){
                decSimOp((FunctionApplication) primitiveConstraint.el1, (FunctionApplication) primitiveConstraint.el2);
                i = -1;
                continue;
            }
            if(oriSimCond(primitiveConstraint)){
                oriSimOp(primitiveConstraint);
                i = -1;
                continue;
            }
            if(elimSimCond(primitiveConstraint)){
                elimSimOp(primitiveConstraint);
                i = -1;
                continue;
            }
            if(conflSimCond(primitiveConstraint) || mismSimCond(primitiveConstraint) || occSimCond(primitiveConstraint)){
                conjunction.clear();
                return false;
            }
            conjunction.addLast(primitiveConstraint);
        }
        return true;
    }

    private boolean occSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'occSimCond'");
    }

    private boolean mismSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mismSimCond'");
    }

    private boolean conflSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflSimCond'");
    }

    private void elimSimOp(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimSimOp'");
    }

    private boolean elimSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimSimCond'");
    }

    private void oriSimOp(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'oriSimOp'");
    }

    private boolean oriSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'oriSimCond'");
    }

    private void decSimOp(FunctionApplication el1, FunctionApplication el2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decSimOp'");
    }

    private boolean decSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decSimCond'");
    }

    private boolean delSimCond(PrimitiveConstraint primitiveConstraint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delSimCond'");
    }
}
