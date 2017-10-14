package cocooncreations.net.moviereviews.ui.base;

/**
 * Created by a.trykashnyi on 03.10.16.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
