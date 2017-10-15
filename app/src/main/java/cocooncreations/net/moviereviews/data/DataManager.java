package cocooncreations.net.moviereviews.data;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.data.network.ApiRestService;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by aleksandr on 10/14/17.
 */

@Singleton
public class DataManager {

    private final ApiRestService apiRestService;

    @Inject
    DataManager(ApiRestService apiRestService) {
        this.apiRestService = apiRestService;
    }

    public void searchMovies(String query) {
        apiRestService.searchMovies(query)
                .flatMap(searchResponse -> Observable.from(searchResponse.getResults()))
                .map(movie -> {
                    Realm realm = Realm.getDefaultInstance();
                    Movie savedMovie = realm.where(Movie.class).equalTo("title", movie.getTitle()).findFirst();
                    realm.beginTransaction();
                    if (savedMovie != null && savedMovie.isValid()) {
                        movie.setBookmark(savedMovie.isBookmarked());
                    }
                    realm.copyToRealmOrUpdate(movie);
                    realm.commitTransaction();
                    return movie;
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(movies -> {
                }, throwable -> {
                    Log.e(this.getClass().getSimpleName(), throwable.getMessage());
                });
    }

    public Observable<Movie> loadMovie(String title) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Movie.class).equalTo("title", title).findFirstAsync().asObservable();
    }

    public RealmResults<Movie> loadSearchResults(String query) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Movie.class).contains("title", query, Case.INSENSITIVE).findAll();
    }

    public RealmResults<Movie> loadBookmarkedMovies() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Movie.class).equalTo("bookmark", true).findAll();
    }

    public void changeBookmarkStatus(String title) {
        Realm realm = Realm.getDefaultInstance();
        Movie movie = realm.where(Movie.class).equalTo("title", title).findFirst();
        if (movie != null && movie.isValid()) {
            realm.beginTransaction();
            movie.setBookmark(!movie.isBookmarked());
            realm.commitTransaction();
        }
    }
}
