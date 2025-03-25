package cybercooker.scheduleservice.repository.interfaces;

import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface WeekRepository {
    Week getById(LocalDate startDate, int spaceId) throws NotFoundException;

    List<Week> getAllBySpaceId(int spaceId);

    void save(Week week) throws AlreadyExistsException;

    void update(Week week) throws NotFoundException, AlreadyExistsException;

    void delete(LocalDate startDate, int spaceId) throws NotFoundException;
}
