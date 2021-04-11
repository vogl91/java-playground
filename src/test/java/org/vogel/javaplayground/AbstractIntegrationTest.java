package org.vogel.javaplayground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:truncate-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AbstractIntegrationTest {
    @Autowired
    public TestRestTemplate testRestTemplate;
}
