import lombok.*;

import java.util.ArrayList;

/**
 * @program: EvolutionaryGame
 * @Date: 2018/11/21 15:38
 * @Author: Mr.Wang
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IntegerNode {
    int id;
    String strategy;
    double payoff;
    ArrayList<Integer> neighbor;
    
    
    public IntegerNode(int id) {
        this.id = id;
        this.strategy = "";
        this.payoff = 0.0;
        this.neighbor = new ArrayList<Integer>();
    }
}
