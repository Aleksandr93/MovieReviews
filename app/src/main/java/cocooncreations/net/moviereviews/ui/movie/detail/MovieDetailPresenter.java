package cocooncreations.net.moviereviews.ui.movie.detail;

import android.util.Log;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.data.DataManager;
import cocooncreations.net.moviereviews.ui.base.BasePresenter;
import io.realm.internal.ManagableObject;
import rx.Subscription;

/**
 * Created by aleksandr on 10/15/17.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailMvpView> {

    private final DataManager dataManager;
    private Subscription subscription;

    @Inject
    MovieDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MovieDetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    void loadMovie(String title) {
        checkViewAttached();
        subscription = dataManager.loadMovie(title)
                .filter(ManagableObject::isValid)
                .subscribe(movie -> {
                    getMvpView().showMovie(movie);
                }, throwable -> {
                    Log.e(this.getClass().getSimpleName(), throwable.getMessage());
                });
    }

    void changeBookmarkStatus(String title) {
        dataManager.changeBookmarkStatus(title);
    }

}
