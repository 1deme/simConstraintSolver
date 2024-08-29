import constraintElements.Element;

public class PrimitiveConstraint {

    Element el1;
    Element el2;

    PrimitiveConstraint(Element el1, Element el2){
        this.el1 = el1;
        this.el2 = el2;
    }

    public String toString(){
        return el1.toString() + " = " + el2.toString();
    }

}
