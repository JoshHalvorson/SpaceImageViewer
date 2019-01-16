package joshuahalvorson.com.joshh.spaceimageviewer.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagePreview {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("collection")
    @Expose
    private String collection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
