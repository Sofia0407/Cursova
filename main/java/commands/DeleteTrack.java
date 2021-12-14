package commands;

import core.Core;
import entities.tracks.Track;

import java.util.List;

public class DeleteTrack implements Command{
    Core core;
    private int trackId;

    public DeleteTrack(Core core, int trackId){
        this.core = core;
        this.trackId = trackId;
    }

    public void execute() {
        core.log(1, "Delete Track command execute");

        List<Track> tracks = core.getTracksRepository().findAll();
        core.getTracksRepository().deleteTrackByName(tracks.get(trackId).getName());
        core.log(1, "Track " + trackId + " deleted");
    }
}
