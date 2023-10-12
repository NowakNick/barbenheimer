package org.barbenheimer;

import java.util.List;
import java.util.Objects;

public class Media {

    private String id;
    private String name;
    private String date;
    private String media;
    private List<String> tags;

    public Media() {
    }

    public Media(String name,
                 String id,
                 String date,
                 String media,
                 List<String> tags) {

        this.name = name;
        this.id = id;
        this.date = date;
        this.media = media;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Media)) {
            return false;
        }

        Media other = (Media) obj;

        return Objects.equals(other.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
