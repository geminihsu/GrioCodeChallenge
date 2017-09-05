package gemini.griocodechallenge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gemini.griocodechallenge.R;
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
                .inflate(R.layout.repo_activity_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GithubRepoList githubRepo = mItems.get(i);
        viewHolder.login.setText(githubRepo.getName()+" ,start : "+githubRepo.getStargazers_count());
        viewHolder.description.setText(githubRepo.getDescription());
        //viewHolder.startCount.setText(githubRepo.getStargazers_count());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView description;
        //public TextView startCount;
        //public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            description = (TextView) itemView.findViewById(R.id.description);
            //startCount = (TextView) itemView.findViewById(R.id.startCount);
        }
    }
}