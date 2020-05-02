import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DFS {

    private Stack<Integer> _dfs_stack; 
    private ArrayList<Boolean> _dfs_explored;
    private ArrayList<LinkedList<Integer>> _graph;
    private ArrayList<Degree<Integer, Integer>> _dfs_tree;

    public DFS(ArrayList<LinkedList<Integer>> graph, int s) {
        _graph = graph;
        _dfs_stack = new Stack<Integer>();
        _dfs_explored = new ArrayList<Boolean>();
        _dfs_tree = new ArrayList<Degree<Integer, Integer>>();

        for (int i = 0; i <graph.size(); i++) {
            _dfs_explored.add(false);
        }

        DFS_IMPL(s);

        for (int l = 0; l < _dfs_tree.size(); l++) {
            System.out.println(_dfs_tree.get(l).outNode + " " + _dfs_tree.get(l).inNode);
        }

    }

    private void DFS_IMPL(int s) {
        _dfs_stack.push(s);
        _dfs_explored.set(s, true);

        while(!_dfs_stack.isEmpty()) {
            int vertex = _dfs_stack.peek();

            Iterator<Integer> vList = _graph.get(vertex).iterator();
            
            while(vList.hasNext()) {
                int nextVertex = vList.next();

                if(_dfs_explored.get(nextVertex) == false ) {
                    _dfs_tree.add(new Degree<Integer, Integer> (vertex, nextVertex));
                    DFS_IMPL(nextVertex);
                }
            }

            _dfs_stack.pop();
            break;
        }
    }









}