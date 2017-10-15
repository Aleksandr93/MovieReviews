package cocooncreations.net.moviereviews.ui.movie.bookmark;

import android.util.Log;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.data.DataManager;
import cocooncreations.net.moviereviews.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by aleksandr on 10/15/17.
 */

public class MovieBookmarkPresenter extends BasePresenter<MovieBookmarkMvpView> {

    private final DataManager dataManager;
    private Subscription subscription;

    @Inject
    MovieBookmarkPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MovieBookmarkMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    void loadBookmarkedMovies() {
        dataManager.loadBookmarkedMovies()
                .subscribe(movies -> {
                    getMvpView().showMovies(movies);
                }, throwable -> {
                    Log.e(this.getClass().getSimpleName(), throwable.getMessage());
                });
    }
}
