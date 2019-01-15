package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ImagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private HubbleImageAdapter adapter;
    private Context context;
    private ImageDetailFragment imageDetailFragment;
    private HubbleImageDetailsViewModel viewModel;
    private Button searchButton;
    private EditText searchText;

    Observer<List<HubbleImage>> observer = new Observer<List<HubbleImage>>() {
        @Override
        public void onChanged(List<HubbleImage> images) {
            adapter = new HubbleImageAdapter(context, images, new HubbleImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(HubbleImage image) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    imageDetailFragment = new ImageDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("image", image);
                    imageDetailFragment.setArguments(bundle);
                    ft.replace(R.id.fragment_container, imageDetailFragment, "image_details_key");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
            recyclerView.setAdapter(adapter);
        }
    };

    public ImagesFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HubbleImageDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.images_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.all_images_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        searchButton = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_list);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getImageList().observe(this, observer);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.searchList(searchText.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getImageList().removeObserver(observer);
    }

}
