package services;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import model.Branch;
import model.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.github.com")
public interface GitHubClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<Repository>> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<Branch>> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);
}
