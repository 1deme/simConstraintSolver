import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import constraintElements.*;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import relations.Relation;
import relations.relationCollection;
import dnf.*;

public class App {
    public static void main(String[] args) throws Exception {

        FunctionVariable X = new FunctionVariable('F');

        
        FunctionConstant white_circle = new FunctionConstant('W');
        FunctionConstant white_ellipse = new FunctionConstant('w');
        FunctionConstant gray_circle = new FunctionConstant('G');
        FunctionConstant gray_ellipse = new FunctionConstant('g');


        relationCollection.add(white_circle, gray_ellipse, 1, 0.5);
        relationCollection.add(white_ellipse, gray_circle, 1, 0.5);
        relationCollection.add(gray_circle, gray_ellipse, 2, 0.7);
        relationCollection.add(white_ellipse, white_circle, 2, 0.7);

        PrimitiveConstraint sp1 = new SimilarityPredicate(X, white_circle, 1, 0.4);
        PrimitiveConstraint sp2 = new SimilarityPredicate(X, gray_ellipse, 2, 0.5);

        List<PrimitiveConstraint> pc = new ArrayList<>();
        pc.add(sp1);
        pc.add(sp2);
        
        Conjunction c = new Conjunction(pc);
        System.out.println(c.toString()); 
        // for (PrimitiveConstraint pcc : c.conjunction) {
        //     pcc.map(X, gray_ellipse);
        //  } 
        c.map(X, gray_ellipse);
//        c.constraints.stream().map(x -> x.map(X, gray_ellipse)).collect(Collectors.toList());

        System.out.println(c.toString());

    }
}
