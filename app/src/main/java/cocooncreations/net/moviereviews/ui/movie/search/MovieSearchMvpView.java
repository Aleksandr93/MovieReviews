package cocooncreations.net.moviereviews.ui.movie.search;

import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.MvpView;
import io.realm.RealmResults;

/**
 * Created by aleksandr on 10/14/17.
 */

public interface MovieSearchMvpView extends MvpView {

    void showSearchResults(RealmResults<Movie> movies);
}
