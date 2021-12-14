package commands;

import core.Core;
import entities.disks.Disk;
import entities.tracks.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddDisk implements Command{
    Core core;
    private Map<String, Object> paramsMap;

    public AddDisk(Core core, Map<String, Object> paramsMap){
        this.core = core;
        this.paramsMap = paramsMap;
    }

    public void execute(){
        core.log(1, "Add Disk command execute");
        List<Track> tracks = new ArrayList<>();

        String tracksString = (String)paramsMap.get("tracks");
        String name = (String)paramsMap.get("name");
        double price = (double)paramsMap.get("price");

        List<Track> availableTracks = core.getTracksRepository().findAll();
        if (tracksString.contains(",")){
            String[] indexes = tracksString.split("(,)+( )*");
            for (String index : indexes) {
                int audioIndex = Integer.parseInt(index) - 1;

                tracks.add(availableTracks.get(audioIndex));
            }
        }
        else {
            int audioIndex = Integer.parseInt(tracksString)-1;
            tracks.add(availableTracks.get(audioIndex));
        }

        core.getDisksRepository().save(new Disk(name, price, tracks));
        core.log(1, "Disk added");
    }
}
