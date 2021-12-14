package commands;

import core.Core;
import entities.disks.Disk;
import javafx.scene.control.TextArea;

import java.util.List;

public class ShowDisks {
    Core core;
    private TextArea informationPanel;

    public ShowDisks(Core core, TextArea informationPanel){
        this.core = core;
        this.informationPanel = informationPanel;
    }

    public void execute(){
        core.log(1, "Command ShowDisks executed");

        StringBuilder sb = new StringBuilder();
        List<Disk> disks = core.getDisksRepository().findAll();
        for (int i = 0; i < disks.size(); i++){
            sb.append(String.format("\t\t%3d.", i+1)).append(disks.get(i).toString()).append(System.lineSeparator());
        }

        informationPanel.setText(sb.toString());
    }
}
