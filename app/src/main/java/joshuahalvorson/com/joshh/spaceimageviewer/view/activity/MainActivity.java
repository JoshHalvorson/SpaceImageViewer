package joshuahalvorson.com.joshh.spaceimageviewer.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.view.fragment.ImagePreviewsFragment;
import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.adapter.ViewPagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements ImagePreviewsFragment.OnListFragmentInteractionListener {
    public static final String IMAGE_DATA_KEY = "image_data";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ImagePreviewsFragment(), "Hubble Pictures");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onListFragmentInteraction(ImagePreview item) {
        Log.i("imagePressed", item.getName() + " - " + Integer.toString(item.getId()));
    }
}
