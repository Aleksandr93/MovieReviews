package cocooncreations.net.moviereviews.ui.movie.search;


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
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.App;
import cocooncreations.net.moviereviews.R;
import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.adapters.RealmMoviesAdapter;
import cocooncreations.net.moviereviews.ui.base.listener.OnItemClickListener;
import cocooncreations.net.moviereviews.ui.movie.detail.MovieDetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmResults;


public class MovieSearchFragment extends Fragment implements MovieSearchMvpView, OnItemClickListener {

    private RecyclerView recyclerView;
    private EditText inputSearch;
    private RealmMoviesAdapter adapter;

    @Inject MovieSearchPresenter presenter;

    public MovieSearchFragment() {
    }

    public static MovieSearchFragment newInstance() {
        MovieSearchFragment fragment = new MovieSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getActivity().getApplication()).getAppComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_search, container, false);
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

        inputSearch = view.findViewById(R.id.input_search);
        RxTextView.textChanges(inputSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    presenter.searchMovies(query);
                    loadSearchResults(query);
                });
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
    public void showSearchResults(RealmResults<Movie> movies) {
        adapter.updateData(movies);
    }

    private void loadSearchResults(String query) {
        if (query.isEmpty()) {
            adapter.updateData(null);
        } else {
            presenter.loadSearchResults(query);
        }
    }
}
