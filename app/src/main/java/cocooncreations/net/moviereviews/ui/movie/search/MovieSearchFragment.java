package cocooncreations.net.moviereviews.ui.movie.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import io.realm.Realm;
import io.realm.RealmResults;


public class MovieSearchFragment extends Fragment implements MovieSearchMvpView, OnItemClickListener {

    private RecyclerView recyclerView;
    private EditText inputSearch;

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
        setAdapter(recyclerView);

        inputSearch = view.findViewById(R.id.input_search);
        RxTextView.textChanges(inputSearch)
                .filter(charSequence -> charSequence.length() > 3)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .subscribe(query -> {
                    Log.d("LOL", query);
                    presenter.searchMovies(query);
                });
    }

    @Override
    public void onItemClick(View itemView, int position) {

    }

    private void setAdapter(RecyclerView recyclerView) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();
        RealmMoviesAdapter adapter = new RealmMoviesAdapter(getActivity(), movies);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

}
