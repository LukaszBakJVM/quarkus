package services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import model.Branch;
import model.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class GithubServices {
    @RestClient
    GitHubClient gitHubClient;

    public Uni<List<Repository>> getRepositories(String username, String type) {
        return gitHubClient.getRepositories(username, type);
    }

    public Uni<List<Branch>> getBranches(String owner, String repo) {
        return gitHubClient.getBranches(owner, repo);
    }

    public Multi<Repository>eee(String user){
        return   this.getRepositories(user, "owner")
                .onItem().transformToMulti(Unchecked.function(repos -> {
                    if (repos.isEmpty()) {
                        throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
                    }
                    return Multi.createFrom().iterable(repos)
                            .onItem().transformToUniAndConcatenate(repo ->
                                    this.getBranches(repo.owner().login(), repo.name())
                                            .onItem().invoke(repo::branches)
                                            .replaceWith(repo)
                            );
                }));
    }
}

