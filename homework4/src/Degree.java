
public class Degree<A, B> {
    public A outNode;
    public B inNode;
    
    public Degree(A outNode, B inNode) {
        this.outNode = outNode;
        this.inNode = inNode;
    }
}