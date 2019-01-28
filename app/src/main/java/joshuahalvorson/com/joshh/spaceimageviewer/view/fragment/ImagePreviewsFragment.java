package joshuahalvorson.com.joshh.spaceimageviewer.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import joshuahalvorson.com.joshh.spaceimageviewer.adapter.MyHubbleImageRecyclerViewAdapter;
import joshuahalvorson.com.joshh.spaceimageviewer.R;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.viewmodel.HubbleImageClientViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewsFragment extends Fragment {
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 168;
    private int currentPage = 1;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private LiveData<List<ImagePreview>> imagePreviews;
    private List<ImagePreview> imagePreviewsList;

    private MyHubbleImageRecyclerViewAdapter adapter;

    private Button prevPageButton, nextPageButton;
    private TextView pageNumber;

    Context context;

    public ImagePreviewsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.images_preview_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagePreviewsList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        adapter = new MyHubbleImageRecyclerViewAdapter(imagePreviewsList, mListener);
        recyclerView.setAdapter(adapter);

        context = view.getContext();

        nextPageButton = view.findViewById(R.id.next_page_button);
        prevPageButton = view.findViewById(R.id.prev_page_button);
        pageNumber = view.findViewById(R.id.page_number);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateList(currentPage);

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < LAST_PAGE){
                    currentPage++;
                    updateList(currentPage);
                }else{
                    Toast.makeText(getActivity(), "No next page", Toast.LENGTH_LONG).show();
                }
            }
        });

        prevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage > FIRST_PAGE){
                    currentPage--;
                    updateList(currentPage);
                }else{
                    Toast.makeText(getActivity(), "No previous page", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateList(int page) {
        imagePreviews = HubbleImageClientViewModel.getImagePreviews(page);
        imagePreviews.observe(this, new Observer<List<ImagePreview>>() {
            @Override
            public void onChanged(List<ImagePreview> retImagePreviewsList) {
                pageNumber.setText(Integer.toString(currentPage));
                imagePreviewsList.clear();
                imagePreviewsList.addAll(retImagePreviewsList);
                recyclerView.getLayoutManager().scrollToPosition(0);
                adapter.notifyDataSetChanged();
            }
        });
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
        void onImagePreviewsFragmentInteraction(ImagePreview item, View sharedView);
    }
}
