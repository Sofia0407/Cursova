package entities.tracks;

import entities.artists.Artist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.Genre;
import tools.Tools;

@Document("tracks")
public class Track {
    @Id
    private String name;
    private Artist artist;
    private int length;
    private Genre genre;

    public Track(String name, Artist artist, int length, Genre genre) {
        this.name = name;
        this.artist = artist;
        this.length = length;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String toString(){
        return String.format("Name: %-20s\tArtist: %-20s\tLength: %8s\tGenre: %s",
                this.name, this.artist.getName() + ", "+ this.artist.getCountry(), Tools.lengthToString(this.length), this.genre);
    }
}
