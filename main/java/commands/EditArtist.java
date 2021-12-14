package commands;

import core.Core;
import entities.artists.Artist;

import java.util.List;
import java.util.Optional;

public class EditArtist implements Command {
    Core core;
    private String artistName;
    private Artist newArtist;

    public EditArtist(Core core, String artistName, Artist newArtist){
        this.core = core;
        this.artistName = artistName;
        this.newArtist = newArtist;
    }

    public void execute(){
        core.log(1, "Edit Artist command execute");

        if(artistName.isEmpty())
            return;

        Optional<Artist> artistOptional = core.getArtistRepository().findById(artistName);
        if(!artistOptional.isPresent())
            return;

        Artist artist = artistOptional.get();

        if (!newArtist.getName().isEmpty()) {
            artist.setName(newArtist.getName());
        }

        if (!newArtist.getCountry().isEmpty()) {
            artist.setCountry(newArtist.getCountry());
        }

        core.getArtistRepository().save(artist);

        core.log(1, "Artist edited");
    }
}
