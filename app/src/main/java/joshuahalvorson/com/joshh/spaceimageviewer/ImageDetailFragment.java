package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageDetailFragment extends Fragment {
    private LinearLayout imageDetailsLayout;
    private TextView titleView, descView, creditsView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Context mContext;

    public ImageDetailFragment(){

    }

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_detailed_image_actvity, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageDetailsLayout = view.findViewById(R.id.image_details_layout);
        imageView = view.findViewById(R.id.image_view);
        titleView = view.findViewById(R.id.title_text);
        descView = view.findViewById(R.id.desc_text);
        creditsView = view.findViewById(R.id.credits_text);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HubbleImage image = getArguments().getParcelable("image");
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
        setViewData(image);
    }

    private void loadImageIntoView(HubbleImage image) {
        Glide
            .with(getActivity())
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

    private void loadGifIntoView(HubbleImage image) {
        Glide
                .with(getActivity())
                .asGif()
                .load(image.getFullResImage())
                .thumbnail(.1f)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imageView);
    }


    public void setViewData(HubbleImage image){
        if(getActivity() != null){
            if(image.getFullResImage().contains(".gif")){
                loadGifIntoView(image);
            }else{
                loadImageIntoView(image);
            }
        }

    }
}
