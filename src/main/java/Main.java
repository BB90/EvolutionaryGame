import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program: EvolutionaryGame
 * @Date: 2018/11/21 15:13
 * @Author: Mr.Wang
 * @Description:
 */
public class Main {
    //protected static Logger log = Logger.getLogger(Main.class.getName());
    private static Log log = LogFactory.getLog(Main.class.getName());
    /**
     * 存放图相关信息
     */
    static IntegerNodeGraph ingraph = new IntegerNodeGraph();
    /**
     * 更新顺序
     */
    static ArrayList<Integer> updateOrder;
    
    /**
     * 数据集地址
     */
    static String dataset = "F:\\kite.txt";
    /**
     * 迭代次数
     */
    static int iterationTimes = 10;
    /**
     * 初始化合作者比例
     */
    static double p = 0.5;
    /**
     * 背叛因子
     */
    static double b = 1.05;
    /**
     * 费米噪声
     */
    static double K = 0.1;
    
    
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info("开始实验================");
        File f = new File(dataset);
        //初始化图
        initIntegerNodeGraph(f);
        //迭代
        iteration(iterationTimes);
        //打印图信息
        showGraph();
        log.info("结束实验================");
    }
    
    /**
     * 对每个节点更新其策略
     * @param iterationTimes
     */
    private static void iteration(int iterationTimes) {
        log.info("准备开始"+iterationTimes+"次迭代");
        Collections.shuffle(updateOrder);
        for (int times = 0; times < iterationTimes; times++) {
            for (Integer nodeID : updateOrder) {
                updateStrategy(ingraph.getNode(nodeID));
            }
        }
        log.info("结束"+iterationTimes+"次迭代");
    }
    
    /**
     * 更新节点策略
     *
     * @param currentNode
     */
    private static void updateStrategy(IntegerNode currentNode) {
        if (currentNode.neighbor.size() == 0) {
            return;
        }
        IntegerNode targetNode =
            ingraph.getNode(currentNode.neighbor.get(new Random().nextInt(currentNode.neighbor.size())));
        if (!currentNode.strategy.equals(targetNode.strategy)) {
            double w = 1 / (1 + Math.pow(Math.E, (currentNode.payoff - targetNode.payoff) / K));
            if (new Random().nextFloat() < w) {
                currentNode.strategy = targetNode.strategy;
            }
        }
        updatePayoff(currentNode);
        for (Integer nodeID : currentNode.neighbor) {
            updatePayoff(ingraph.getNode(nodeID));
        }
    }
    
    /**
     * 初始化图
     *
     * @param f
     */
    public static void initIntegerNodeGraph(File f) {
        log.info("开始初始化");
        try {
            ingraph.graph = GraphReader.readGraph(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (ingraph.graph != null) {
            for (int nodeID = 1; nodeID <= ingraph.graph.vertexSet().size(); nodeID++) {
                ingraph.nodeMap.add(new IntegerNode(nodeID));
            }
        }
        
        init(ingraph);
        log.info("结束初始化，成功初始化"+ingraph.graph.vertexSet().size()+"个顶点和"+ingraph.graph.edgeSet().size()+"条边");
    }
    
    /**
     * 初始化图的邻居列表，策略和payoff
     *
     * @param ingraph
     */
    private static void init(IntegerNodeGraph ingraph) {
        
        //初始化邻居列表
        for (IntegerNode node1 : ingraph.nodeMap) {
            for (IntegerNode node2 : ingraph.nodeMap) {
                if (node1.id == node2.id) {
                    continue;
                } else if (ingraph.isEdge(node1.id, node2.id)) {
                    node1.neighbor.add(node2.id);
                }
            }
        }
        
        //初始化策略
        updateOrder = new ArrayList<Integer>();
        for (IntegerNode node : ingraph.nodeMap) {
            updateOrder.add(node.id);
        }
        Collections.shuffle(updateOrder);
        for (int index = 0; index < updateOrder.size(); index++) {
            if (index < updateOrder.size() * p) {
                ingraph.getNode(updateOrder.get(index)).strategy = "C";
            } else {
                ingraph.getNode(updateOrder.get(index)).strategy = "D";
            }
        }
        
        //初始化payoff
        for (IntegerNode node : ingraph.nodeMap) {
            updatePayoff(node);
        }
        
    }
    
    /**
     * 更新node的payoff
     *
     * @param node
     */
    public static void updatePayoff(IntegerNode node) {
        node.payoff = 0.0;
        for (Integer neighborNode : node.neighbor) {
            if ("C".equals(node.strategy) && "C".equals(ingraph.getNode(neighborNode))) {
                node.payoff += 1;
            } else if ("D".equals(node.strategy) && "D".equals(ingraph.getNode(neighborNode).strategy)) {
                node.payoff += b;
            }
        }
    }
    
    /**
     * 打印图的概况
     */
    public static void showGraph() {
        for (IntegerNode node : ingraph.nodeMap) {
            System.out.println(node.toString());
        }
    }
    
}
