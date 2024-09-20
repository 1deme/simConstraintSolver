package test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constraintElements.Element;
import constraintElements.FunctionApplication;
import constraintElements.FunctionConstant;
import constraintElements.FunctionVariable;
import constraintElements.Term;
import constraintElements.TermVariable;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;

public class EqualityPredicateTest {
    
    private EqualityPredicate predicate;

    @BeforeEach
    void setUp() {

        FunctionConstant f = new FunctionConstant('f');
        FunctionConstant g = new FunctionConstant('g');

        TermVariable x = new TermVariable('X');
        TermVariable y = new TermVariable('Y');
        TermVariable z = new TermVariable('Z');

        FunctionVariable F = new FunctionVariable('F');

        FunctionApplication fx = new FunctionApplication(f, new Term[]{x});
        FunctionApplication Fx = new FunctionApplication(F, new Term[]{y, z});
        Term[] args = new Term[]{Fx, fx};
        FunctionApplication gfxFx = new FunctionApplication(g, args);

        predicate = new EqualityPredicate(gfxFx, Fx);

        System.out.println(predicate.toString());

    }
    @Test
    void testCreateCopy() {

        EqualityPredicate copy = predicate.createCopy();

        assertNotSame(predicate, copy);
        assertNotEquals(copy, predicate);
        assertEquals(predicate.toString(), copy.toString());

    }

    @Test
    void testForNoneExisting() {

        char from = 'a';
        Element to = new TermVariable('d');

        PrimitiveConstraint mappedConstraint = predicate.map(from, to);
        assertSame(predicate, mappedConstraint);
        assertEquals(predicate, mappedConstraint);
        assertEquals(predicate.toString(), mappedConstraint.toString());

    }

    @Test
    void testForExisting2() {

        char from = 'F';
        FunctionConstant to = new FunctionConstant('f');
        
        String s = "g(f(Y, Z), f(X)) = f(Y, Z)";

        assertEquals(predicate.map(from, to).toString(), s);

    }

}
