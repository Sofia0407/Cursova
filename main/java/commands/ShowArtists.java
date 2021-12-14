package commands;

import core.Core;
import entities.artists.Artist;
import javafx.scene.control.TextArea;

import java.util.List;

public class ShowArtists implements Command{
    private Core core;
    private TextArea informationPanel;

    public ShowArtists(Core core, TextArea informationPanel){
        this.core = core;
        this.informationPanel = informationPanel;
    }

    public void execute(){
        core.log(1, "Command ShowArtists executed");

        StringBuilder sb = new StringBuilder();
        List<Artist> artists = core.getArtistRepository().findAll();
        for (int i = 0; i < artists.size(); i++){
            sb.append(String.format("%3d.", i+1)).append(artists.get(i).toString()).append(System.lineSeparator());
        }

        informationPanel.setText(sb.toString());
    }
}
