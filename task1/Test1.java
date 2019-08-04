package test1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> numbers = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
        while (bufferedReader.ready()) {
            String s = bufferedReader.readLine();
            String s1="";
            Pattern p = Pattern.compile("[^\\\\n]");
            Matcher m = p.matcher(s);
            while (m.find()){
                s1+=m.group();
            }
            numbers.add(Integer.parseInt(s1));
        }
        bufferedReader.close();
        //результаты
        System.out.println(get90Percentile(numbers));
        System.out.println(getMedian(numbers));
        System.out.println(getMaxValue(numbers));
        System.out.println(getMinValue(numbers));
        System.out.println(getAverageValue(numbers));

    }

    public static BigDecimal get90Percentile(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        double x = 0.9 * (numbers.size() - 1) + 1;
        double rest = new BigDecimal(x - (int) x).setScale(1, RoundingMode.UP).doubleValue();
        int position = (int) x;
        double d = numbers.get(position - 1) + rest * (numbers.get(position) - (numbers.get(position - 1)));
        BigDecimal result = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return result;
    }

    public static BigDecimal getMedian(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        if (numbers.size() % 2 != 0) {
            int middlePos = (int) numbers.size() / 2;
            double d = numbers.get(middlePos);
            BigDecimal result = new BigDecimal(d).setScale(2, RoundingMode.UP);
            return result;
        } else {
            int middlePos = (int) numbers.size() / 2;
            double d = (numbers.get(middlePos) + numbers.get(middlePos - 1)) / 2;
            BigDecimal result = new BigDecimal(d).setScale(2, RoundingMode.UP);
            return result;
        }
    }
    public static BigDecimal getMaxValue(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        BigDecimal result = new BigDecimal(numbers.get(numbers.size()-1)).setScale(2, RoundingMode.UP);
        return result;
    }
    public static BigDecimal getMinValue(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        BigDecimal result = new BigDecimal(numbers.get(0)).setScale(2, RoundingMode.UP);
        return result;
    }
    public static BigDecimal getAverageValue(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        double sum=0;
        for (int i :numbers ) {
            sum+=i;
        }
        double res = sum/numbers.size();
        BigDecimal result = new BigDecimal(res).setScale(2, RoundingMode.UP);
        return result;
    }
}
