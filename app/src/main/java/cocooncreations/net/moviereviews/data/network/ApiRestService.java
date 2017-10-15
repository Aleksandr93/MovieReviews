package cocooncreations.net.moviereviews.data.network;

import android.app.Application;

import com.google.gson.Gson;

import cocooncreations.net.moviereviews.App;
import cocooncreations.net.moviereviews.BuildConfig;
import cocooncreations.net.moviereviews.data.response.SearchResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aleksandr on 10/14/17.
 */

public interface ApiRestService {

    @GET("reviews/search.json")
    Observable<SearchResponse> searchMovies(@Query("query") String query);

    class Creator {

        public static ApiRestService newApiRestService(Application application) {
            Gson gson = ((App) application).getAppComponent().gson();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api-key", BuildConfig.API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BuildConfig.ENDPOINT)
                    .client(httpClient.build())
                    .build();

            return retrofit.create(ApiRestService.class);
        }
    }
}
