import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;

/**
 * @program: EvolutionaryGame
 * @Date: 2018/11/21 15:52
 * @Author: Mr.Wang
 * @Description:
 */
@Getter
@Setter
@ToString
public class IntegerNodeGraph {
    SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    ArrayList<IntegerNode> nodeMap = new ArrayList<IntegerNode>();
    
    /**
     * 返回id为i的节点
     *
     * @param id
     * @return IntegerNode
     */
    public IntegerNode getNode(int id) {
        for (IntegerNode node : nodeMap) {
            if (node.id == id)
                return node;
        }
        return null;
    }
    
    /**
     * 判断两个节点是不是有连边
     *
     * @param nodeID1
     * @param nodeID2
     * @return
     */
    public boolean isEdge(int nodeID1, int nodeID2) {
        return graph.containsEdge(nodeID1, nodeID2) || graph.containsEdge(nodeID2, nodeID1);
    }
}