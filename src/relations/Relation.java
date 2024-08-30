package relations;
import constraintElements.Element;
import java.util.Objects;

public class Relation {

    Element el1;
    Element el2;
    int relId;
    float value;

    public Relation(Element el1, Element el2, int relId, float value) {
        this.el1 = el1;
        this.el2 = el2;
        this.relId = relId;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(el1, el2, relId);
    }

}
