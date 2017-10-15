package cocooncreations.net.moviereviews.ui.movie.detail;

import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.MvpView;

/**
 * Created by aleksandr on 10/15/17.
 */

interface MovieDetailMvpView extends MvpView {

    void showMovie(Movie movie);
}
