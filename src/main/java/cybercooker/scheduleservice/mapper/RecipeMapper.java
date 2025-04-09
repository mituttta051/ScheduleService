package cybercooker.scheduleservice.mapper;

import cybercooker.recipeservice.grpc.recipe.RecipeGrpc;
import cybercooker.recipeservice.grpc.recipe.TagGrpc;
import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.request.generate.Recipe;

public class RecipeMapper {
    public static RecipeDTO toRecipeDTO(RecipeGrpc recipeGrpc) {
        return RecipeDTO.builder()
                .id(recipeGrpc.getId())
                .spaceId(recipeGrpc.getSpaceId())
                .name(recipeGrpc.getName())
                .description(recipeGrpc.getDescription())
                .cookTime(recipeGrpc.getCookTime())
                .servingsNumber(recipeGrpc.getServingsNumber())
                .ingredients(recipeGrpc.getIngredientsList())
                .tags(recipeGrpc.getTagsList().stream().map(RecipeMapper::toTagDTO).toList())
                .build();
    }

    public static RecipeDTO.TagDTO toTagDTO(TagGrpc tagGrpc) {
        return RecipeDTO.TagDTO.builder()
                .id(tagGrpc.getId())
                .values(tagGrpc.getValuesList())
                .build();
    }

    public static Recipe toRecipe(RecipeDTO recipeDTO) {
        return Recipe.builder()
                .id(recipeDTO.getId())
                .name(recipeDTO.getName())
                .build();
    }

}
