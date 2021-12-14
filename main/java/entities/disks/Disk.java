package entities.disks;

import entities.tracks.Track;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("disks")
public class Disk {
    @Id
    private String name;
    private double price;
    private List<Track> tracks;

    public Disk(String name, double price, List<Track> tracks) {
        this.name = name;
        this.price = price;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double artist) {
        this.price = artist;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String toString(){
        StringBuilder result = new StringBuilder(String.format("Name: %s\nPrice: %f", this.name, this.price));

        for (int i = 0; i < tracks.size(); i++){
            result.append("\n  ").append(i+1 + ". ").append(tracks.get(i).toString());
        }

        return result.toString();
    }

}
