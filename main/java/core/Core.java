package core;

import config.MongoConfig;
import entities.artists.Artist;
import entities.disks.Disk;
import entities.tracks.Track;
import javafx.scene.control.TextArea;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.ArtistsRepository;
import repository.DisksRepository;
import repository.TracksRepository;
import tools.Logger;

import java.util.ArrayList;
import java.util.List;

public class Core {
    static List<Artist> artists = new ArrayList<>();
    static List<Track> tracks = new ArrayList<>();
    static List<Disk> disks = new ArrayList<>();
    static Logger logger = new Logger();
    private final ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
    private final ArtistsRepository artistRepository;
    private final DisksRepository disksRepository;
    private final TracksRepository tracksRepository;
    private TextArea infoPanel;

    public Core(TextArea infoPanel) {
        this.infoPanel = infoPanel;
        artistRepository = context.getBean(ArtistsRepository.class);
        disksRepository = context.getBean(DisksRepository.class);
        tracksRepository = context.getBean(TracksRepository.class);
    }

    public TextArea getInfoPanel() {
        return infoPanel;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Disk> getDisks() {
        return disks;
    }

    public void log(int level, String text){
        logger.log(level, text);
    }

    public void closeLogger(){
        logger.close();
    }

    public ArtistsRepository getArtistRepository() {
        return artistRepository;
    }

    public DisksRepository getDisksRepository() {
        return disksRepository;
    }

    public TracksRepository getTracksRepository() {
        return tracksRepository;
    }

    public static void setArtists(List<Artist> artists) {
        Core.artists = artists;
    }

    public static void setTracks(List<Track> tracks) {
        Core.tracks = tracks;
    }

    public static void setDisks(List<Disk> disks) {
        Core.disks = disks;
    }
}
