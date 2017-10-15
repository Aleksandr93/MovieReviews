package cocooncreations.net.moviereviews.ui.movie.bookmark;

import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.MvpView;
import io.realm.RealmResults;

/**
 * Created by aleksandr on 10/15/17.
 */

interface MovieBookmarkMvpView extends MvpView {

    void showMovies(RealmResults<Movie> movies);

}
