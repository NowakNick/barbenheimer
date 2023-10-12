package org.barbenheimer;

import java.util.List;
import java.util.Objects;

public class Media {

    private String name;
    private List<String> tags;
    private String id;
    private String media;
    private String date;

    public Media() {
    }

    public Media(String name, String description) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
