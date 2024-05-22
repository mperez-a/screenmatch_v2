package com.aluracursos.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rate;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodeData d) {
        this.season = season;
        this.title = d.title();
        this.episodeNumber = d.episodeNumber();
        try {
            this.rate = Double.valueOf(d.rate());
        } catch (NumberFormatException e) {
            this.rate = 0.0;        }
        try {
            this.releaseDate = LocalDate.parse(d.releasedDate());
        } catch (DateTimeException e) {
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getReleasedDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releasedDate) {
        this.releaseDate = releasedDate;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rate=" + rate +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
