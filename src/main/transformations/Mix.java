package transformations;
import predicates.EqualityPredicate;
import predicates.PrimitiveConstraint;
import predicates.SimilarityPredicate;
import relations.Relation;
import relations.relationCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import constraintElements.*;
import dnf.*;
import utils.*;

public class Mix {

    static relationCollection relationCollection;

    public static boolean mix(Disjunction disjunction, List<PrimitiveConstraint> conjunction){
        if(conjunction.getFirst() instanceof SimilarityPredicate){
            SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.removeFirst();

            if(MismMixCond(similarityPredicate, conjunction) || occMixCond(conjunction, similarityPredicate)){
                conjunction.clear();
                return false;
            }
            if(TVEMixCond(similarityPredicate ,conjunction)){
                TVEMixop(similarityPredicate, conjunction);
            }
            if(FVEMixCond(similarityPredicate, conjunction)){
                FVEMixOp(similarityPredicate, conjunction, disjunction, relationCollection);
            }
        }
        return false;
    }

    private static boolean occMixCond(List<PrimitiveConstraint> conjunction, SimilarityPredicate similarityPredicate) {
        conjunction.addFirst(similarityPredicate);
        
        Map<Element, List<Element>> graph = new HashMap<>();

        for (PrimitiveConstraint pc : conjunction) {
            Element from = pc.el1;
            Element to = pc.el2;
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        Set<Element> visited = new HashSet<>();
        Set<Element> recStack = new HashSet<>();

        return hasCycleDFS(similarityPredicate.el1, graph, visited, recStack);
    }

    private static boolean MismMixCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        if(!(similarityPredicate.el1 instanceof TermVariable || similarityPredicate.el2 instanceof FunctionApplication)){
            return false;
        }
        for(PrimitiveConstraint pc : conjunction){
            if(pc instanceof SimilarityPredicate && pc.el1 instanceof TermVariable && pc.el2 instanceof FunctionApplication){
                SimilarityPredicate sp = (SimilarityPredicate) pc;
                if(sp.RelationId != similarityPredicate.RelationId && ( (FunctionApplication) sp.el2).args.length != ( (FunctionApplication) similarityPredicate.el2).args.length){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean TVEMixCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        if(!conjunction.stream().anyMatch(x -> x.el1.contains(similarityPredicate.el1) || x.el2.contains(similarityPredicate.el1))){
            return false;
        }        
        conjunction.addFirst(similarityPredicate);
        
        Map<Element, List<Element>> graph = new HashMap<>();

        for (PrimitiveConstraint pc : conjunction) {
            Element from = pc.el1;
            Element to = pc.el2;
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        Set<Element> visited = new HashSet<>();
        Set<Element> recStack = new HashSet<>();

        return hasCycleDFS(similarityPredicate.el1, graph, visited, recStack);
    }

    private static void TVEMixop(PrimitiveConstraint similarityPredicate, List<PrimitiveConstraint> conjunction) {
        FunctionApplication old = (FunctionApplication) similarityPredicate.el2;
        FunctionApplication renamedFunctionApplication = (FunctionApplication) renamingFunction((Term) old);
        conjunction.replaceAll(x -> 
        {
            if(x instanceof EqualityPredicate)
                return new EqualityPredicate(
                    x.el1.map(similarityPredicate.el1, similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1, similarityPredicate.el2)
                );
            else{
                return new SimilarityPredicate(
                    x.el1.map(similarityPredicate.el1, similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1, similarityPredicate.el2),( (SimilarityPredicate) x).RelationId , (( SimilarityPredicate) x).CutValue);

            }
        }
        );

        for(int i = 0; i < renamedFunctionApplication.args.length; i++){
            conjunction.add(new SimilarityPredicate(renamedFunctionApplication.args[i], old.args[i], ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
        }
        conjunction.add(new SimilarityPredicate(old.functionSymbol, renamedFunctionApplication.functionSymbol, ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
        conjunction.add(new SimilarityPredicate(similarityPredicate.el1, renamedFunctionApplication, ( (SimilarityPredicate) similarityPredicate).RelationId, ( (SimilarityPredicate) similarityPredicate).CutValue));
    }


    private static boolean FVEMixCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        if(similarityPredicate.el1 instanceof FunctionVariable && similarityPredicate.el2 instanceof FunctionConstant){
            return conjunction.stream().anyMatch(x -> x.el1.contains(similarityPredicate.el1) || x.el2.contains(similarityPredicate.el1));
        }
        return false;
    }

    private static void FVEMixOp(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction, Disjunction disjunction, relationCollection relationCollection) {
        List<Element> neighbarhood =  new LinkedList<>();

        for(Relation rel : relations.relationCollection.collection){
            if(
                rel.relId == similarityPredicate.RelationId 
                && rel.el1 == similarityPredicate.el2
                && relations.relationCollection.lookup(rel.el2, similarityPredicate.el2, rel.relId) >= similarityPredicate.CutValue 
            ){
                neighbarhood.add(rel.el2);
            }
            if (
                rel.relId == similarityPredicate.RelationId 
                && rel.el2 == similarityPredicate.el2
                && relations.relationCollection.lookup(rel.el1, similarityPredicate.el2, rel.relId) >= similarityPredicate.CutValue
                ) {
                neighbarhood.add(rel.el1);
            }
        }

        for(Element neighbour : neighbarhood){
            List<PrimitiveConstraint> newConj = conjunction.stream().map(x -> x.createCopy()).collect(Collectors.toList());

            newConj.replaceAll(x -> 
        {
            if(x instanceof EqualityPredicate)
                return new EqualityPredicate(
                    x.el1.map(similarityPredicate.el1, similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1, similarityPredicate.el2)
                );
            else{
                return new SimilarityPredicate(
                    x.el1.map(similarityPredicate.el1, similarityPredicate.el2),
                    x.el2.map(similarityPredicate.el1, similarityPredicate.el2),( (SimilarityPredicate) x).RelationId , (( SimilarityPredicate) x).CutValue);

            }
        }
        );
            
            newConj.addFirst(new SimilarityPredicate(similarityPredicate.el1, neighbour, similarityPredicate.RelationId, similarityPredicate.CutValue));
            disjunction.add(newConj);
        }


    }

    public static Term renamingFunction(Term trm){
        char newName;
        if(trm instanceof TermVariable){
            newName = NameGenerator.getNewTermVarName();
            NameGenerator.usedNames.add(newName);
            return new TermVariable(newName);
        }

        FunctionApplication fa = (FunctionApplication) trm;
        newName = NameGenerator.getNewFunVarName();
        NameGenerator.usedNames.add(newName);
        FunctionSymbol newFs = new FunctionVariable(newName);
        Term[] newArgs = new Term[fa.args.length];

        for(int i = 0; i < fa.args.length; i++){
            newArgs[i] = renamingFunction(fa.args[i]);
        }
            
        return new FunctionApplication(newFs, newArgs);
    }

    private static boolean hasCycleDFS(Element current, Map<Element, List<Element>> graph, 
                                Set<Element> visited, Set<Element> recStack) {
        if (recStack.contains(current)) {
            return true;  
        }
        if (visited.contains(current)) {
            return false;  
        }

        visited.add(current);
        recStack.add(current);

        List<Element> neighbors = graph.get(current);
        if (neighbors != null) {
            for (Element neighbor : neighbors) {
                if (hasCycleDFS(neighbor, graph, visited, recStack)) {
                    return true;
                }
            }
        }

        recStack.remove(current);
        return false;
    }

}
