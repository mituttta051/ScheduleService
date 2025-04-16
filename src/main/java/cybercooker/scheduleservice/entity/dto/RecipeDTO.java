package cybercooker.scheduleservice.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeDTO {
    private int id;
    private int spaceId;
    private String name;
    private String description;
    private List<Integer> ingredients;
    private int servingsNumber;
    private int cookTime;
    private int shelfLife;
    private List<TagDTO> tags;

    @Builder
    @Getter
    public static class TagDTO {
        private Integer id;
        private List<Integer> values;
    }
}
