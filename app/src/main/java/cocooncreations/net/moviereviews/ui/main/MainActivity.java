package cocooncreations.net.moviereviews.ui.main;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cocooncreations.net.moviereviews.R;
import cocooncreations.net.moviereviews.ui.base.adapters.ViewPagerAdapter;
import cocooncreations.net.moviereviews.ui.movie.bookmark.MovieBookmarkFragment;
import cocooncreations.net.moviereviews.ui.movie.search.MovieSearchFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        String[] tabsTitle = getResources().getStringArray(R.array.main_tabs);
        adapter.addFragment(MovieSearchFragment.newInstance(), tabsTitle[0]);
        adapter.addFragment(MovieBookmarkFragment.newInstance(), tabsTitle[1]);
        viewPager.setAdapter(adapter);
    }
}
