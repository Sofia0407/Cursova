package commands;

import core.Core;
import entities.artists.Artist;
import entities.tracks.Track;
import tools.Genre;

import java.util.Map;

public class AddTrack implements Command{
    Core core;
    private Map<String, Object> paramsMap;

    public AddTrack(Core core, Map<String, Object> paramsMap){
        this.core = core;
        this.paramsMap = paramsMap;
    }

    public void execute(){
        core.log(1, "Add Track command execute");

        String name = (String)paramsMap.get("name");
        int artistId = (int)paramsMap.get("artistId");
        Artist artist;
        int length = (int)paramsMap.get("len");
        Genre genre;

        artist = core.getArtistRepository().findAll().get(artistId);

        int genreId = (int)paramsMap.get("genreId");
        if (genreId < 0 || genreId >= Genre.values().length){
            System.out.println("Wrong index, set to \"Unknown\"");
            genre = Genre.Unknown;
        }
        else {
            genre = Genre.values()[genreId];
        }

        core.getTracksRepository().save(new Track(name, artist, length, genre));
//        core.log(1, "Track added: " + core.getTracks().get(core.getTracks().size()-1));
    }
}
