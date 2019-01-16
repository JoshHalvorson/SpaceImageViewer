package joshuahalvorson.com.joshh.spaceimageviewer.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.view.fragment.ImagePreviewsFragment.OnListFragmentInteractionListener;
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.image);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagePreviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView imageName;
        public ImagePreview image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageName = view.findViewById(R.id.image_name_text);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
