package cocooncreations.net.moviereviews.ui.movie.bookmark;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.App;
import cocooncreations.net.moviereviews.R;
import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.adapters.RealmMoviesAdapter;
import cocooncreations.net.moviereviews.ui.base.listener.OnItemClickListener;
import cocooncreations.net.moviereviews.ui.movie.detail.MovieDetailActivity;
import io.realm.RealmResults;


public class MovieBookmarkFragment extends Fragment implements MovieBookmarkMvpView, OnItemClickListener {

    private RecyclerView recyclerView;
    private RealmMoviesAdapter adapter;

    @Inject MovieBookmarkPresenter  presenter;

    public MovieBookmarkFragment() {
    }

    public static MovieBookmarkFragment newInstance() {
        MovieBookmarkFragment fragment = new MovieBookmarkFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getActivity().getApplication()).getAppComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_bookmark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new RealmMoviesAdapter(getActivity(), null);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        presenter.loadBookmarkedMovies();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Movie movie = adapter.getItem(position);
        if (movie != null) {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.ARG_MOVIE_TITLE, movie.getTitle());
            startActivity(intent);
        }
    }

    @Override
    public void showMovies(RealmResults<Movie> movies) {
        adapter.updateData(movies);
    }
}
