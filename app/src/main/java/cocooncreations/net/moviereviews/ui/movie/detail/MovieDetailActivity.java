package cocooncreations.net.moviereviews.ui.movie.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import cocooncreations.net.moviereviews.App;
import cocooncreations.net.moviereviews.R;
import cocooncreations.net.moviereviews.data.model.Movie;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailMvpView, View.OnClickListener {

    public static final String ARG_MOVIE_TITLE = "arg_movie_title";

    private ImageView imageMain;
    private TextView textHeadline;
    private TextView textByline;
    private TextView textSummary;
    private Button btnOpenLink;
    private FloatingActionButton fabBookmark;

    @Inject MovieDetailPresenter presenter;

    private String movieTitle;
    private String linkUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ((App)getApplication()).getAppComponent().inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageMain = findViewById(R.id.image_main);
        textHeadline = findViewById(R.id.text_headline);
        textByline = findViewById(R.id.text_byline);
        textSummary = findViewById(R.id.text_summary);
        btnOpenLink = findViewById(R.id.btn_open_link);
        btnOpenLink.setOnClickListener(this);
        fabBookmark = findViewById(R.id.fab_bookmark);
        fabBookmark.setOnClickListener(this);

        presenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            movieTitle = extras.getString(ARG_MOVIE_TITLE);
            presenter.loadMovie(movieTitle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_link:
                if (linkUrl != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
                    startActivity(browserIntent);
                }
                break;
            case R.id.fab_bookmark:
                if (movieTitle != null) {
                    presenter.changeBookmarkStatus(movieTitle);
                }
                break;
        }
    }

    @Override
    public void showMovie(Movie movie) {
        if (movie.getMultimedia() != null) {
            Glide.with(this).load(movie.getMultimedia().getSrc())
                    .apply(new RequestOptions().placeholder(R.drawable.img_default_movie).error(R.drawable.img_default_movie))
                    .into(imageMain);
        } else {
            imageMain.setImageResource(R.drawable.img_default_movie);
        }
        setTitle(movie.getTitle());
        textHeadline.setText(movie.getHeadline());
        textByline.setText(movie.getByline());
        textSummary.setText(movie.getSummary());

        if (movie.getLink() != null) {
            btnOpenLink.setText(movie.getLink().getLinkText());
            linkUrl = movie.getLink().getUrl();
        }
        fabBookmark.setImageResource(movie.isBookmarked() ? R.drawable.ic_bookmark_24dp : R.drawable.ic_bookmark_border_24dp);
    }

}
