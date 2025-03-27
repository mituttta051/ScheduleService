package cybercooker.scheduleservice.controller;

import cybercooker.scheduleservice.entity.template.WeekTemplate;
import cybercooker.scheduleservice.request.WeekTemplateCreateRequest;
import cybercooker.scheduleservice.service.WeekTemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/week-template")
public class WeekTemplateController {
    @Autowired
    private WeekTemplateService weekTemplateService;

    @GetMapping("/get/{id}/{spaceId}")
    public ResponseEntity<WeekTemplate> getWeekTemplate(@PathVariable int id, @PathVariable int spaceId) {
        return ResponseEntity.ok(weekTemplateService.getById(id, spaceId));
    }

    @GetMapping("/get-all/{spaceId}")
    public ResponseEntity<List<WeekTemplate>> getAllWeekTemplates(@PathVariable int spaceId) {
        return ResponseEntity.ok(weekTemplateService.getAllBySpaceId(spaceId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createWeekTemplate(@Valid @RequestBody WeekTemplateCreateRequest request) {
        WeekTemplate template = WeekTemplate.builder()
                .spaceId(request.getSpaceId())
                .name(request.getName())
                .data(request.getData())
                .build();
        weekTemplateService.save(template);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateWeekTemplate(@Valid @RequestBody WeekTemplate template) {
        weekTemplateService.update(template);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}/{spaceId}")
    public ResponseEntity<Void> deleteWeekTemplate(@PathVariable int id, @PathVariable int spaceId) {
        weekTemplateService.delete(id, spaceId);
        return ResponseEntity.ok().build();
    }


}
