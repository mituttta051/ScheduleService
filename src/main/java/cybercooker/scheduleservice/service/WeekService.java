package cybercooker.scheduleservice.service;

import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.repository.interfaces.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeekService {
    @Autowired
    private WeekRepository weekRepository;

    public Week getById(LocalDate startDate, int spaceId) throws NotFoundException {
        return weekRepository.getById(startDate, spaceId);
    }

    public List<Week> getAllBySpaceId(int spaceId) {
        return weekRepository.getAllBySpaceId(spaceId);
    }

    public void save(Week week) throws AlreadyExistsException {
        weekRepository.save(week);
    }

    public void update(Week week) throws NotFoundException, AlreadyExistsException {
        weekRepository.update(week);
    }

    public void delete(LocalDate startDate, int spaceId) throws NotFoundException {
        weekRepository.delete(startDate, spaceId);
    }
}
