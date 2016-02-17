package bmw;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bmw.service.FileReaderServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class FileReaderServiceTest {

    @Value("${file}")
    private String file;

    private FileReaderServiceImpl registrationDao;

    @Before
    public void before() {
        registrationDao = new FileReaderServiceImpl(file);
    }

    @Test
    public void testInitial() {
        assertTrue(registrationDao.fetchAll().size() == 4);
    }

    @Test
    public void testReload() {
        registrationDao.reload();
        assertTrue(registrationDao.fetchAll().size() == 4);
    }
}
