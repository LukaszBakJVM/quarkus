package services;

import dto.Mapper;
import dto.RepositoryDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import model.Branch;
import model.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class GithubServices {
    private final Mapper mapper;
    @RestClient
    GitHubClient gitHubClient;

    @Inject
    public GithubServices(Mapper mapper) {
        this.mapper = mapper;
    }


    public Multi<RepositoryDto> getUserRepositories(String user) {
        return gitHubClient.getRepositories(user).onItem().transformToMulti(Unchecked.function(repos -> {
            if (repos.isEmpty()) {
                throw new WebApplicationException();
            }
            return Multi.createFrom().iterable(repos).filter(repository -> !repository.fork()).onItem().transformToUniAndMerge(repository -> this.getBranchesForRepository(user, repository.name()).map(branches -> mapper.fromRepositoryToDto(new Repository(repository.name(), repository.owner(), repository.fork(), branches))));

        }));
    }

    private Uni<List<Branch>> getBranchesForRepository(String username, String repositoryName) {
        return gitHubClient.getBranches(username, repositoryName);
    }
}

