import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FileIO {

    private int _length;
    private int outTemp;
    private int inTemp;
    private ArrayList<LinkedList<Integer>> inDegreeList = new ArrayList<LinkedList<Integer>>();
    private ArrayList<LinkedList<Integer>> outDegreeList = new ArrayList<LinkedList<Integer>>();
    private int _inDegree = 0;
    private int _outDegree = 0;
    private double _avgInDegree;
    private double _avgOutDegree;


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
                        inDegreeList.add(new LinkedList<Integer>());
                        outDegreeList.add(new LinkedList<Integer>());
                    }

                } else {
                    outTemp = sc.nextInt();
                    inTemp = sc.nextInt();
                    outDegreeList.get(outTemp).add(inTemp);
                    inDegreeList.get(inTemp).add(outTemp);
                }
            }
        } finally {
            sc.close();
        }


        for (int j = 0; j < _length; j++) {
            _inDegree += inDegreeList.get(j).size();
            _outDegree += outDegreeList.get(j).size();
        }
        _avgInDegree = (double)_inDegree / _length;
        _avgOutDegree = (double)_outDegree / _length;









        String output_name = "outputAA.txt";
        FileOutputStream fos = new FileOutputStream(output_name);

        for (int k = 0; k < _length; k++) {
            if (inDegreeList.get(k).size() == outDegreeList.get(k).size()) {
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



        fos.flush();
        fos.close();





    }

}