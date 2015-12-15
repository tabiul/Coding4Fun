package Coding4Fun;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class RedMart {
    Map<String, List<List<Integer>>> memo = new HashMap<>();

    public List<Integer> calc(int[][] map) {
        int maxSize = 0;
        int maxDrop = 0;
        List<Integer> sol = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                List<List<Integer>> paths = traverse(i, j, map);
                //find the max path and highest drop
                for (List<Integer> p : paths) {
                    //System.out.println("path: " + p);
                    if (p.size() > maxSize) {
                        maxSize = p.size();
                        sol = p;
                    } else if (p.size() == maxSize) {
                        int diff = p.get(0) - p.get(p.size() - 1);
                        if (diff > maxDrop) {
                            maxDrop = diff;
                            sol = p;
                        }
                    }
                }
            }
        }

        return sol;
    }

    private List<List<Integer>> traverse(int i, int j, int[][] map) {
        List<List<Integer>> result = new ArrayList<>();
        if (memo.containsKey(i + "#" + j)) {
            return memo.get(i + "#" + j);
        }
        boolean hasPath = false;
        //go right
        if (j != map[i].length - 1 && map[i][j + 1] < map[i][j]) {
            hasPath = true;
            List<List<Integer>> paths = null;
            if (memo.containsKey(i + "#" + (j + 1))) {
                paths = memo.get(i + "#" + (j + 1));
            } else {
                paths = traverse(i, j + 1, map);
                memo.put(i + "#" + (j + 1), paths);
            }
            for (List<Integer> p : paths) {
                List<Integer> temp = new ArrayList<>(p);
                temp.add(0, map[i][j]);
                result.add(temp);
            }
        }
        //go left
        if (j != 0 && map[i][j - 1] < map[i][j]) {
            hasPath = true;
            List<List<Integer>> paths = null;
            if (memo.containsKey(i + "#" + (j - 1))) {
                paths = memo.get(i + "#" + (j - 1));
            } else {
                paths = traverse(i, j - 1, map);
                memo.put(i + "#" + (j - 1), paths);
            }
            for (List<Integer> p : paths) {
                List<Integer> temp = new ArrayList<>(p);
                temp.add(0, map[i][j]);
                result.add(temp);
            }
        }

        //go up
        if (i != 0 && map[i - 1][j] < map[i][j]) {
            hasPath = true;
            List<List<Integer>> paths = null;
            if (memo.containsKey((i - 1) + "#" + j)) {
                paths = memo.get((i - 1) + "#" + j);
            } else {
                paths = traverse(i - 1, j, map);
                memo.put((i - 1) + "#" + j, paths);
            }
            for (List<Integer> p : paths) {
                List<Integer> temp = new ArrayList<>(p);
                temp.add(0, map[i][j]);
                result.add(temp);
            }
        }
        //go down
        if (i != map.length - 1 && map[i + 1][j] < map[i][j]) {
            hasPath = true;
            List<List<Integer>> paths = null;
            if (memo.containsKey((i + 1) + "#" + j)) {
                paths = memo.get((i + 1) + "#" + j);
            } else {
                paths = traverse(i + 1, j, map);
                memo.put((i + 1) + "#" + j, paths);
            }
            for (List<Integer> p : paths) {
                List<Integer> temp = new ArrayList<>(p);
                temp.add(0, map[i][j]);
                result.add(temp);
            }
        }
        if (!hasPath) {
            List<Integer> p = new ArrayList<>();
            p.add(map[i][j]);
            result.add(p);
        }
        memo.put(i + "#" + j, result);
        return result;
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
            List<Integer> sol = redMart.calc(map);
            System.out.printf("length : %d , drop %d ", sol.size(), sol.get(0) - sol
                .get(sol.size() - 1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
