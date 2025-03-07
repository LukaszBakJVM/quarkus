package controller;


import dto.RepositoryDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Repository;
import services.GithubServices;


@Path("/repositories")
public class GithubController {
    @Inject
    GithubServices services;


    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Uni<RepositoryDto>>getRepositories(@PathParam("username") String username) {
        return services.eee(username);
    }
}
