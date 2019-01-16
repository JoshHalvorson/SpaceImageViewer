package joshuahalvorson.com.joshh.spaceimageviewer.viewmodel;

import java.util.List;

import androidx.lifecycle.ViewModel;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.repository.HubbleImageRepository;

public class HubbleImageClientViewModel extends ViewModel {
    public static List<ImagePreview> getImagePreviews(int page){
        return HubbleImageRepository.getImagePreviews(page);
    }
}
