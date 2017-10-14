package cocooncreations.net.moviereviews.injection.component;

import com.google.gson.Gson;

import javax.inject.Singleton;

import cocooncreations.net.moviereviews.injection.module.AppModule;
import cocooncreations.net.moviereviews.ui.movie.search.MovieSearchFragment;
import dagger.Component;

/**
 * Created by aleksandr on 10/14/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MovieSearchFragment fragment);

    Gson gson();
}
