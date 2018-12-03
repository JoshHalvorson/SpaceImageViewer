package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.senab.photoview.PhotoView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_INDEX = 4277;
    public static final String HTTP_HUBBLESITE_ORG = "http://hubblesite.org/";
    private TextView imageDesc, imageCredits, imageName;
    private LinearLayout imageHolderLayout;
    private ProgressBar progressBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        imageDesc = findViewById(R.id.image_desc);
        imageName = findViewById(R.id.image_name);
        progressBar = findViewById(R.id.progress_bar);
        imageCredits = findViewById(R.id.image_credits);
        imageHolderLayout = findViewById(R.id.image_holder_layout);

        findViewById(R.id.new_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getNewImage(randomId(1, MAX_INDEX));
                //Log.i("imageid", Integer.toString(id));
            }
        });
        progressBar.setVisibility(View.VISIBLE);

        getNewImage(randomId(1, MAX_INDEX));
        //Log.i("imageid", Integer.toString(id) + " - onCreate");
    }

    private static int randomId(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void getNewImage(int id) {
        SpaceTelescopeImage image = NetworkAdapter.getImage(id);
        if (image != null) {
            List<ImageFile> imageUrls = image.getImageFiles();
            String urls = "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < imageUrls.size(); i++) {
                String url = imageUrls.get(i).getFileUrl();
                if (url.substring(url.length() - 4, url.length()).equals(".jpg") || url.substring(url.length() - 4, url.length()).equals(".png")) {
                    sb.append(url).append(",");
                }
            }
            urls = sb.toString();
            urls = urls.replaceAll(", $", "");
            String[] validUrls = urls.split(",");
            String thumbnailUrl;
            if (validUrls.length == 1) {
                thumbnailUrl = validUrls[0];
            } else {
                thumbnailUrl = validUrls[1];
            }
            PhotoView photoView = NetworkAdapter.displayImage(validUrls[validUrls.length - 1], thumbnailUrl, progressBar, context);
            imageHolderLayout.removeAllViews();
            imageHolderLayout.addView(photoView);
            imageDesc.setText(image.getDescription());
            if (imageDesc.getText().toString().equals("")) {
                imageDesc.setText(getString(R.string.image_desc_default_text));
            }
            imageName.setText(image.getName());
            imageCredits.setText(image.getCredits());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
