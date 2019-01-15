package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import joshuahalvorson.com.joshh.spaceimageviewer.ImagesFragment.OnListFragmentInteractionListener;
import java.util.List;

public class MyHubbleImageRecyclerViewAdapter extends RecyclerView.Adapter<MyHubbleImageRecyclerViewAdapter.ViewHolder> {

    private final List<HubbleImage> hubbleImages;
    private final OnListFragmentInteractionListener mListener;

    public MyHubbleImageRecyclerViewAdapter(List<HubbleImage> hubbleImages, OnListFragmentInteractionListener listener) {
        this.hubbleImages = hubbleImages;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hubbleimage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.image = hubbleImages.get(position);
        holder.mIdView.setText(hubbleImages.get(position).getName());

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
        return hubbleImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public HubbleImage image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
