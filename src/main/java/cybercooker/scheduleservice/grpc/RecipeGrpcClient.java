package cybercooker.scheduleservice.grpc;

import cybercooker.recipeservice.grpc.recipe.GetRecipesByFilterGrpc;
import cybercooker.recipeservice.grpc.recipe.RecipeGrpc;
import cybercooker.recipeservice.grpc.recipe.RecipeServiceGrpc;
import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.entity.filter.Filter;
import cybercooker.scheduleservice.mapper.FilterMapper;
import cybercooker.scheduleservice.mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeGrpcClient implements RecipeGrpcClientInterface {
    @Autowired
    private RecipeServiceGrpc.RecipeServiceBlockingStub recipeServiceStub;

    @Override
    public List<RecipeDTO> getRecipesByFilter(Filter filter, int spaceId) {
        GetRecipesByFilterGrpc request = FilterMapper.toGetRecipesByFilterGrpc(filter, spaceId);

        List<RecipeGrpc> responses = recipeServiceStub.getRecipesByFilter(request).getRecipesList();
        return responses.stream()
                .map(RecipeMapper::toRecipeDTO)
                .toList();

    }

}
