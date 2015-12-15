package Coding4Fun;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class RedMart {

    public static class Data {
        private int length;
        private int drop;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getDrop() {
            return drop;
        }

        public void setDrop(int drop) {
            this.drop = drop;
        }

        @Override
        public String toString() {
            return "Data{" +
                "length=" + length +
                ", drop=" + drop +
                '}';
        }
    }

    Map<String, Data> memo = new HashMap<>();

    public Data calc(int[][] map) {
        int maxLength = 0;
        int maxDrop = 0;
        Data sol = null;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Data data = traverse(i, j, map);
                if (data.getLength() > maxLength) {
                    sol = data;
                    maxLength = data.length;
                } else if (data.getLength() == maxLength) {
                    if (data.getDrop() > maxDrop) {
                        sol = data;
                    }
                }
            }
        }
        return sol;
    }

    private Data traverse(int i, int j, int[][] map) {
        if (memo.containsKey(i + "#" + j)) {
            return memo.get(i + "#" + j);
        }
        Data data = new Data();
        int maxDrop = 0;
        int maxLength = 0;

        //go right
        if (j != map[i].length - 1 && map[i][j + 1] < map[i][j]) {
            Data temp = null;
            if (memo.containsKey(i + "#" + (j + 1))) {
                temp = memo.get(i + "#" + (j + 1));
            } else {
                temp = traverse(i, j + 1, map);
                memo.put(i + "#" + (j + 1), temp);
            }
            if (temp.length > maxLength) {
                maxLength = temp.getLength();
                maxDrop = temp.getDrop() + map[i][j] - map[i][j + 1];
            } else if (temp.length == maxLength) {
                int drop = temp.getDrop() + map[i][j] - map[i][j + 1];
                if(drop > maxDrop) maxDrop = drop;

            }
        }

        //go left
        if (j != 0 && map[i][j - 1] < map[i][j]) {
            Data temp = null;
            if (memo.containsKey(i + "#" + (j - 1))) {
                temp =  memo.get(i + "#" + (j - 1));
            } else {
                temp = traverse(i, j - 1, map);
                memo.put(i + "#" + (j - 1), temp);
            }
            if (temp.length > maxLength) {
                maxLength = temp.getLength();
                maxDrop = temp.getDrop() + map[i][j] - map[i][j - 1];
            } else if (temp.length == maxLength) {
                int drop = temp.getDrop() + map[i][j] - map[i][j - 1];
                if(drop > maxDrop) maxDrop = drop;
            }
        }

        //go up
        if (i != 0 && map[i - 1][j] < map[i][j]) {
            Data temp = null;
            if (memo.containsKey((i - 1) + "#" + j)) {
                temp =  memo.get((i - 1) + "#" + j);
            } else {
                temp = traverse((i - 1), j, map);
                memo.put((i - 1) + "#" + j, temp);
            }
            if (temp.length > maxLength) {
                maxLength = temp.getLength();
                maxDrop = temp.getDrop() + map[i][j] - map[i - 1][j];
            } else if (temp.length == maxLength) {
                int drop = temp.getDrop() + map[i][j] - map[i - 1][j];
                if(drop > maxDrop) maxDrop = drop;

            }
        }

        //go down
        if (i != map.length - 1 && map[i + 1][j] < map[i][j]) {
            Data temp = null;
            if (memo.containsKey((i + 1) + "#" + j)) {
                temp =  memo.get((i + 1) + "#" + j);
            } else {
                temp = traverse((i + 1), j, map);
                memo.put((i + 1) + "#" + j, temp);
            }
            if (temp.length > maxLength) {
                maxLength = temp.getLength();
                maxDrop = temp.getDrop() + map[i][j] - map[i + 1][j];
            } else if (temp.length == maxLength) {
                int drop = temp.getDrop() + map[i][j] - map[i + 1][j];
                if(drop > maxDrop) maxDrop = drop;

            }
        }
        data.setLength(maxLength + 1);
        data.setDrop(maxDrop);
        memo.put(i + "#" + j, data);
        return data;
    }

    public static void main(String args[]) {

        RedMart redMart = new RedMart();
        boolean firstLine = false;
        int[][] map = null;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get
            ("c:\\downloads\\map" +
                ".txt"))) {
            String line = null;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    String[] arr = line.split(" ");
                    map = new int[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])];
                    firstLine = true;
                } else {
                    String[] arr = line.split(" ");
                    int col = 0;
                    for (String v : arr) {
                        map[counter][col++] = Integer.parseInt(v);
                    }
                    counter++;
                }
            }
            Data data = redMart.calc(map);
            System.out.printf("length : %d , drop %d ", data.getLength(), data.getDrop());
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
