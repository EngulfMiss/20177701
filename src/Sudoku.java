import java.io.*;


/**
 * @author Engulf丶Missing
 */
public class Sudoku {
    public static final int NUM_9 = 9;
    public static final int NUM_2 = 2;
    public static final int NUM_3 = 3;
    public static final int NUM_4 = 4;
    public static final int NUM_6 = 6;
    public static final int NUM_8 = 8;
    static int len;
    static int[][] maze;
    public static String inputFilename;
    public static String outputFilename;
    public static int m;
    public static int n;

    /**
     *
     * @param maze 需要遍历的数独数组
     * @param i 当前位置的行
     * @param j 当前位置的列
     * @param x 带宫数独是如何切分的（切成多少行）
     * @param y 带宫数独是如何切分的（切分成多少列）
     * @param inNum 想填入的数字
     * @return 全部填完返回true，填的数字超过最大可取数返回false
     */
    static boolean insertNum(int[][] maze, int i, int j, int x, int y, int inNum) {

        while (maze[i][j] != 0) {
            if (j < len - 1) {
                j++;
            }
            else if (i < len - 1) {
                i++;
                j = 0;
            } else {

                return true;
            }
        }

        for (int k = 1; k <= inNum; k++) {
            maze[i][j] = k;
            if (len != 3){
                if (checkNum(maze, i, j, x, y)) {
                    if (!insertNum(maze, i, j, x, y, inNum)) {
                        continue;
                    }
                    return true;
                }
            }else{
                if (cheackNum3(maze, i, j)) {
                    if (!insertNum(maze, i, j, -1, -1, NUM_9)) {
                        continue;
                    }
                    return true;
                }
            }
        }
        maze[i][j] = 0;
        return false;
    }

    /**
     *
     * @param maze 需要填数的数独数组
     * @param i 当前位置的行
     * @param j 当前位置的列
     * @param x 带宫数独是如何切分的（切成多少行）
     * @param y 带宫数独是如何切分的（切分成多少列）
     * @return 行，列，（宫）都满足要求返回true，有一个不满足就返回false
     */
    static boolean checkNum(int[][] maze,int i,int j,int x,int y) {
        int num = maze[i][j];
        for (int k = 0; k < len; k++) {
            if (k == j) {
                continue;
            } else if (maze[i][k] == num) {
                return false;
            }
        }

        for (int k = 0; k < len; k++) {
            if (k == i) {
                continue;
            } else if (maze[k][j] == num) {
                return false;
            }
        }

        if((x != 0)&&(y != 0)){
            int bi = (i / (len / x)) * (len / x);
            int bj = (j / (len / y)) * (len / y);
            for (int k = bi; k < bi + (len / x); k++) {
                for (int l = bj; l < bj + (len / y); l++) {
                    if ((k == i) && (l == j)) {
                        continue;
                    }
                    if (maze[k][l] == num) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     *
     * @param maze 需要填数的数独数组
     * @param i 当前位置的行
     * @param j 当前位置的列
     * @return 单独对3宫进行检查，行，列，9个格子没1-9重复返回true
     */
    static boolean cheackNum3(int[][] maze,int i,int j)
    {
        int normal = 3;
        int num = maze[i][j];
        for(int ii = 0; ii < normal; ii++)
        {
            for(int jj = 0; jj < normal; jj++)
            {
                if(ii == i && jj == j){
                    continue;
                }else if(num == maze[ii][jj]){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     *
     * @param maze 要打印输出的数组
     */
    static void print(int[][] maze) {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     *
     * @param file 读取文件（数独所在文件位置）
     */
    static void txtString(FileReader file){
        BufferedReader br = new BufferedReader(file);

        try {


            String line = " ";
            String []sp = null;
            String [][]c = new String[m][m];

            int count=0;
            while((line=br.readLine())!=null) {
                sp = line.split(" ");
                for(int i=0;i<sp.length;i++){
                    c[count][i] = sp[i];
                }
                count++;
            }
            for(int i=0;i<m;i++){
                for(int j=0;j<m;j++){
                    maze[i][j] = Integer.parseInt(c[i][j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param str 接收命令行传来的命令，并按要求提取信息
     */
    public static void loadArgs(String[] str){
        if(str.length>0&&str!=null){
            for(int i=0;i<str.length;i++){
                switch (str[i]) {
                    case "-i":
                        inputFilename = str[++i];
                        break;
                    case "-o":
                        outputFilename = str[++i];
                        break;
                    case "-m":
                        m=Integer.valueOf(str[++i]);
                        break;
                    case "-n":
                        n=Integer.valueOf(str[++i]);
                        break;

                    default:
                        break;
                }
            }
        }
    }




    public static void main(String[] args) throws IOException {

        loadArgs(args);
        len = m;
        maze = new int[len][len];
        File file = new File("D:\\" + outputFilename);

        File file3 = new File("D:\\" + inputFilename);


        if(!file.exists()){
            file.createNewFile();
        }
        if(!(file3.exists())){
            file3.createNewFile();
        }
        FileReader file2 = new FileReader("D:\\" + inputFilename);
        txtString(file2);



        if((len % NUM_2 != 0) && (len != NUM_9) && (len != NUM_3)) {
            insertNum(maze, 0, 0, 0, 0, len);
        }else if(len == NUM_4){
            insertNum(maze, 0, 0, 2, 2, len);
        }else if(len == NUM_6){
            insertNum(maze, 0, 0, 3, 2, len);
        }else if(len == NUM_8){
            insertNum(maze, 0, 0, 2, 4, len);
        }else if(len == NUM_9){
            insertNum(maze, 0, 0, 3, 3, len);
        }else if(len == NUM_3){
            insertNum(maze, 0, 0, -1 , -1, NUM_9);
        }else{
            System.out.println("输入有问题");
        }


        FileOutputStream fos = new FileOutputStream(file);
        PrintStream printStream = new PrintStream(fos);
        System.setOut(printStream);
        print(maze);
        fos.close();
        printStream.close();
    }


}



