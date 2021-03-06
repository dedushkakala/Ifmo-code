package Algorithm.LCA;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 29.10.2016.
 * Project : F
 * Start time : 20:56
 */

public class F {

    private int ln;
    private int min;
    private ArrayList<Integer> d;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> dp;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        ln = (int) (Math.log(n) / Math.log(2)) + 1;
        d = new ArrayList<>();
        d.add(0);
        dp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dp.add(i, new ArrayList<>());
        }
        for (int i = 0; i < ln; i++) {
            dp.get(0).add(i, new Pair<>(0, Integer.MAX_VALUE));
        }
        for (int i = 1; i < n; i++) {
            int from = nextInt() - 1;
            dp.get(i).add(0, new Pair<>(from, nextInt()));
            d.add(i, d.get(from) + 1);
        }
        Pair<Integer, Integer> cur, to;
        for (int j = 1; j < ln; j++) {
            for (int i = 1; i < n; i++) {
                cur = dp.get(i).get(j - 1);
                to = dp.get(cur.getKey()).get(j - 1);
                dp.get(i).add(j, new Pair<>(to.getKey(), Math.min(cur.getValue(), to.getValue())));
            }
        }

        int m = nextInt();
        for (int i = 0; i < m; i++) {
            min = Integer.MAX_VALUE;
            lca(nextInt() - 1, nextInt() - 1);
            out.println(min);
        }


    }

    private int lca(int v, int u) {
        if (d.get(v) > d.get(u)) {
            int temp = v;
            v = u;
            u = temp;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (d.get(u) - d.get(v) >= (1 << i)) {
                min = Math.min(min, dp.get(u).get(i).getValue());
                u = dp.get(u).get(i).getKey();

            }
        }
        if (v == u) return v;
        for (int i = ln - 1; i >= 0; i--) {
            if (!dp.get(v).get(i).equals(dp.get(u).get(i))) {
                min = Math.min(min, dp.get(v).get(i).getValue());
                min = Math.min(min, dp.get(u).get(i).getValue());
                v = dp.get(v).get(i).getKey();
                u = dp.get(u).get(i).getKey();
            }
        }
        return dp.get(v).get(0).getKey();
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("minonpath" + ".in"));
            out = new PrintWriter("minonpath" + ".out");
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

}