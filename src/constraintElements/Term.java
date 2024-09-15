package constraintElements;

public interface Term extends Element {
    public Element map(char from, Element to);
    public Element createCopy();
}
