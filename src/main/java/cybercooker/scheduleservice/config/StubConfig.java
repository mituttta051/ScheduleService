package cybercooker.scheduleservice.config;

import cybercooker.recipeservice.grpc.recipe.RecipeServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {
    @Bean
    RecipeServiceGrpc.RecipeServiceBlockingStub stub(GrpcChannelFactory channels) {
        return RecipeServiceGrpc.newBlockingStub(channels.createChannel("local"));
    }
}
