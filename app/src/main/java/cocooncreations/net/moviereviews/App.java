package cocooncreations.net.moviereviews;

import android.app.Application;

import cocooncreations.net.moviereviews.injection.component.AppComponent;
import cocooncreations.net.moviereviews.injection.component.DaggerAppComponent;
import cocooncreations.net.moviereviews.injection.module.AppModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by aleksandr on 10/14/17.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
