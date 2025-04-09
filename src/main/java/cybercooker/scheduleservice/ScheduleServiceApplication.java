package cybercooker.scheduleservice;

import cybercooker.scheduleservice.grpc.RecipeGrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class ScheduleServiceApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ScheduleServiceApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RecipeGrpcClient recipeGrpcClient;


    public static void main(String[] args) {
        SpringApplication.run(ScheduleServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executeSqlFromFile();
    }

    private void executeSqlFromFile() throws IOException {
        log.info("Creating tables");

        File file = ResourceUtils.getFile("classpath:db/init.sql");
        String sql = new String(Files.readAllBytes(file.toPath()));
        jdbcTemplate.execute(sql);

        log.info("Tables created");
    }
}
