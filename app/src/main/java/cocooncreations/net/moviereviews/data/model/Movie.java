package cocooncreations.net.moviereviews.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksandr on 10/14/17.
 */

public class Movie extends RealmObject {

    @PrimaryKey
    @SerializedName("display_title")
    private String title;
    @SerializedName("headline")
    private String headline;
    @SerializedName("byline")
    private String byline;
    @SerializedName("summary_short")
    private String summary;
    @SerializedName("link")
    private Link link;
    @SerializedName("multimedia")
    private Multimedia multimedia;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }
}
