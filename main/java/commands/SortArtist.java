package commands;

import comparators.ArtistComparator;
import core.Core;
import entities.artists.Artist;

import java.util.List;

public class SortArtist implements Command{
    Core core;
    private int fieldIndex = 0;
    private int orderIndex;

    public SortArtist(Core core, int fieldIndex, int orderIndex){
        this.core = core;
        this.orderIndex = orderIndex;
        this.fieldIndex = fieldIndex;
    }

    public void execute(){
        core.log(1, "Command SortArtist executed");

        if (fieldIndex < 1 || fieldIndex > 2) {
            return;
        }

        boolean asc = orderIndex == 1;

        List<Artist> artists = core.getArtistRepository().findAll();

        if (fieldIndex == 1){
            artists.sort(new ArtistComparator("name", asc));
        }

        if (fieldIndex == 2){
            artists.sort(new ArtistComparator("country", asc));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < artists.size(); i++){
            sb.append(String.format("%3d.", i+1)).append(artists.get(i).toString()).append(System.lineSeparator());
        }
        core.getInfoPanel().setText(sb.toString());
    }
}

