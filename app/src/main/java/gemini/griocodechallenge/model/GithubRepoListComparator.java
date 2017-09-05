package gemini.griocodechallenge.model;

import java.util.Comparator;

/**
 * Created by geminihsu on 04/09/2017.
 */

public class GithubRepoListComparator implements Comparator<GithubRepoList> {
    public int compare(GithubRepoList chair1, GithubRepoList chair2) {
        return chair2.getStargazers_count() - chair1.getStargazers_count();
    }
}
