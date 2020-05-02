import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

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


    FileIO() throws Exception {
        // pass the path to the file as a parameter
        File file = new File("/Users/benjaminhoward/Documents/GitHub/250-hw4/homework4/test1.txt");
        Scanner sc = new Scanner(file);
        int i = 0;
        try {
            while (sc.hasNextLine()) {
                if (i == 0) {
                    int length = sc.nextInt();
                    System.out.println(_length);
                    _length = length;
                    i++;
                   
                    for (int j = 0; j < length; j++) {
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









        String output_name = "outputAA.txt";
        FileOutputStream fos = new FileOutputStream(output_name);

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

        new DFS(_outDegreeList, 0);


        fos.flush();
        fos.close();

        System.out.println(_inDegreeList);
        System.out.println(_outDegreeList);





    }

}