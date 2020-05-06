import java.util.ArrayList;
import java.util.LinkedList;

public class Topological {
    ArrayList<LinkedList<Integer>> _inDegreeList;
    ArrayList<LinkedList<Integer>> _outDegreeList;
    ArrayList<Integer> Active;
    LinkedList<Integer> Candidate;
    ArrayList<Integer> _inDegreeNumber;
    ArrayList<Integer> s;

    
    public Topological(ArrayList<LinkedList<Integer>> inDegreeList, ArrayList<LinkedList<Integer>> outDegreeList, int length) {
        _inDegreeList = inDegreeList;
        _outDegreeList = outDegreeList;
        Active = new ArrayList<Integer>();
        Candidate = new LinkedList<Integer>();
        _inDegreeNumber = new ArrayList<Integer>();
        s = new ArrayList<Integer>();

        // make active list
        for (int i = 0; i < length; i++) {
            Active.add(i);
            _inDegreeNumber.add(_inDegreeList.get(i).size());
            if (_inDegreeNumber.get(i) == 0) {
                Candidate.add(i);
            }
        }
        System.out.println(Candidate + "c");
        while (Candidate.size() != 0) {
            int i = Candidate.poll();
            System.out.println(i + "--");
            s.add(i);

                for (int j = 0; j < _outDegreeList.get(i).size(); j++) {
                    int temp = _outDegreeList.get(i).get(j);
                    _inDegreeNumber.set(temp, _inDegreeNumber.get(temp)-1);
                    if (_inDegreeNumber.get(temp) == 0) {
                        Candidate.add(temp);
                    }
                }
        }
        System.out.println(s);
    }


}