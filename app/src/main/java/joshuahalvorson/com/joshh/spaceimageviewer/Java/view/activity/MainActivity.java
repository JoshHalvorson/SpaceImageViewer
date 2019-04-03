package joshuahalvorson.com.joshh.spaceimageviewer.Java.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.view.DetailsTransition;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.view.fragment.DetailedImageFragment;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.view.fragment.ImagePreviewsFragment;
import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.adapter.ViewPagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements ImagePreviewsFragment.OnListFragmentInteractionListener,
        DetailedImageFragment.OnListFragmentInteractionListener {
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
    public void onImagePreviewsFragmentInteraction(ImagePreview item, View sharedView) {
        Log.i("imagePressed", item.getName() + " - " + Integer.toString(item.getId()));

        DetailedImageFragment detailedImageFragment = new DetailedImageFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailedImageFragment.setSharedElementEnterTransition(new DetailsTransition());
            detailedImageFragment.setEnterTransition(new Fade());
            detailedImageFragment.setExitTransition(new Fade());
            detailedImageFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("image_preview", item);
        detailedImageFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(sharedView, "image_name_transition")
                .replace(R.id.fragment_container, detailedImageFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDetailedImageFragmentInteraction(ImagePreview item) {

    }
}
