package cybercooker.scheduleservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.repository.interfaces.WeekTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = ScheduleServiceApplication.class)
public class WeekTemplateRepositoryTests extends RepositoryTests {
    @Autowired
    WeekTemplateRepository weekTemplateRepository;
    
    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM week_template");
        jdbcTemplate.execute("DELETE FROM last_week_template_id");
    }
    
    @Test
    void testSave() throws IOException {
        WeekTemplate weekTemplate = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate1.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate);
        WeekTemplate savedWeekTemplate = weekTemplateRepository.getById(1, 1);
        assertThat(savedWeekTemplate).isEqualTo(weekTemplate);

        WeekTemplate weekTemplate2 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate2.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate2);
        WeekTemplate savedWeekTemplate2 = weekTemplateRepository.getById(2, 1);
        assertThat(savedWeekTemplate2).isEqualTo(weekTemplate2);

        WeekTemplate weekTemplate3 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate3.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate3);
        WeekTemplate savedWeekTemplate3 = weekTemplateRepository.getById(1, 2);
        assertThat(savedWeekTemplate3).isEqualTo(weekTemplate3);
    }
    
    @Test
    void testUpdate() throws IOException {
        WeekTemplate weekTemplate = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate1.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate);
        WeekTemplate savedWeekTemplate = weekTemplateRepository.getById(1, 1);
        assertThat(savedWeekTemplate).isEqualTo(weekTemplate);
        
        weekTemplate.setName("New name");
        weekTemplateRepository.update(weekTemplate);
        WeekTemplate updatedWeekTemplate = weekTemplateRepository.getById(1, 1);
        assertThat(updatedWeekTemplate).isEqualTo(weekTemplate);
    }
    
    @Test
    void testDelete() throws IOException {
        WeekTemplate weekTemplate = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate1.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate);
        WeekTemplate savedWeekTemplate = weekTemplateRepository.getById(1, 1);
        assertThat(savedWeekTemplate).isEqualTo(weekTemplate);
        
        weekTemplateRepository.delete(1, 1);
        assertThatThrownBy(() -> weekTemplateRepository.getById(1, 1)).isInstanceOf(NotFoundException.class);
    }
    
    @Test
    void testGetAllBySpaceId() throws IOException {
        WeekTemplate weekTemplate = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate1.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate);
        WeekTemplate savedWeekTemplate = weekTemplateRepository.getById(1, 1);
        assertThat(savedWeekTemplate).isEqualTo(weekTemplate);

        WeekTemplate weekTemplate2 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate2.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate2);
        WeekTemplate savedWeekTemplate2 = weekTemplateRepository.getById(2, 1);
        assertThat(savedWeekTemplate2).isEqualTo(weekTemplate2);

        WeekTemplate weekTemplate3 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate3.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate3);
        WeekTemplate savedWeekTemplate3 = weekTemplateRepository.getById(1, 2);
        assertThat(savedWeekTemplate3).isEqualTo(weekTemplate3);
        
        assertThat(weekTemplateRepository.getAllBySpaceId(1)).containsExactlyInAnyOrder(weekTemplate, weekTemplate2);
        assertThat(weekTemplateRepository.getAllBySpaceId(2)).containsExactly(weekTemplate3);
        assertThat(weekTemplateRepository.getAllBySpaceId(3)).isEmpty();
    }
    
    @Test
    void testGetByIdThatDoesNotExist() {
        assertThatThrownBy(() -> weekTemplateRepository.getById(1, 1)).isInstanceOf(NotFoundException.class);
    }
    
    @Test
    void testSaveThatAlreadyExists() throws IOException {
        WeekTemplate weekTemplate3 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/weekTemplate/weekTemplate3.json"), WeekTemplate.class);
        weekTemplateRepository.save(weekTemplate3);
        WeekTemplate savedWeekTemplate3 = weekTemplateRepository.getById(1, 2);
        assertThat(savedWeekTemplate3).isEqualTo(weekTemplate3);
        
        assertThatThrownBy(() -> weekTemplateRepository.save(weekTemplate3)).isInstanceOf(AlreadyExistsException.class);
    }
    
    @Test
    void deleteThatDoesNotExist() {
        assertThatThrownBy(() -> weekTemplateRepository.delete(1, 1)).isInstanceOf(NotFoundException.class);
    }
    
    @Test
    void updateThatDoesNotExist() {
        assertThatThrownBy(() -> weekTemplateRepository.update(WeekTemplate.builder().id(1).spaceId(1).name("Name").build())).isInstanceOf(NotFoundException.class);
    }
    
}
