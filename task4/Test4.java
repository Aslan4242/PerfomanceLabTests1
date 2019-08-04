import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test4 {
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
        while (bufferedReader.ready()) {
            String s = bufferedReader.readLine();
            String s1 = "";
            Pattern p = Pattern.compile("[^\\\\n]");
            Matcher m = p.matcher(s);
            while (m.find()) {
                s1 += m.group();
            }
            list.add(s1);
        }
        bufferedReader.close();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");

        Date start = simpleDateFormat.parse("8:00");
        Date end = simpleDateFormat.parse("9:30");

        int maxVisitor = 0;
        long startPeriod = 0;
        long endPeriod = 0;
        int countVisitor = 0;
        ArrayList<Integer> visitors = new ArrayList<>();
        LinkedHashMap<Date[], Integer> periods = new LinkedHashMap<>();

        for (long i = start.getTime(); i <= end.getTime(); i += 60000) {
                 int previousCountVisitor = countVisitor;
            for (int j = 0; j < list.size(); j++) {
                String[] time1 = list.get(j).split("\\s+");
                Date dateStart = simpleDateFormat.parse(time1[0]);
                Date dateEnd = simpleDateFormat.parse(time1[1]);
                if (i >= dateStart.getTime() && i <= dateEnd.getTime() && !visitors.contains(j)) {
                    countVisitor++;
                    visitors.add(j);
                    startPeriod = dateStart.getTime();
                    endPeriod = dateEnd.getTime();
                }
                if ((i < dateStart.getTime() || i > dateEnd.getTime()) && visitors.contains(j)) {

                    countVisitor--;
                    visitors.set(visitors.indexOf(j), -1);
                }
            }
            if (previousCountVisitor > countVisitor) {
                endPeriod = i - 60000;
                Date[] dateArr = {new Date(startPeriod), new Date(endPeriod)};
                periods.put(dateArr, previousCountVisitor);
            }
            if (countVisitor > maxVisitor) maxVisitor = countVisitor;
        }
        //выводим результат
        for (Map.Entry<Date[], Integer> entry : periods.entrySet()) {
            if (maxVisitor == entry.getValue()) {
                System.out.println(simpleDateFormat.format(entry.getKey()[0])+" "+simpleDateFormat.format(entry.getKey()[1]));
            }
        }
    }
}
