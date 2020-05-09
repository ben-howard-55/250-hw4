import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DFS {

    private Stack<Integer> _dfs_stack;
    private ArrayList<Boolean> _dfs_explored;
    private ArrayList<LinkedList<Integer>> _graph;
    private int _start;
    private ArrayList<ArrayList<Integer>> cycleList = new ArrayList<ArrayList<Integer>>();



    public DFS(ArrayList<LinkedList<Integer>> graph, int s) {
        _start = s;
        _graph = graph;
        _dfs_stack = new Stack<Integer>();
        _dfs_explored = new ArrayList<Boolean>();

        for (int i = 0; i < graph.size(); i++) {
            _dfs_explored.add(false);
        }

        DFS_IMPL(s);
    }

    public ArrayList<ArrayList<Integer>> returnCycles() {
        return cycleList;
    }

    private void DFS_IMPL(int s) {
        _dfs_stack.push(s);
        _dfs_explored.set(s, true);

        while (!_dfs_stack.isEmpty()) {
            int vertex = _dfs_stack.peek();

            Iterator<Integer> vList = _graph.get(vertex).iterator();

            while (vList.hasNext()) {
                int nextVertex = vList.next();

                if (_dfs_explored.get(nextVertex) == false) {
                    DFS_IMPL(nextVertex);
                } else if (_start == nextVertex) {
                    cycleList.add(new ArrayList<>(_dfs_stack));
                }
            }
            _dfs_stack.pop();
            break;
        }
    }
}