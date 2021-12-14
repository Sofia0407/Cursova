package comparators;

import entities.artists.Artist;

import java.util.Comparator;

public class ArtistComparator implements Comparator<Artist> {
    String field;
    boolean asc;

    public ArtistComparator(String field, boolean asc){
        this.field = field;
        this.asc = asc;
    }

    @Override
    public int compare(Artist o1, Artist o2) {
        if (field.equals("name")){
            if (asc){
                return o1.getName().compareTo(o2.getName()) > 0 ? 1 : -1;
            }
            else{
                return o1.getName().compareTo(o2.getName()) > 0 ? -1 : 1;
            }
        }

        else {
            if (asc){
                return o1.getCountry().compareTo(o2.getCountry()) > 0 ? 1 : -1;
            }
            else{
                return o1.getCountry().compareTo(o2.getCountry()) > 0 ? -1 : 1;
            }
        }
    }
}
