package cocooncreations.net.moviereviews.ui.movie.favorite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cocooncreations.net.moviereviews.R;


public class MovieFavoriteFragment extends Fragment {

    public MovieFavoriteFragment() {
    }

    public static MovieFavoriteFragment newInstance() {
        MovieFavoriteFragment fragment = new MovieFavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

}
