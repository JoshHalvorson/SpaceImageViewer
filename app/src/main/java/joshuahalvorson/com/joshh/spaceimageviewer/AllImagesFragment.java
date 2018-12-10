package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllImagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private HubbleImageAdapter adapter;
    private Context context;
    private ImageDetailFragment imageDetailFragment;
    private HubbleImageDetailsViewModel viewModel;

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
                    ft.add(R.id.image_details_container, imageDetailFragment, "image_details_key");
                    ft.addToBackStack(null);
                    ft.commit();
                    //Log.i("sdadsaD", Integer.toString(getChildFragmentManager().getBackStackEntryCount()));

                    //imageDetailFragment.setViewData(image);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    };

    public AllImagesFragment(){

    }

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HubbleImageDetailsViewModel.class);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_images_fragment_layout, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.all_images_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
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
        viewModel.getImageList().observe(this, observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getImageList().removeObserver(observer);
    }

    public void populateRecyclerView(){

    }
}
