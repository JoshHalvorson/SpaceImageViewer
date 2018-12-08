package joshuahalvorson.com.joshh.spaceimageviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HubbleImage {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("mission")
    @Expose
    private String mission;
    @SerializedName("collection")
    @Expose
    private String collection;
    @SerializedName("image_files")
    @Expose
    private List<ImageFile> imageFiles = null;

    private String thumbnailImage;

    private String fullResImage;

    public String getFullResImage() {
        return fullResImage;
    }

    public void setFullResImage(String fullResImage) {
        this.fullResImage = fullResImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredits() {
        return credits;
    }

    public String getNewsName() {
        return newsName;
    }

    public String getCollection() {
        return collection;
    }

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

}
