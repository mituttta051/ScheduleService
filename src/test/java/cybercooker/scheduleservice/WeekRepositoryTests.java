package cybercooker.scheduleservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.repository.interfaces.WeekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = ScheduleServiceApplication.class)
public class WeekRepositoryTests extends RepositoryTests {
    @Autowired
    WeekRepository weekRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM week");
    }

    @Test
    void testSave() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        weekRepository.save(week);
        LocalDate localDate1 = LocalDate.of(2021, 1, 4);
        Week savedWeek = weekRepository.getById(localDate1, 1);
        assertThat(savedWeek).isEqualTo(week);

        Week week2 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week2.json"), Week.class);
        weekRepository.save(week2);
        LocalDate localDate2 = LocalDate.of(2021, 1, 11);
        Week savedWeek2 = weekRepository.getById(localDate2, 1);
        assertThat(savedWeek2).isEqualTo(week2);

        Week week3 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week3.json"), Week.class);
        weekRepository.save(week3);
        Week savedWeek3 = weekRepository.getById(localDate1, 2);
        assertThat(savedWeek3).isEqualTo(week3);

    }

    @Test
    void testUpdate() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        weekRepository.save(week);
        LocalDate localDate1 = LocalDate.of(2021, 1, 4);
        Week savedWeek = weekRepository.getById(localDate1, 1);
        assertThat(savedWeek).isEqualTo(week);

        week.getData().getDaySchedules().getFirst().setWeekDay(3);
        weekRepository.update(week);
        Week updatedWeek = weekRepository.getById(localDate1, 1);
        assertThat(updatedWeek).isEqualTo(week);
    }

    @Test
    void testDelete() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        weekRepository.save(week);
        LocalDate localDate1 = LocalDate.of(2021, 1, 4);
        Week savedWeek = weekRepository.getById(localDate1, 1);
        assertThat(savedWeek).isEqualTo(week);

        weekRepository.delete(localDate1, 1);
        assertThatThrownBy(() -> weekRepository.getById(localDate1, 1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testGetAllBySpaceId() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        weekRepository.save(week);
        LocalDate localDate1 = LocalDate.of(2021, 1, 4);
        Week savedWeek = weekRepository.getById(localDate1, 1);
        assertThat(savedWeek).isEqualTo(week);

        Week week2 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week2.json"), Week.class);
        weekRepository.save(week2);
        LocalDate localDate2 = LocalDate.of(2021, 1, 11);
        Week savedWeek2 = weekRepository.getById(localDate2, 1);
        assertThat(savedWeek2).isEqualTo(week2);

        Week week3 = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week3.json"), Week.class);
        weekRepository.save(week3);
        Week savedWeek3 = weekRepository.getById(localDate1, 2);
        assertThat(savedWeek3).isEqualTo(week3);

        assertThat(weekRepository.getAllBySpaceId(1)).containsExactlyInAnyOrder(week, week2);
        assertThat(weekRepository.getAllBySpaceId(2)).containsExactly(week3);
        assertThat(weekRepository.getAllBySpaceId(3)).isEmpty();
    }

    @Test
    void testGetByIdThatDoesNotExist() {
        assertThatThrownBy(() -> weekRepository.getById(LocalDate.of(2021, 1, 4), 1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testSaveThatAlreadyExists() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        weekRepository.save(week);
        LocalDate localDate1 = LocalDate.of(2021, 1, 4);
        Week savedWeek = weekRepository.getById(localDate1, 1);
        assertThat(savedWeek).isEqualTo(week);

        assertThatThrownBy(() -> weekRepository.save(week)).isInstanceOf(AlreadyExistsException.class);
    }

    @Test
    void deleteThatDoesNotExist() {
        assertThatThrownBy(() -> weekRepository.delete(LocalDate.of(2021, 1, 4), 1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateThatDoesNotExist() throws IOException {
        Week week = objectMapper.readValue(new File("src/test/java/cybercooker/scheduleservice/samples/week/week1.json"), Week.class);
        assertThatThrownBy(() -> weekRepository.update(week)).isInstanceOf(NotFoundException.class);
    }
}

