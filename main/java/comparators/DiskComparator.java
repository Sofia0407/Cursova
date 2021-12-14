package comparators;

import entities.disks.Disk;

import java.util.Comparator;

public class DiskComparator implements Comparator<Disk> {
    String field;
    boolean asc;

    public DiskComparator(String field, boolean asc){
        this.field = field;
        this.asc = asc;
    }

    @Override
    public int compare(Disk o1, Disk o2) {
        if (field.equals("name")){
            if (asc){
                return o1.getName().compareTo(o2.getName()) > 0 ? 1 : -1;
            }
            else{
                return o1.getName().compareTo(o2.getName()) > 0 ? -1 : 1;
            }
        }

        else {
            if (asc){
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            }
            else{
                return o1.getPrice() > o2.getPrice() ? -1 : 1;
            }
        }
    }
}
