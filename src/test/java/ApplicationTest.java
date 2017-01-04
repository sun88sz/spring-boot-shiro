import com.sun.Application;
import com.sun.springboot.service.BaseUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Sun on 16/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

	@Autowired
	private BaseUserService userService;

	@Test
	public void exampleTest() {

		userService.selectById();
	}

}
