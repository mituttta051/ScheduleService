package cybercooker.scheduleservice;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@SpringBootTest(classes = ScheduleServiceApplication.class)
public abstract class RepositoryTests {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void init(@Autowired JdbcTemplate jdbcTemplate) throws IOException {
        jdbcTemplate.execute("DROP DATABASE IF EXISTS \"schedule-db-test\"");
        jdbcTemplate.execute("CREATE DATABASE \"schedule-db-test\"");

        DataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/schedule-db-test", "admin", "secret");
        jdbcTemplate.setDataSource(dataSource);

        File file = ResourceUtils.getFile("classpath:db/init.sql");
        String sql = new String(Files.readAllBytes(file.toPath()));
        jdbcTemplate.execute(sql);
    }
}
