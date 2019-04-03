package joshuahalvorson.com.joshh.spaceimageviewer.Java.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.view.fragment.ImagePreviewsFragment.OnListFragmentInteractionListener;
import java.util.List;

public class MyHubbleImageRecyclerViewAdapter extends RecyclerView.Adapter<MyHubbleImageRecyclerViewAdapter.ViewHolder> {

    private final List<ImagePreview> imagePreviews;
    private final OnListFragmentInteractionListener mListener;

    public MyHubbleImageRecyclerViewAdapter(List<ImagePreview> imagePreviews, OnListFragmentInteractionListener listener) {
        this.imagePreviews = imagePreviews;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.images_element_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.image = imagePreviews.get(position);
        holder.imageName.setText(imagePreviews.get(position).getName());

        animateOnEnter(holder.mView, holder, position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onImagePreviewsFragmentInteraction(holder.image, holder.imageName);
                }
            }
        });
    }

    public void animateOnEnter(View view, ViewHolder viewHolder, int position){
        if(position > viewHolder.lastPos){
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in);
            animation.setDuration(100);
            view.startAnimation(animation);
            viewHolder.lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return imagePreviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView imageName;
        public ImagePreview image;
        int lastPos;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageName = view.findViewById(R.id.image_name_text);
            lastPos = -1;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
