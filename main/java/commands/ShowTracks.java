package commands;

import core.Core;
import entities.tracks.Track;
import javafx.scene.control.TextArea;

import java.util.List;

public class ShowTracks implements Command{
    Core core;
    private TextArea infoPanel;

    public ShowTracks(Core core, TextArea infoPanel){
        this.core = core;
        this.infoPanel = infoPanel;
    }

    public void execute(){
        core.log(1, "Command ShowTracks executed");

        StringBuilder sb = new StringBuilder();
        List<Track> tracks = core.getTracksRepository().findAll();
        for (int i = 0; i < tracks.size(); i++){
            sb.append(String.format("%3d.", i+1)).append(tracks.get(i).toString()).append(System.lineSeparator());
        }

        infoPanel.setText(sb.toString());
    }
}
