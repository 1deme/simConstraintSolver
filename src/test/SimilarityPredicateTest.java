package test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constraintElements.Element;
import constraintElements.FunctionApplication;
import constraintElements.FunctionConstant;
import constraintElements.FunctionSymbol;
import constraintElements.FunctionVariable;
import constraintElements.Term;
import constraintElements.TermVariable;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;

public class SimilarityPredicateTest {
    
    private SimilarityPredicate predicate;

    @BeforeEach
    void setUp() {

        FunctionConstant f = new FunctionConstant('f');
        FunctionConstant g = new FunctionConstant('g');
        FunctionConstant h = new FunctionConstant('h');

        TermVariable x = new TermVariable('X');
        TermVariable y = new TermVariable('Y');
        TermVariable z = new TermVariable('Z');

        FunctionVariable F = new FunctionVariable('F');
        FunctionVariable G = new FunctionVariable('G');
        FunctionVariable H = new FunctionVariable('H');

        FunctionApplication fx = new FunctionApplication(f, new Term[]{x});
        FunctionApplication Fx = new FunctionApplication(F, new Term[]{y, z});
        Term[] args = new Term[]{Fx, fx};
        FunctionApplication gfxFx = new FunctionApplication(g, args);

        predicate = new SimilarityPredicate(gfxFx, Fx, 1, 0.75f);

    }
    @Test
    void testCreateCopy() {

        SimilarityPredicate copy = predicate.createCopy();

        // Assert
        assertNotSame(predicate, copy); 
        assertEquals(predicate.RelationId, copy.RelationId);
        assertEquals(predicate.CutValue, copy.CutValue);
        assertNotSame(predicate.el1, copy.el1);
        assertNotSame(predicate.el2, copy.el2);
        assertEquals(predicate.el1.toString(), copy.el1.toString());
        assertEquals(predicate.el2.toString(), copy.el2.toString());

    }

    @Test
    void testForNoneExisting() {

        char from = 'a';
        Element to = new TermVariable('d');

        PrimitiveConstraint mappedConstraint = predicate.map(from, to);

        assertEquals(predicate, mappedConstraint);

    }

    @Test
    void testForExisting() {

        char from = 'F';
        FunctionConstant to = new FunctionConstant('f');

        PrimitiveConstraint mappedConstraint = predicate.map(from, to);

        assertNotEquals(predicate, mappedConstraint);
        assertNotEquals(predicate, mappedConstraint);

    }
    
}
