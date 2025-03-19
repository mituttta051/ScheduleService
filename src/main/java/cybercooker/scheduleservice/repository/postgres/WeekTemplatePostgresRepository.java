package cybercooker.scheduleservice.repository.postgres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.entity.template.TemplateSchedule;
import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.repository.interfaces.WeekTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeekTemplatePostgresRepository implements WeekTemplateRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public WeekTemplate getById(int id, int spaceId) throws NotFoundException {
        String sql = "SELECT * FROM week_template WHERE id = ? AND space_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id, spaceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Week template with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeekTemplate> getAllBySpaceId(int spaceId) {
        return List.of();
    }

    @Override
    public void save(WeekTemplate weekTemplate) throws AlreadyExistsException {

    }

    @Override
    public void update(WeekTemplate weekTemplate) throws NotFoundException, AlreadyExistsException {

    }

    @Override
    public void delete(int id, int spaceId) throws NotFoundException {

    }

    private RowMapper<WeekTemplate> rowMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
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
