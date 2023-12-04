import org.junit.jupiter.api.Test;
import com.xiaoheizi.utils.FileUtils;
import com.xiaoheizi.utils.InputUtils;
import com.xiaoheizi.utils.PasswordUtils;

public class UtilTest {
    @Test
    public void testJarPath() {
        System.out.println("Jar path is: " + FileUtils.getJarPath());
    }

    @Test
    public void testInputUtils() {
        try {
            InputUtils.cleanInput("null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPasswordUtils() {
        assert (!PasswordUtils.isStrongPassword("12345678"));
    }
}
