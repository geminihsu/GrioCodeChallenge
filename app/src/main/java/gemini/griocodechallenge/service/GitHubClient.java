package gemini.griocodechallenge.service;


import gemini.griocodechallenge.model.GithubRepoList;
import retrofit.http.GET;
import retrofit.http.Path;
import java.util.*;
import rx.Observable;

public interface GitHubClient {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{user}/repos")
    Observable<List<GithubRepoList>> reposForUser(
            @Path("user") String user
    );

}
