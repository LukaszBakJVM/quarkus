package controller;


import dto.RepositoryDto;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import services.GithubServices;


@Path("/repositories")
public class GithubController {

   private final GithubServices services;
    @Inject
    public GithubController(GithubServices services) {
        this.services = services;
    }


    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<RepositoryDto> getRepositories(@PathParam("username") String username) {
        return services.getUserRepositories(username);
    }
}
