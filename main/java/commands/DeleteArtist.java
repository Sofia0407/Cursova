package commands;

import core.Core;
import entities.artists.Artist;

import java.util.List;

public class DeleteArtist implements Command{
    private Core core;
    private int artistId;

    public DeleteArtist(Core core, int artistId){
        this.core = core;
        this.artistId = artistId;
    }

    public void execute() {
        core.log(1, "Delete Artist command execute");

        List<Artist> artists = core.getArtistRepository().findAll();
        core.getArtistRepository().deleteArtistByName(artists.get(artistId).getName());

        core.log(1, "Artist " + artistId + " deleted");
    }
}
