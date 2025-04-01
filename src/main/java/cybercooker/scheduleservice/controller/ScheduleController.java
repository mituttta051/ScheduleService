package cybercooker.scheduleservice.controller;

import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/generate")
    public ResponseEntity<Week> generateWeek(@Valid @RequestBody Week incompleteWeek) {
        return ResponseEntity.ok(scheduleService.generateSchedule(incompleteWeek));
    }

}
