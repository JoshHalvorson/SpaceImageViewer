package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class DetailedImageActvity extends AppCompatActivity {

    private LinearLayout imageDetailsLayout;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image_actvity);

        HubbleImage image = getIntent().getParcelableExtra(MainActivity.IMAGE_DATA_KEY);
        imageDetailsLayout = findViewById(R.id.image_details_layout);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);

        imageDetailsLayout.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);
        generateView(image);

    }

    private void generateView(HubbleImage image){
        TextView tv = new TextView(DetailedImageActvity.this);
        if(image.getDescription() == null){
            tv.setText(getString(R.string.image_desc_null_text));
        }else{
            tv.setText(image.getDescription());
        }
        tv.setTextSize(17);
        tv.setTextColor(Color.BLACK);
        imageDetailsLayout.addView(tv);
        Glide
                .with(DetailedImageActvity.this)
                .load(image.getFullResImage())
                .thumbnail(.1f)
                .apply(new RequestOptions()
                        .centerCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
