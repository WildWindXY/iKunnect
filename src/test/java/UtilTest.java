import utils.FileUtils;

public class UtilTest {
    @org.junit.Test
    public void testJarPath() {
        System.out.println("Jar path is: " + FileUtils.getJarPath());
    }
}
