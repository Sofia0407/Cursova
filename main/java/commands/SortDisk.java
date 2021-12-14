package commands;

import comparators.DiskComparator;
import comparators.TrackComparator;
import core.Core;
import entities.disks.Disk;

import java.util.List;

public class SortDisk implements Command{
    Core core;
    private int fieldIndex;
    private int orderIndex;
    private int diskChoise;

    public SortDisk(Core core, int fieldIndex, int orderIndex, int diskChoice){
        this.core = core;
        this.fieldIndex = fieldIndex;
        this.orderIndex = orderIndex;
        this.diskChoise = diskChoice;
    }

    public void execute(){
        core.log(1, "Command SortDisk executed");

        if (fieldIndex >= 3 && diskChoise < 0)
            return;

        boolean asc = orderIndex == 1;
        List<Disk> disks = core.getDisksRepository().findAll();

        switch (fieldIndex){
            case 1: {
                disks.sort(new DiskComparator("name", asc));
                break;
            }

            case 2: {
                disks.sort(new DiskComparator("price", asc));
                break;
            }

            case 3: {
                disks.get(diskChoise).getTracks().sort(new TrackComparator("name", asc));
                break;
            }
            case 4: {
                disks.get(diskChoise).getTracks().sort(new TrackComparator("artist", asc));
                break;
            }
            case 5: {
                disks.get(diskChoise).getTracks().sort(new TrackComparator("length", asc));
                break;
            }
            case 6: {
                disks.get(diskChoise).getTracks().sort(new TrackComparator("genre", asc));
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < disks.size(); i++){
            sb.append(String.format("\t\t%3d.", i+1)).append(disks.get(i).toString()).append(System.lineSeparator());
        }

        core.getInfoPanel().setText(sb.toString());
    }
}

