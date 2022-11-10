package com.example.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Table(name="label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="label_id")
    private Integer labelId;
    private String name;
    private String website;

    public Label() {
    }

    public Label(Integer labelId, String name, String website) {
        this.labelId = labelId;
        this.name = name;
        this.website = website;
    }

    public Label(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return labelId == label.labelId && Objects.equals(name, label.name) && Objects.equals(website, label.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelId, name, website);
    }

    @Override
    public String toString() {
        return "Label{" +
                "labelId=" + labelId +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
