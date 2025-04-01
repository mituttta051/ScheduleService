package cybercooker.scheduleservice.mapper;

import cybercooker.recipeservice.grpc.recipe.RecipeGrpc;
import cybercooker.scheduleservice.entity.dto.RecipeDTO;

public class RecipeMapper {
    public static RecipeDTO toRecipeDTO(RecipeGrpc recipeGrpc) {
        return RecipeDTO.builder()
                .id(recipeGrpc.getId())
                .name(recipeGrpc.getName())
                .build();
    }

}
