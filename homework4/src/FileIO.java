import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private ArrayList<ArrayList<Integer>> _cycles = new ArrayList<ArrayList<Integer>>();
    String _fileInput;

    
    FileIO(String fileInput) throws Exception {
        _fileInput = fileInput;
        // pass the path to the file as a parameter
        File file = new File(_fileInput);
        int i = 0;
        try {
            
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                if (i == 0) {
                    _length = sc.nextInt();
                    if (_length == -1) {
                        System.out.println("ERROR: file not loaded correctly!");
                        sc.close();
                        return;
                    }
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
                }
            }
            sc.close();


            System.out.println("in " + _inDegreeList);
            System.out.println("out " + _outDegreeList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
    }

         
    public void outputFile() {

        // find average degrees --------------------------------------
        for (int j = 0; j < _length; j++) {
            _inDegree += _inDegreeList.get(j).size();
            _outDegree += _outDegreeList.get(j).size();
        }
        // format average degrees
        _avgInDegree = (double) _inDegree / _length;
        _avgOutDegree = (double) _outDegree / _length;
        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);  

        // find all cycles from each node -----------------------------
        for (int i = 0; i <_length; i++) {
            _cycles.addAll(new DFS(_outDegreeList, i).returnCycles());
        }
        ArrayList<ArrayList<Integer>> _distinctCycles = _cycles;
       

        // sort cycles
        for (int i = 0; i <_distinctCycles.size(); i++) {
            Collections.sort(_distinctCycles.get(i));
        }
        //make all cycles distinct & print
        ArrayList<ArrayList<Integer>> _numCycles = (ArrayList<ArrayList<Integer>>) _distinctCycles.stream().distinct()
                .collect(Collectors.toList());
         System.out.println("distinct cycles: " + _distinctCycles);
         System.out.println("number of distinct cycles: " + _distinctCycles.size());

        // begin writing the ouput
        try {
        String output_name = "output" + _fileInput;
        FileOutputStream fos = new FileOutputStream(output_name);
        
        // write all nodes with same in and out degrees.
        for (int k = 0; k < _length; k++) {
            if (_inDegreeList.get(k).size() == _outDegreeList.get(k).size()) {
                fos.write(String.valueOf(k).getBytes(), 0, 1);
                fos.write(' ');
            }
        }
        // write average in and out degrees.
        fos.write('\n');
        fos.write(df.format(_avgInDegree).getBytes(), 0, df.format(_avgInDegree).length());
        fos.write(' ');
        fos.write(df.format(_avgOutDegree).getBytes(), 0, df.format(_avgOutDegree).length());
        fos.write('\n');

         if (_numCycles.size() == 0) { // then topological
            fos.write(("Order:").getBytes(), 0, 6);
            fos.write('\n');
            Topological topo = new Topological(_inDegreeList, _outDegreeList, _length);
            fos.write(topo.returnTOrdering().getBytes(),0,_length*2);
            fos.write('\n');
         } else { // then cycle
             // grab first cycle
             ArrayList<Integer> outputCycle = _cycles.get(0);
             fos.write(("Cycle(s):").getBytes(), 0, 9);
             fos.write('\n');
             String temp = "";
             for (int j = 0; j < outputCycle.size(); j++) {
                temp = temp + outputCycle.get(j).toString() + " ";
             }
             fos.write(temp.getBytes(), 0, _distinctCycles.get(0).size()*2);
             fos.write('\n');

         }

         if (_numCycles.size() > 3) {
            fos.write(("No").getBytes(), 0, 2);
         } else {
            fos.write(("Yes").getBytes(), 0, 3);
         }

        fos.flush();
        fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}