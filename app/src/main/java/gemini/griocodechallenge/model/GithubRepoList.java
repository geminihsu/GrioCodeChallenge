package gemini.griocodechallenge.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by geminihsu on 04/09/2017.
 */

public class GithubRepoList implements Serializable {
    private static final long serialVersionUID = -6446585793905589505L;
    private String name;
    private String description;
    private int stargazers_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

}

