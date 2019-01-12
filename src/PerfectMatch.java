import java.io.*;

public class PerfectMatch {

    private static int match[]; //匹配关系
    private static boolean[] visited; //纵坐标是否访问
    private static int[][] ajacy; //邻接矩阵
    private static int standard; //权值的最大标准值
    private static int n; //点个数

    /**
     * 二分法查找确定权值
     * @param w
     * @return
     */
    private static int biSearch(int w) {
        int lowBound = 0;
        int highBound = w;
        int w0 = 0;
        while (lowBound <= highBound) {
            standard = (lowBound + highBound) / 2;
            if (perfectMatch()) {
                w0 = standard;
                highBound = standard - 1;
            } else
                lowBound = standard + 1;
        }
        return w0;
    }

    /**
     * 是否存在完美匹配
     * @return
     */
    private static boolean perfectMatch() {
        int countMatch = 0;
        match = new int[n];
        for (int i = 0; i < n; i++)
            match[i] = -1;
        for (int i = 0; i < n; i++) {
            visited = new boolean[n];
            if (augumentPath(i))
                countMatch++;
        }
        return countMatch == n;
    }

    /**
     * 是否存在增广路径
     * @param start 起始点
     * @return
     */
    private static boolean augumentPath(int start) {
        for (int v = 0; v < ajacy.length; v++) {
            if (ajacy[start][v] <= standard && !visited[v]) {
                visited[v] = true;
                if (match[v] == -1 || augumentPath(match[v])) {
                    match[v] = start;
                    return true;
                }
            }
        }
        return false;
    }

    private static void practice() {
        String dir = "resource/data/";
        String outDir = "resource/output/";
        String path = dir + "part.";
        String inFile = path + "in";
        String outFile = path + "out";
        File iFile = new File(inFile);
        File oFile = new File(outFile);
        String outputContent = "";
        int errorNum = 0;
        int t = 0;
        try {
            BufferedReader rw = new BufferedReader(new InputStreamReader(new FileInputStream(iFile)));
            BufferedReader of = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            String line = rw.readLine();
            t = Integer.parseInt(line);
            for (int i = 0; i < t; i++) {
                int k = Integer.parseInt(rw.readLine());
                int[][] biadjacency = new int[k][k];
                int w = Integer.MIN_VALUE;
                for (int j = 0; j < k; j++) {
                    String weightsLine = rw.readLine();
                    String[] weights = weightsLine.split(" ");
                    for (int m = 0; m < k; m++) {
                        w = Math.max(Integer.parseInt(weights[m]), w);
                        biadjacency[j][m] = Integer.parseInt(weights[m]);
                    }
                }
                ajacy = biadjacency;
                n = k;
                String result = String.valueOf(biSearch(w));
                String out = of.readLine();
                if (!result.equals(out)) {
                    System.out.println(i + ":" + " Exists error");
                    System.out.println("my:" + result);
                    System.out.println("st:" + out);
                    System.out.println();
                    errorNum++;
                } else
                    outputContent += result + "\n";
            }
            rw.close();
            of.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (errorNum != 0) {
            System.out.println("Error,Please improve.");
            System.out.println("Result:" + (t - errorNum) + "/" + t);
        } else {
            String outputFile = outDir + "part" + ".out";
            try {
                File file = new File(outputFile);
                FileWriter writer = new FileWriter(file, false);
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.write(outputContent);
                printWriter.println();
                writer.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("AC,great!");
        }
    }

    public static void main(String[] args) {
        practice();
    }
}
