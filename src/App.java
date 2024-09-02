import java.util.ArrayList;
import java.util.List;
import constraintElements.*;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import dnf.*;

public class App {
    public static void main(String[] args) throws Exception {

        FunctionVariable fVarf = new FunctionVariable('F');
        FunctionVariable fVarg = new FunctionVariable('G');
        //FunctionVariable fVarh = new FunctionVariable('H');

        FunctionConstant fConstf = new FunctionConstant('f');
        FunctionConstant fConstg = new FunctionConstant('g');
        FunctionConstant fConsth = new FunctionConstant('h');


        TermVariable tVarX = new TermVariable('X');
        TermVariable tVarY = new TermVariable('Y');
        TermVariable tVarZ = new TermVariable('Z');

        FunctionApplication fa0 = new FunctionApplication(fVarg,  new Term[]{tVarZ});

        FunctionApplication fa1 = new FunctionApplication(fVarf,  new Term[]{tVarX});
        //FunctionApplication fa2 = new FunctionApplication(fVarg, new Term[]{fa0});
        FunctionApplication fa3 = new FunctionApplication(fConstg, new Term[]{ tVarZ});

        FunctionApplication e1 = new FunctionApplication(fConsth, new Term[]{tVarX});
        FunctionApplication e2 = new FunctionApplication(fConsth, new Term[]{fa0});
        FunctionApplication e3 = new FunctionApplication(fConsth, new Term[]{fa3});
        FunctionApplication e4 = new FunctionApplication(fConsth, new Term[]{tVarY});

        //PrimitiveConstraint pc1 = new EqualityPredicate(fVarf, fa1);
        PrimitiveConstraint pc2 = new EqualityPredicate(e1, e2);
        PrimitiveConstraint pc3 = new EqualityPredicate(e3, e4);


        List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();
        conjunction.add(pc3);
        conjunction.add(pc2);
        Conjunction c = new Conjunction(conjunction);
        System.out.println(c.toString());
        c.unif();
        System.out.println(c.toString());


    }
}
