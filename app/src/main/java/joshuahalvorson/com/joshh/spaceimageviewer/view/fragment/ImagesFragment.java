package joshuahalvorson.com.joshh.spaceimageviewer.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import joshuahalvorson.com.joshh.spaceimageviewer.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.adapter.MyHubbleImageRecyclerViewAdapter;
import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.viewmodel.HubbleImageClientViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private static List<ImagePreview> imagePreviews;
    private MyHubbleImageRecyclerViewAdapter adapter;

    public ImagesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.images_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();

        imagePreviews = new ArrayList<>();

        adapter = new MyHubbleImageRecyclerViewAdapter(imagePreviews, mListener);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getImagePreviews().execute();
            }
        });

        //
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ImagePreview item);
    }

    private class getImagePreviews extends AsyncTask<Void, Integer, List<ImagePreview>>{

        @Override
        protected List<ImagePreview> doInBackground(Void... voids) {
            List<ImagePreview> imagePreviews = HubbleImageClientViewModel.getImagePreviews(1);
            return imagePreviews;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ImagePreview> imagePreviewsList) {
            super.onPostExecute(imagePreviewsList);
            imagePreviews.clear();
            imagePreviews.addAll(imagePreviewsList);
            adapter.notifyDataSetChanged();
        }
    }
}
