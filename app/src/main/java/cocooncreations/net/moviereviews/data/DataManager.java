package cocooncreations.net.moviereviews.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import cocooncreations.net.moviereviews.data.network.ApiRestService;
import io.realm.Realm;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by aleksandr on 10/14/17.
 */

@Singleton
public class DataManager {

    private ApiRestService apiRestService;

    @Inject
    public DataManager(ApiRestService apiRestService) {
        this.apiRestService = apiRestService;
    }

    public void searchMovies(String query) {
        apiRestService.searchMovies(query)
                .flatMap(searchResponse -> Observable.from(searchResponse.getResults()))
                .map(movie -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movie);
                    realm.commitTransaction();
                    return movie;
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(movies -> {
                }, throwable -> {
                });
    }
}
