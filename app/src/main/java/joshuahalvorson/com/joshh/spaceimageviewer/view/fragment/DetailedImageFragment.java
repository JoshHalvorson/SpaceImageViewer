package joshuahalvorson.com.joshh.spaceimageviewer.view.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImageFile;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.viewmodel.HubbleImageClientViewModel;

public class DetailedImageFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;

    private ImageView image;
    private TextView imageTitle, imageDesc, imageCredits;
    private ProgressBar loadingCircle;

    private ImagePreview imagePreview;
    private HubbleImage hubbleImage;

    public DetailedImageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detailed_image_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = view.findViewById(R.id.image);
        imageTitle = view.findViewById(R.id.image_name);
        imageDesc = view.findViewById(R.id.image_desc);
        imageCredits = view.findViewById(R.id.image_credits);
        loadingCircle = view.findViewById(R.id.loading_circle);

        imagePreview = (ImagePreview)getArguments().getSerializable("image_preview");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new getImageData().execute();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public interface OnListFragmentInteractionListener {
        void onDetailedImageFragmentInteraction(ImagePreview item);
    }

    private class getImageData extends AsyncTask<Void, Integer, HubbleImage> {

        @Override
        protected HubbleImage doInBackground(Void... voids) {
            HubbleImage hubbleImage = HubbleImageClientViewModel.getImageData(imagePreview.getId());
            return hubbleImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(HubbleImage hubbleImage) {
            super.onPostExecute(hubbleImage);
            List<ImageFile> imageFiles = hubbleImage.getImageFiles();
            String imageUrl = imageFiles.get(imageFiles.size() - 1).getFileUrl();
            if(imageFiles != null){
                Glide
                        .with(getActivity())
                        .load(imageUrl)
                        .apply(new RequestOptions().centerCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(getContext(), "Image failed to load", Toast.LENGTH_SHORT).show();
                                loadingCircle.setVisibility(View.GONE);
                                image.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                loadingCircle.setVisibility(View.GONE);
                                image.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .thumbnail(.5f)
                        .into(image);
            }
            imageTitle.setText(hubbleImage.getName());
            imageDesc.setText(hubbleImage.getDescription());
            imageCredits.setText(hubbleImage.getCredits());
        }
    }
}
