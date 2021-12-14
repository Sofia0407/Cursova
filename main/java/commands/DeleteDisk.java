package commands;

import core.Core;
import entities.disks.Disk;

import java.util.List;

public class DeleteDisk implements Command{
    Core core;
    private int diskId = -1;

    public DeleteDisk(Core core, int diskId){
        this.core = core;
        this.diskId = diskId;
    }

    public void execute() {
        core.log(1, "Delete Disk command execute");

        List<Disk> disks = core.getDisksRepository().findAll();
        core.getDisksRepository().deleteDiskByName(disks.get(diskId).getName());
        core.log(1, "Disk " + diskId + " deleted");
    }
}
