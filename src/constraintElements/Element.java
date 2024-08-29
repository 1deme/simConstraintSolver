package constraintElements;

public interface Element {
    Element map(char from, Element to);
    public String toString();
    public String getType();
    public boolean contains(Element el);
    public char getName();
}
