import java.io.File;
import java.io.FileNotFoundException;

/**
 * @program: EvolutionaryGame
 * @Date: 2018/11/21 17:06
 * @Author: Mr.Wang
 * @Description:
 */
public class GraphReaderTest {
    
    @org.junit.Test
    public void testReadGraph() throws FileNotFoundException {
        GraphReader.readGraph(new File("F:\\Eclipse wokespace\\TreasureOf203\\kite.txt"));
    }
}