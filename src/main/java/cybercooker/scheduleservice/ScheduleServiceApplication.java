package cybercooker.scheduleservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.filter.ContainsTagFilter;
import cybercooker.scheduleservice.entity.filter.Filter;
import cybercooker.scheduleservice.repository.interfaces.WeekTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleServiceApplication {
    @Autowired private static WeekTemplateRepository weekTemplateRepository;

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(ScheduleServiceApplication.class, args);
    }

}
