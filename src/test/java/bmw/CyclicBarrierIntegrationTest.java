package bmw;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import bmw.model.Registration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
@DirtiesContext
public class CyclicBarrierIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${local.server.port}")
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Test
    public void testMultipleClientsCyclicBarrier() throws Exception {
        String url;
        url = "http://localhost:9000/register/fetchAll";
        //url = "http://localhost:" + this.port + "/register/fetchAll";

        int threads = 5;

        final CyclicBarrier barrier = new CyclicBarrier(threads);
        ExecutorService svc = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            svc.execute(() -> {
                try {
                    log("await()");
                    barrier.await();
                    log("working");
                    Thread.sleep((int) (Math.random() * 100));

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<List<Registration>> response =
                            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Registration>>() {
                            });
                    List<Registration> registrationList = response.getBody();
                    assertEquals(registrationList.size(), 4);
                    log("verified result");


                    log("Wait for end");
                    barrier.await();
                    log("Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(100);
        }
    }

    private static void log(String msg) {
        System.out.println(System.currentTimeMillis() + ": " + Thread.currentThread().getId() + " " + msg);
    }

}
