package relations;
import constraintElements.Element;

import java.util.Map;
import java.util.HashMap;

public class relationCollection {

    public Map<Relation, Float> collection;

    public relationCollection() {
        collection = new HashMap<>();
    }

    public void add(Element el1, Element el2, int relId, float value) {
        Relation pair = new Relation(el1, el2, relId, value);
        collection.put(pair, value);
    }

    public Float lookup(Element el1, Element el2, int relId) {
        Relation key = new Relation(el1, el2, relId, 0.0f);
        return collection.get(key);
    }

}
