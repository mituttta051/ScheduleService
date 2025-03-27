package cybercooker.scheduleservice.controller;

import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.service.WeekService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/week")
public class WeekController {
    @Autowired
    private WeekService weekService;

    @GetMapping("/get/{startDate}/{spaceId}")
    public ResponseEntity<Week> getWeek(@PathVariable LocalDate startDate, @PathVariable int spaceId) {
        return ResponseEntity.ok(weekService.getById(startDate, spaceId));
    }

    @GetMapping("/get-all/{spaceId}")
    public ResponseEntity<List<Week>> getAllWeeks(@PathVariable int spaceId) {
        return ResponseEntity.ok(weekService.getAllBySpaceId(spaceId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createWeek(@Valid @RequestBody Week week) {
        weekService.save(week);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateWeek(@Valid @RequestBody Week week) {
        weekService.update(week);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{startDate}/{spaceId}")
    public ResponseEntity<Void> deleteWeek(@PathVariable LocalDate startDate, @PathVariable int spaceId) {
        weekService.delete(startDate, spaceId);
        return ResponseEntity.ok().build();
    }
}
