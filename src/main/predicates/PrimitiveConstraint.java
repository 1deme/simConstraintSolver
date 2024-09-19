package predicates;
import constraintElements.Element;

public class PrimitiveConstraint {

    public Element el1;
    public Element el2;

    public PrimitiveConstraint(Element el1, Element el2){
        this.el1 = el1;
        this.el2 = el2;
    }

    public String toString(){
        return el1.toString() + " = " + el2.toString();
    }

    public PrimitiveConstraint createCopy(){
        return null;
    }

    public PrimitiveConstraint setEl1(Element element){
        el1 = element;
        return this;
    }

    public PrimitiveConstraint setEl2(Element element){
        el2 = element;
        return this;
    }

}
