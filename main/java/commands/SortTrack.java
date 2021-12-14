package commands;

import comparators.TrackComparator;
import core.Core;
import entities.tracks.Track;

import java.util.List;

public class SortTrack implements Command{
    Core core;
    private int fieldIndex;
    private int orderIndex;

    public SortTrack(Core core, int fieldIndex, int orderIndex){
        this.core = core;
        this.fieldIndex = fieldIndex;
        this.orderIndex = orderIndex;
    }

    public void execute(){
        core.log(1, "Command SortTrack executed");

        if (fieldIndex < 1 || fieldIndex > 4)
            return;

        boolean asc = orderIndex == 1;

        List<Track> tracks = core.getTracksRepository().findAll();

        switch (fieldIndex){
            case 1: {
                tracks.sort(new TrackComparator("name", asc));
                break;
            }
            case 2: {
                tracks.sort(new TrackComparator("artist", asc));
                break;
            }
            case 3: {
                tracks.sort(new TrackComparator("length", asc));
                break;
            }
            case 4: {
                tracks.sort(new TrackComparator("genre", asc));
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tracks.size(); i++){
            sb.append(String.format("%3d.", i+1)).append(tracks.get(i).toString()).append(System.lineSeparator());
        }

        core.getInfoPanel().setText(sb.toString());
    }
}

