import java.util.ArrayList;
import java.util.LinkedList;


public class Topological {
    ArrayList<Integer> _Active;
    LinkedList<Integer> Candidate;
    ArrayList<Integer> _inDegreeNumber;
    ArrayList<Integer> _s;
    String temp = "";
    private int _length;

    
    public Topological(ArrayList<LinkedList<Integer>> inDegreeList, ArrayList<LinkedList<Integer>> outDegreeList, int length) {
        _length = length;
        _Active = new ArrayList<Integer>();
        Candidate = new LinkedList<Integer>();
        _inDegreeNumber = new ArrayList<Integer>();
        _s = new ArrayList<Integer>();

        // make active list
        for (int i = 0; i < length; i++) {
            _Active.add(i);
            _inDegreeNumber.add(inDegreeList.get(i).size());
            if (_inDegreeNumber.get(i) == 0) {
                Candidate.add(i);
            }
        }
        while (Candidate.size() != 0) {
            int i = Candidate.poll();
            _s.add(i);

                for (int j = 0; j < outDegreeList.get(i).size(); j++) {
                    int temp = outDegreeList.get(i).get(j);
                    _inDegreeNumber.set(temp, _inDegreeNumber.get(temp)-1);
                    if (_inDegreeNumber.get(temp) == 0) {
                        Candidate.add(temp);
                    }
                }
        }
        System.out.println(_s + "topological");
    }

    public String returnTOrdering() {
        for (int i = 0; i < _length; i++) {
             temp = temp + _s.get(i).toString() + " ";
        }
        return temp;
    }
}