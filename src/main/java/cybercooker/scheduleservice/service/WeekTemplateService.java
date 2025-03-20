package cybercooker.scheduleservice.service;

import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.repository.interfaces.WeekTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekTemplateService {
    @Autowired
    private WeekTemplateRepository weekTemplateRepository;

    public WeekTemplate getById(int id, int spaceId) throws NotFoundException {
        return weekTemplateRepository.getById(id, spaceId);
    }

    public List<WeekTemplate> getAllBySpaceId(int spaceId) {
        return weekTemplateRepository.getAllBySpaceId(spaceId);
    }

    public void save(WeekTemplate weekTemplate) throws AlreadyExistsException {
        weekTemplateRepository.save(weekTemplate);
    }

    public void update(WeekTemplate weekTemplate) throws NotFoundException, AlreadyExistsException {
        weekTemplateRepository.update(weekTemplate);
    }

    public void delete(int id, int spaceId) throws NotFoundException {
        weekTemplateRepository.delete(id, spaceId);
    }
}
