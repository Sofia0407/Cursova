package commands;

import core.Core;
import entities.artists.Artist;
import entities.tracks.Track;
import tools.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditTrack implements Command {
    Core core;
    private Map<String, Object> paramsMap;

    public EditTrack(Core core, Map<String, Object> paramsMap){
        this.core = core;
        this.paramsMap = paramsMap;
    }

    public void execute(){
        core.log(1, "Edit Track command execute");

        String id = (String)paramsMap.get("id");
        Artist artist = null;
        int length = -1;
        Genre genre = null;

        if(id.isEmpty())
            return;

        String artistId = (String)paramsMap.get("artistId");

        if (!artistId.isEmpty()){
            artist = core.getArtistRepository().findById(artistId).orElse(null);
        }

        // Length
        String lengthParam = (String)paramsMap.get("len");

        if (!lengthParam.isEmpty()) {
            length = Integer.parseInt(lengthParam);
        }

        // Genre
        // Друк всіх жанрів
        String genreId = (String)paramsMap.get("genreId");

        if (!genreId.isEmpty()){
            int genreIndex = Integer.parseInt(genreId) - 1;

            if (genreIndex < 0 || genreIndex > Genre.values().length-1){
                genre = Genre.Unknown;
            }
            else {
                genre = Genre.values()[genreIndex];
            }
        }

        Optional<Track> trackOptional = core.getTracksRepository().findById(id);

        if(!trackOptional.isPresent())
            return;

        Track track = trackOptional.get();

        if (artist != null){
            track.setArtist(artist);
        }

        if (length > 0){
            track.setLength(length);
        }

        if (genre != null){
            track.setGenre(genre);
        }

        core.getTracksRepository().save(track);
        core.log(1, "Track edited");
    }
}
