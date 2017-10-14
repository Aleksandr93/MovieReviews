package cocooncreations.net.moviereviews.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksandr on 10/14/17.
 */

public class Link extends RealmObject {

    @PrimaryKey
    @SerializedName("url")
    private String url;
    @SerializedName("type")
    private String type;
    @SerializedName("suggested_link_text")
    private String linkText;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }
}
