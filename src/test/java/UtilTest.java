import org.junit.jupiter.api.Test;
import utils.FileUtils;

public class UtilTest {
    @Test
    public void testJarPath() {
        System.out.println("Jar path is: " + FileUtils.getJarPath());
    }
}
