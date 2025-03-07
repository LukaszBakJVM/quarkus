package services;

import dto.Mapper;
import dto.RepositoryDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
    @Inject
    Mapper mapper;

   public Uni<List<Repository>> getRepositories(String username) {
        return gitHubClient.getRepositories(username);
    }

    public Uni<List<Branch>> getBranches(String owner, String repo) {
        return gitHubClient.getBranches(owner, repo);
    }

    public Multi<Uni<RepositoryDto>> eee(String user){
        return   this.getRepositories(user)
                .onItem().transformToMulti(Unchecked.function(repos -> {
                    if (repos.isEmpty()) {
                        throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
                    }
                   // Multi.createFrom().
                    return Multi.createFrom().iterable(repos).filter(repository -> !repository.fork())
                            .map(repository -> this.getBranchesForRepository(user,repository.name())
                            .map(branches->mapper.fromRepositoryToDto(new Repository(repository.name(), repository.owner(), repository.fork(), branches))));

                }));
    }

    private Uni<List<Branch>> getBranchesForRepository(String username, String repositoryName) {
       return gitHubClient.getBranches(username, repositoryName).map(p->p.stream().toList());
    }
}

