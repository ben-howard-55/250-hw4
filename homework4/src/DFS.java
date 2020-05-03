import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DFS {

    private Stack<Integer> _dfs_stack; 
    private ArrayList<Boolean> _dfs_explored;
    private ArrayList<LinkedList<Integer>> _graph;
    private ArrayList<Degree<Integer, Integer>> _dfs_tree;
    private int _start;
    private int _end;
    private boolean _cycleFound = false;
    private String _temp;

    public DFS(ArrayList<LinkedList<Integer>> graph, int s) {
        _start = s;
        _graph = graph;
        _dfs_stack = new Stack<Integer>();
        _dfs_explored = new ArrayList<Boolean>();
        _dfs_tree = new ArrayList<Degree<Integer, Integer>>();

        for (int i = 0; i <graph.size(); i++) {
            _dfs_explored.add(false);
        }

        DFS_IMPL(s);
        

        if (_end == _start && _cycleFound) {
            _temp = _end + " " + _start;
        } else if (_cycleFound) {
            
            _temp = _dfs_tree.get(0).outNode + " " + _dfs_tree.get(0).inNode;
            for (int i = 1; i< _dfs_tree.size(); i++) {
                _temp = _temp + " " + _dfs_tree.get(i).inNode;
            }
        }
    }

    public String returnPath() {
        return _temp;
    }


    private void DFS_IMPL(int s) {
        _dfs_stack.push(s);
        _dfs_explored.set(s, true);

        while(!_dfs_stack.isEmpty()) {
            int vertex = _dfs_stack.peek();

            Iterator<Integer> vList = _graph.get(vertex).iterator();
            
            while(vList.hasNext() && !_cycleFound) {
                int nextVertex = vList.next();

                if(_dfs_explored.get(nextVertex) == false ) {
                    _dfs_tree.add(new Degree<Integer, Integer> (vertex, nextVertex));
                    DFS_IMPL(nextVertex);
                } else if (_start == nextVertex) {
                    _cycleFound = true;
                    _end = vertex;
                }
            }
            _dfs_stack.pop();
            break;
        }
    }
}