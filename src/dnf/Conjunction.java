package dnf;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintElements.Element;
import predicates.PrimitiveConstraint;
import transformations.Sim;
import transformations.Unif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Conjunction {

    List<PrimitiveConstraint> conjunction = new ArrayList<PrimitiveConstraint>();

    public Conjunction(List<PrimitiveConstraint> conjunction){
        this.conjunction = conjunction;
    }

    public boolean unif(){
        return Unif.unif(conjunction);
    }

    public boolean sim(){
        return Sim.sim(conjunction);
    }

    public boolean isElementInCycle(Element startElement) {

        Map<Element, List<Element>> graph = new HashMap<>();

        for (PrimitiveConstraint pc : conjunction) {
            Element from = pc.el1;
            Element to = pc.el2;
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        Set<Element> visited = new HashSet<>();
        Set<Element> recStack = new HashSet<>();

        return hasCycleDFS(startElement, graph, visited, recStack);
    }

    private boolean hasCycleDFS(Element current, Map<Element, List<Element>> graph, 
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
