import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileIO {

    private int _length;
    private int _outTemp;
    private int _inTemp;
    private ArrayList<LinkedList<Integer>> _inDegreeList = new ArrayList<LinkedList<Integer>>();
    private ArrayList<LinkedList<Integer>> _outDegreeList = new ArrayList<LinkedList<Integer>>();
    private int _inDegree = 0;
    private int _outDegree = 0;
    private double _avgInDegree;
    private double _avgOutDegree;
    private ArrayList<Degree<Integer, Integer>> _edges = new ArrayList<Degree<Integer, Integer>>();
    private ArrayList<ArrayList<Integer>> _cycles = new ArrayList<ArrayList<Integer>>();
    
    FileIO() throws Exception {
        // pass the path to the file as a parameter
        File file = new File("/Users/benjaminhoward/Documents/GitHub/250-hw4/homework4/test2.txt");
        Scanner sc = new Scanner(file);
        int i = 0;
        try {
            while (sc.hasNextLine()) {
                if (i == 0) {
                    _length = sc.nextInt();
                    System.out.println(_length);
                    i++;
                   
                    for (int j = 0; j < _length; j++) {
                        _inDegreeList.add(new LinkedList<Integer>());
                        _outDegreeList.add(new LinkedList<Integer>());
                    }

                } else {
                    _outTemp = sc.nextInt();
                    _inTemp = sc.nextInt();
                    _outDegreeList.get(_outTemp).add(_inTemp);
                    _inDegreeList.get(_inTemp).add(_outTemp);
                    _edges.add(new Degree<Integer, Integer>(_outTemp, _inTemp));
                }
            }
        } finally {
            sc.close();
        }


        for (int j = 0; j < _length; j++) {
            _inDegree += _inDegreeList.get(j).size();
            _outDegree += _outDegreeList.get(j).size();
        }
        _avgInDegree = (double)_inDegree / _length;
        _avgOutDegree = (double)_outDegree / _length;

        String output_name = "outputAAA.txt";
        FileOutputStream fos = new FileOutputStream(output_name);
        try {
        
        for (int k = 0; k < _length; k++) {
            if (_inDegreeList.get(k).size() == _outDegreeList.get(k).size()) {
                fos.write(String.valueOf(k).getBytes(), 0, 1);
                fos.write(' ');
            }
        }

        fos.write('\n');
        fos.write(String.format("%.5f", _avgInDegree).getBytes(), 0, 7);
        fos.write(' ');
        fos.write(String.format("%.5f", _avgOutDegree).getBytes(), 0, 7);
        fos.write(' ');
        fos.write('\n');

        // find all cycles
        for (i = 0; i <_length; i++) {
            _cycles.addAll( new DFS(_outDegreeList, i).returnCycles());
         }
         //make all cycles distinct & print
         ArrayList<ArrayList<Integer>> _distinctCycles = (ArrayList<ArrayList<Integer>>) _cycles.stream().distinct().collect(Collectors.toList());
         System.out.println("distinct cycles: " + _distinctCycles);
         System.out.println("number of distinct cycles: " + _distinctCycles.size());

         if (_distinctCycles.size() == 0) {
            fos.write(("Order:").getBytes(), 0, 6);
            fos.write('\n');
            // Topological topo = new Topological(_inDegreeList, _outDegreeList, _length);
            // fos.write(topo.returnTOrdering().getBytes(),0,_length*2);
            fos.write('\n');
         } else {
             fos.write(("Cycle(s):").getBytes(), 0, 9);
             fos.write('\n');
             String temp = "";
             for (int j = 0; j < _distinctCycles.get(0).size(); j++) {
                temp = temp + _distinctCycles.get(0).get(j).toString() + " ";
             }
             fos.write(temp.getBytes(), 0, _distinctCycles.get(0).size()*2);
             fos.write('\n');

         }

         if (_distinctCycles.size() > 3) {
            fos.write(("No").getBytes(), 0, 2);
         } else {
            fos.write(("Yes").getBytes(), 0, 3);
         }

        fos.flush();

        } finally {
            fos.close();
        }
       

        System.out.println("in " + _inDegreeList);
        System.out.println("out " + _outDegreeList);
    }

}