package commands;

import core.Core;
import entities.artists.Artist;

public class AddArtist implements Command{
    Core core;
    private Artist artist;

    public AddArtist(Core core, Artist artist){
        this.artist = artist;
        this.core = core;
    }

    public void execute(){
        core.log(1, "Add Artist command execute");

        core.getArtistRepository().save(artist);
//        core.log(1, "Added artist: " + core.getArtists().get(core.getArtists().size()-1));
    }
}
