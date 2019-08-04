package test3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    public static void main(String[] args) throws IOException {
       double[] arr = new double[16];
        File path = new File(args[0]);
        File[] listFiles = path.listFiles();
        int j =0;
        for(int i = 0; i < listFiles.length; i++){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(listFiles[i]));
            while (bufferedReader.ready()){
                String s = bufferedReader.readLine();
                Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+");
                Matcher m = p.matcher(s);
                while (m.find()){
                    arr[j]+=Double.parseDouble(m.group());
                    j++;
                    if (j==16) j=0;
                }
            }
            bufferedReader.close();
        }
        double max = 0;
        int position = 0;
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]>max){
                max=arr[i];
                position = i+1;
            }
        }
        System.out.println(position);
    }
}
