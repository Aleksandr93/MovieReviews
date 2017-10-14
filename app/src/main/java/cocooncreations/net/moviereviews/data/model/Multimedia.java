package cocooncreations.net.moviereviews.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksandr on 10/14/17.
 */

public class Multimedia extends RealmObject {

    @PrimaryKey
    @SerializedName("src")
    private String src;
    @SerializedName("width")
    private Integer width;
    @SerializedName("height")
    private Integer height;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
