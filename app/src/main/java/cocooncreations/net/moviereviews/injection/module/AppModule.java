package cocooncreations.net.moviereviews.injection.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import cocooncreations.net.moviereviews.data.network.ApiRestService;
import cocooncreations.net.moviereviews.injection.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;

/**
 * Created by aleksandr on 10/14/17.
 */

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiRestService provideApiRestService(Application application) {
        return ApiRestService.Creator.newApiRestService(application);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        return gsonBuilder.create();
    }

}
