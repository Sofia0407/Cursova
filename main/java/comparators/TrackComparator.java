package comparators;

import entities.artists.Artist;
import tools.Genre;
import entities.tracks.Track;

import java.util.Comparator;

public class TrackComparator implements Comparator<Track> {
    String field;
    boolean asc;

    public TrackComparator(String field, boolean asc){
        this.field = field;
        this.asc = asc;
    }

    private String name;
    private Artist artist;
    private int length;
    private Genre genre;

    @Override
    public int compare(Track o1, Track o2) {
        switch (field) {
            case "name":
                if (asc) {
                    return o1.getName().compareTo(o2.getName()) > 0 ? 1 : -1;
                } else {
                    return o1.getName().compareTo(o2.getName()) > 0 ? -1 : 1;
                }
            case "artist":
                if (asc) {
                    return o1.getArtist().getName().compareTo(o2.getArtist().getName()) > 0 ? 1 : -1;
                } else {
                    return o1.getArtist().getName().compareTo(o2.getArtist().getName()) > 0 ? -1 : 1;
                }
            case "length":
                if (asc) {
                    return o1.getLength() > o2.getLength() ? 1 : -1;
                } else {
                    return o1.getLength() > o2.getLength() ? -1 : 1;
                }
            case "genre":
                if (asc) {
                    return o1.getGenre().toString().compareTo(o2.getGenre().toString()) > 0 ? 1 : -1;
                } else {
                    return o1.getGenre().toString().compareTo(o2.getGenre().toString()) > 0 ? -1 : 1;
                }
            default:
                return 0;
        }
    }
}
