package de.zuellich.meal_planner.pinterest.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pin {

    /**
     * Pinterest's unique identifier for this pin.
     */
    private String id;

    /**
     * The URL to the pinned page.
     */
    private String link;

    public Pin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return Objects.equals(getId(), pin.getId()) &&
                Objects.equals(getLink(), pin.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLink());
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
