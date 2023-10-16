package org.barbenheimer;

import java.util.List;
import java.util.Objects;

public class Media {

    private String id;
    private String name;
    private String date;
    private String contentType;
    private String media;
    private List<Integer> tags;

    public Media() {
    }

    public Media(String name, String date, String media, String contentType) {
        this.name = name;
        this.date = date;
        this.media = media;
        this.contentType = contentType;
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

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void getContentType(String contentType) {
        this.contentType = contentType;
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

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}
