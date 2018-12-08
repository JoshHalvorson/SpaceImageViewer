package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
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

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image_actvity);

        context = this;
        HubbleImage image = getIntent().getParcelableExtra(MainActivity.IMAGE_DATA_KEY);
        imageDetailsLayout = findViewById(R.id.image_details_layout);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);

        imageDetailsLayout.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);
        generateView(image);

    }

    private void generateView(HubbleImage image){
        TextView titleView = new TextView(this);
        TextView descView = new TextView(this);
        TextView creditsView = new TextView(this);
        titleView.setText(image.getName());
        titleView.setTextSize(25);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        if(image.getDescription() == null){
            descView.setText(getString(R.string.image_desc_null_text));
        }else{
            descView.setText(image.getDescription());
        }
        descView.setText(descView.getText().toString());
        descView.setTextSize(17);
        descView.setTextColor(Color.BLACK);
        creditsView.setText(image.getCredits());
        creditsView.setTextSize(8);
        creditsView.setTypeface(null, Typeface.ITALIC);
        imageDetailsLayout.addView(titleView);
        imageDetailsLayout.addView(descView);
        imageDetailsLayout.addView(creditsView);
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
