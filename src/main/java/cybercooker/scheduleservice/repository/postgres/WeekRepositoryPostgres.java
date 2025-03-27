package cybercooker.scheduleservice.repository.postgres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.week.Schedule;
import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.exception.details.DatabaseDetails;
import cybercooker.scheduleservice.repository.interfaces.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class WeekRepositoryPostgres implements WeekRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public Week getById(LocalDate startDate, int spaceId) throws NotFoundException {
        String sql = "SELECT * FROM week WHERE start_date = ? AND space_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), startDate, spaceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(new DatabaseDetails("Week with start date " + startDate + " not found in space " + spaceId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Week> getAllBySpaceId(int spaceId) {
        String sql = "SELECT * FROM week WHERE space_id = ?";
        return jdbcTemplate.query(sql, rowMapper(), spaceId);
    }

    @Override
    public void save(Week week) throws AlreadyExistsException {
        String sql = "INSERT INTO week (space_id, start_date, data) VALUES (?, ?, ?::jsonb)";
        try {
            jdbcTemplate.update(sql, week.getSpaceId(), week.getStartDate(), objectMapper.writeValueAsString(week.getData()));
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(new DatabaseDetails("Week with start date " + week.getStartDate() + " already exists in space " + week.getSpaceId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Week week) throws NotFoundException, AlreadyExistsException {
        String sql = "UPDATE week SET data = ?::jsonb WHERE space_id = ? AND start_date = ?";
        try {
            int numOfRows = jdbcTemplate.update(sql, objectMapper.writeValueAsString(week.getData()), week.getSpaceId(), week.getStartDate());
            if (numOfRows == 0) {
                throw new NotFoundException(new DatabaseDetails("Week with start date " + week.getStartDate() + " not found in space " + week.getSpaceId()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(new DatabaseDetails("Week with start date " + week.getStartDate() + " already exists in space " + week.getSpaceId()));
        }

    }

    @Override
    public void delete(LocalDate startDate, int spaceId) throws NotFoundException {
        String sql = "DELETE FROM week WHERE start_date = ? AND space_id = ?";
        int numOfRows = jdbcTemplate.update(sql, startDate, spaceId);
        if (numOfRows == 0) {
            throw new NotFoundException(new DatabaseDetails("Week with start date " + startDate + " not found in space " + spaceId));
        }

    }

    private RowMapper<Week> rowMapper() {
        return (rs, rowNum) -> {
            try {
                return Week.builder()
                        .startDate(rs.getDate("start_date").toLocalDate())
                        .spaceId(rs.getInt("space_id"))
                        .data(objectMapper.readValue(rs.getString("data"), Schedule.class))
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
