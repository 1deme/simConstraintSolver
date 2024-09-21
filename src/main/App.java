import java.util.ArrayList;
import java.util.List;
import constraintElements.*;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import relations.Relation;
import relations.relationCollection;
import dnf.*;

public class App {
    public static void main(String[] args) throws Exception {

        // FunctionVariable F = new FunctionVariable('F');
        // FunctionVariable G = new FunctionVariable('G');
        // FunctionVariable H = new FunctionVariable('H');

        // FunctionConstant f = new FunctionConstant('f');
        // FunctionConstant g = new FunctionConstant('g');
        // FunctionConstant h = new FunctionConstant('h');

        // TermVariable X = new TermVariable('X');
        // TermVariable Y = new TermVariable('Y');
        // TermVariable Z = new TermVariable('Z');


        // FunctionApplication fa0 = new FunctionApplication(f,  new Term[]{X});//f(x)

        // EqualityPredicate e = new EqualityPredicate(Z, fa0); // Z = f(x)
        // EqualityPredicate e1 = new EqualityPredicate(G, Z); // G = Z

        // SimilarityPredicate sim = new SimilarityPredicate(Z, fa0, 0, 0);

        // List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();
        // conjunction.add(sim);
        // conjunction.add(e);
        // conjunction.add(e1);
        // Conjunction c = new Conjunction(conjunction);
        // c.Unif();
        // c.Sim();
        // System.out.println(c.toString());

        FunctionVariable X = new FunctionVariable('F');

        
        FunctionConstant white_circle = new FunctionConstant('1');
        FunctionConstant white_ellipse = new FunctionConstant('2');
        FunctionConstant gray_circle = new FunctionConstant('3');
        FunctionConstant gray_ellipse = new FunctionConstant('4');


        relationCollection.add(white_circle, gray_ellipse, 1, 0.5);
        relationCollection.add(white_ellipse, gray_circle, 1, 0.5);
        relationCollection.add(gray_circle, gray_ellipse, 2, 0.7);
        relationCollection.add(white_ellipse, white_circle, 2, 0.7);

        SimilarityPredicate sp1 = new SimilarityPredicate(X, white_circle, 1, 0.4);
        SimilarityPredicate sp2 = new SimilarityPredicate(X, gray_ellipse, 2, 0.5);

        List<PrimitiveConstraint> pc = new ArrayList<>();
        pc.add(sp1);
        pc.add(sp2);
        Conjunction c = new Conjunction(pc);

        List<Conjunction> cl = new ArrayList<>();
        cl.add(c);

        Disjunction d = new Disjunction(cl);
        
        System.out.println(d.toString());
        d.Sim();
        d.Mix();
        System.out.println(d.toString());//debug fve mix op

    }
}
