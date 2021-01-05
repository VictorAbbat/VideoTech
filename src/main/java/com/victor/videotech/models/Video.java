package com.victor.videotech.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="video",uniqueConstraints={@UniqueConstraint(columnNames = "title")})
public class Video {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name="genre")
    @NotNull
    private String genre;

    @Column(name="description")
    private String description;

    @Column(name="year")
    @Min(1895) @Max(2999)
    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Video() {

    }

    public Video(Integer id, String title, String genre, String description, Integer year) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                '}';
    }

}
