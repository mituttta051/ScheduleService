package cybercooker.scheduleservice.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeDTO {
    private int id;
    private String name;
}
