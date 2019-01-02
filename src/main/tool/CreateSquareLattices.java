import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class CreateSquareLattices {
    public static void main(String[] args) {
        //可调节参数，N表示方格网络的边长
        int N = 30;
        createSquareLattices(N,"E:\\SL_"+N+".txt");
    }
    
    /**
     * 生成方格网络的函数，生成txt，
     * @param N 方格长度
     * @param filePath
     */
    public static void createSquareLattices(int N, String filePath) {
        int E = 0;
        int i;
        int j;
        for(int k = 1; k <= N * N; k++){
            if(k % N == 0){
                j = N;
                i = k / N;
            }else{
                j = k % N;
                i = k / N + 1;
            }
            if(j != N){
                contentToTxt(filePath, k+"	"+(k+1)+"\r\n");
                E++;
            }
            if(i != N){
                contentToTxt(filePath, k+"	"+(k+N)+"\r\n");
                E++;
            }
        }
        System.out.println("生成方格图完毕，节点数"+N*N+"边数"+E);
    }
    
    /**
     * 追加txt内容函数
     * @param filePath
     * @param content
     */
    public static void contentToTxt(String filePath, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
