package commands;

import core.Core;
import entities.disks.Disk;
import entities.tracks.Track;
import repository.DisksRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditDisk implements Command{
    Core core;
    private Map<String, Object> paramsMap;

    public EditDisk(Core core, Map<String, Object> paramsMap){
        this.core = core;
        this.paramsMap = paramsMap;
    }

    public void execute(){
        core.log(1, "Edit Disk command execute");

        String id = (String)paramsMap.get("id");
        String name = (String)paramsMap.get("name");
        String price = (String)paramsMap.get("price");
        String tracksList = (String)paramsMap.get("tracks");

        Optional<Disk> diskOptional = core.getDisksRepository().findById(id);
        if(!diskOptional.isPresent())
            return;

        Disk diskToEdit = diskOptional.get();

        List<Track> tracks = core.getTracksRepository().findAll();

        List<Track> tracksToRemove = new ArrayList<>();
        List<Track> tracksToAdd = new ArrayList<>();

        if (tracksList.contains(",")){
            String[] indexes = tracksList.split("(,)+( )*");

            for (String index : indexes) {
                int audioIndex = Integer.parseInt(index);

                if (audioIndex < 0){
                    if ((-audioIndex)-1 < 0 ||(-audioIndex)-1 >= diskToEdit.getTracks().size()){
                        continue;
                    }
                    tracksToRemove.add(diskToEdit.getTracks().get(-audioIndex-1));
                }

                else {
                    if (audioIndex-1 < 0 || audioIndex-1 >= tracks.size()){
                        continue;
                    }
                    tracksToAdd.add(tracks.get(audioIndex-1));
                }
            }
        }

        else if (!tracksList.isEmpty()){
            int index = Integer.parseInt(tracksList);

            if (index < 0){
                diskToEdit.getTracks().remove(-index-1);
            }
            else{
                diskToEdit.getTracks().add(tracks.get(index-1));
            }
        }

        diskToEdit.getTracks().addAll(tracksToAdd);
        diskToEdit.getTracks().removeAll(tracksToRemove);

        if (!price.isEmpty()){
            diskToEdit.setPrice(Double.parseDouble(price));
        }

        core.getDisksRepository().save(diskToEdit);

        core.log(1, "Disk edited");
    }
}
