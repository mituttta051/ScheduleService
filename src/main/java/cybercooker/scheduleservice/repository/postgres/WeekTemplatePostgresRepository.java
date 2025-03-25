package cybercooker.scheduleservice.repository.postgres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.template.TemplateSchedule;
import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.exception.details.DatabaseDetails;
import cybercooker.scheduleservice.repository.interfaces.WeekTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeekTemplatePostgresRepository implements WeekTemplateRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public WeekTemplate getById(int id, int spaceId) throws NotFoundException {
        String sql = "SELECT * FROM week_template WHERE id = ? AND space_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id, spaceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(new DatabaseDetails("Week template with id " + id + " not found in space " + spaceId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeekTemplate> getAllBySpaceId(int spaceId) {
        String sql = "SELECT * FROM week_template WHERE space_id = ?";
        return jdbcTemplate.query(sql, rowMapper(), spaceId);
    }

    @Override
    public void save(WeekTemplate weekTemplate) throws AlreadyExistsException {
        String sql = "INSERT INTO week_template (space_id, name, data) VALUES (?, ?, ?::jsonb)";
        try {
            jdbcTemplate.update(sql, weekTemplate.getSpaceId(), weekTemplate.getName(), objectMapper.writeValueAsString(weekTemplate.getData()));
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(new DatabaseDetails("Week template with name " + weekTemplate.getName() + " already exists in space " + weekTemplate.getSpaceId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(WeekTemplate weekTemplate) throws NotFoundException, AlreadyExistsException {
        String sql = "UPDATE week_template SET name = ?, data = ?::jsonb WHERE id = ? AND space_id = ?";
        try {
            int numOfRows = jdbcTemplate.update(sql, weekTemplate.getName(), objectMapper.writeValueAsString(weekTemplate.getData()), weekTemplate.getId(), weekTemplate.getSpaceId());
            if (numOfRows == 0) {
                throw new NotFoundException(new DatabaseDetails("Week template with id " + weekTemplate.getId() + " not found in space " + weekTemplate.getSpaceId()));
            }
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(new DatabaseDetails("Week template with id " + weekTemplate.getId() + " already exists in space " + weekTemplate.getSpaceId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id, int spaceId) throws NotFoundException {
        String sql = "DELETE FROM week_template WHERE id = ? AND space_id = ?";
        int numOfRows = jdbcTemplate.update(sql, id, spaceId);
        if (numOfRows == 0) {
            throw new NotFoundException(new DatabaseDetails("Week template with id " + id + " not found in space " + spaceId));
        }

    }

    private RowMapper<WeekTemplate> rowMapper() {
        return (rs, rowNum) -> {
            try {
                return WeekTemplate.builder()
                        .id(rs.getInt("id"))
                        .spaceId(rs.getInt("space_id"))
                        .name(rs.getString("name"))
                        .data(objectMapper.readValue(rs.getString("data"), TemplateSchedule.class))
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
