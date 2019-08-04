import javafx.scene.shape.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {
    public static void main(String[] args) throws IOException {
        ArrayList<double[]> figure = new ArrayList<>(); //координты четырехугольника
        ArrayList<double[]> dots = new ArrayList<>(); // координаты точек
        for (int i = 0; i < args.length; i++) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(args[i]));
            while (bufferedReader.ready()) {
                String s = bufferedReader.readLine();
                String s1="";
                Pattern p = Pattern.compile("[^\\\\n]");
                Matcher m = p.matcher(s);
                while (m.find()){
                    s1+=m.group();
                }
                String[] coords = s1.split("\\s+");
                double[] d = new double[coords.length];
                for (int j = 0; j < coords.length; j++) {
                    d[j] = Double.parseDouble(coords[j]);
                }
                if (i == 0) {
                    figure.add(d);
                } else {
                    dots.add(d);
                }
            }
            bufferedReader.close();
        }
        ArrayList<Integer> list = determinePosition(figure, dots); // результат
        for (int i : list) {
            System.out.print(i + "\n");
        }
    }

    public static ArrayList<Integer> determinePosition(ArrayList<double[]> figure, ArrayList<double[]> dots) {
        ArrayList<Integer> result = new ArrayList<>();
        double[] xCoord = new double[4];
        double[] yCoord = new double[4];
        for (int j = 0; j < figure.size(); ) {
            for (int i = 0; i < 4; i++) {
                xCoord[i] = figure.get(j)[0];
                yCoord[i] = figure.get(j)[1];
                j++;
            }
        }
        Polygon polygon = new Polygon(figure.get(0)[0],figure.get(0)[1],figure.get(1)[0],figure.get(1)[1],
                                      figure.get(2)[0],figure.get(2)[1],figure.get(3)[0],figure.get(3)[1]);

        //получаем стороны четырехугольника
        double[] line1 = {figure.get(0)[0], figure.get(0)[1], figure.get(1)[0], figure.get(1)[1]};
        double[] line2 = {figure.get(1)[0], figure.get(1)[0], figure.get(2)[0], figure.get(2)[1]};
        double[] line3 = {figure.get(2)[0], figure.get(2)[1], figure.get(3)[0], figure.get(3)[1]};
        double[] line4 = {figure.get(3)[0], figure.get(3)[1], figure.get(0)[0], figure.get(0)[1]};
        Arrays.sort(xCoord);
        Arrays.sort(yCoord);
        //получаем вершины четрыехугольника
        double maxX = xCoord[3];
        double minX = xCoord[0];
        double maxY = yCoord[3];
        double minY = yCoord[0];

        for (double[] dot : dots) {
            if (getLineContainsDot(line1, dot)||
                    getLineContainsDot(line2, dot)||
                    getLineContainsDot(line3, dot)||
                    getLineContainsDot(line4, dot)) {
                if ((dot[0] == maxX && dot[1] == maxY) ||// проверяем, находится ли точка на одной из вершин четырехугольника
                        (dot[0] == minX && dot[1] == maxY) ||
                        (dot[0] == minX && dot[1] == minY) ||
                        (dot[0] == maxX && dot[1] == minY)) {
                    result.add(0);
                } else {//если нет, то значит точка на одной из сторон четырехугольника
                    result.add(1);
                }
            } else if (!polygon.contains(dot[0],dot[1])) { // проверяем, находится ли точка снаружи четырехугольника
                result.add(3);
            } else {// если точка не соответствует предыдущим условиям, значит она внутри четырехугольника
                result.add(2);
            }
        }
        return result;
    }

    public static boolean getLineContainsDot(double[] arr, double[] dot) {
        double[] arrStart = {arr[0], arr[1]};
        double[] arrEnd = {arr[2], arr[3]};
        double lengthLine = distBetweenDots(arrStart, arrEnd);
        double a = distBetweenDots(arrStart, dot);
        double b = distBetweenDots(arrEnd, dot);
        if (lengthLine == a + b) {
            return true;
        }
        return false;
    }
    public static double distBetweenDots(double[] d1, double[] d2) {
        return Math.sqrt((d1[0] - d2[0]) * (d1[0] - d2[0]) + (d1[1] - d2[1]) * (d1[1] - d2[1]));

    }

}
