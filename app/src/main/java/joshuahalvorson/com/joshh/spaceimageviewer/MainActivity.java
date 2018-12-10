package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String IMAGE_DATA_KEY = "image_data";
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    AllImagesFragment allImagesFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            FragmentTransaction ft = fm.beginTransaction();
            allImagesFragment = new AllImagesFragment();
            ft.add(R.id.all_images_container, allImagesFragment, TAG_LIST_FRAGMENT);
            ft.commit();
        }else{
            allImagesFragment = (AllImagesFragment)fm.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
