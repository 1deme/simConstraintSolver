package relations;
import constraintElements.Element;

import java.util.Map;
import java.util.HashMap;

public class relationCollection {

    public static Map<Relation, Double> collection = new HashMap<>();

    // public relationCollection() {
    //     collection = new HashMap<>();
    // }

    public static void add(Element el1, Element el2, int relId, Double value) {
        Relation pair = new Relation(el1, el2, relId, value);
        collection.put(pair, value);
    }

    public static Double lookup(Element el1, Element el2, int relId) {
        Relation key = new Relation(el1, el2, relId, 0.0f);
        return collection.get(key);
    }

}
