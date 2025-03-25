package cybercooker.scheduleservice.repository.interfaces;

import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;

import java.util.List;

public interface WeekTemplateRepository {
    WeekTemplate getById(int id, int spaceId) throws NotFoundException;

    List<WeekTemplate> getAllBySpaceId(int spaceId);

    void save(WeekTemplate weekTemplate) throws AlreadyExistsException;

    void update(WeekTemplate weekTemplate) throws NotFoundException, AlreadyExistsException;

    void delete(int id, int spaceId) throws NotFoundException;

}