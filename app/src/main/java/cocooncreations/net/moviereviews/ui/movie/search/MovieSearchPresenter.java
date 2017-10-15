package cocooncreations.net.moviereviews.ui.movie.search;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.data.DataManager;
import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.BasePresenter;
import io.realm.RealmResults;
import rx.Subscription;

/**
 * Created by aleksandr on 10/14/17.
 */

public class MovieSearchPresenter extends BasePresenter<MovieSearchMvpView> {

    private final DataManager dataManager;
    private Subscription subscription;

    @Inject
    MovieSearchPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MovieSearchMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    void searchMovies(String query) {
        if (query != null && !query.isEmpty()) {
            dataManager.searchMovies(query);
        }
    }

    void loadSearchResults(String query) {
        checkViewAttached();
        RealmResults<Movie> movies = dataManager.loadSearchResults(query);
        getMvpView().showSearchResults(movies);
    }
}
