import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.*;

/**
 * @program: EvolutionaryGame
 * @Date: 2018/11/21 16:23
 * @Author: Mr.Wang
 * @Description:读取输入文件类
 */
public class GraphReader {
    
    /**
     * 读取输入文件
     *
     * @param f
     * @return graph
     * @throws FileNotFoundException
     */
    static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> readGraph(File f) throws FileNotFoundException {
        SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph
            = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String[] sizes = br.readLine().split("\\s+");
            int nodes = Integer.parseInt(sizes[0]);
            int edges = Integer.parseInt(sizes[1]);
            //System.out.println("准备读取图，节点数" + nodes + "边数" + edges);
            
            for (int i = 1; i <= nodes; i++) {
                graph.addVertex(new Integer(i));
            }
            
            for (int i = 1; i <= edges; i++) {
                String[] vertices = br.readLine().split("\\s+");
                Integer sourceV = Integer.parseInt(vertices[0]);
                Integer targetV = Integer.parseInt(vertices[1]);
                graph.addEdge(sourceV, targetV);
            }
            
            //System.out.println("成功读取" + graph.vertexSet().size() + "个定点和" + graph.edgeSet().size() + "条边");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return graph;
    }
}
