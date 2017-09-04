package gemini.griocodechallenge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gemini.griocodechallenge.R;
import gemini.griocodechallenge.model.Github;
import gemini.griocodechallenge.model.GithubRepoList;


public class GitHubRepoAdapter extends RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder> {
    List<GithubRepoList> mItems;

    public GitHubRepoAdapter() {
        super();
        mItems = new ArrayList<GithubRepoList>();
    }

    public void addData(GithubRepoList githubRepoList) {
        mItems.add(githubRepoList);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GithubRepoList githubRepo = mItems.get(i);
        viewHolder.login.setText(githubRepo.getName());
        viewHolder.start.setText("repos: " + githubRepo.getStargazers_count());
        //viewHolder.blog.setText("blog: " + github.getBlog());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView start;
        //public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            start = (TextView) itemView.findViewById(R.id.repos);
            //blog = (TextView) itemView.findViewById(R.id.blog);
        }
    }
}