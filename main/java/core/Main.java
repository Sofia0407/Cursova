package core;

import commands.*;

import entities.artists.Artist;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import tools.Tools;

import java.util.*;

public class Main extends Application {

    private BorderPane root;

    private HBox artistControlBox;
    private HBox diskControlBox;
    private HBox trackControlBox;

    private TextArea infoPanel;

    private static Core core;
    static Scanner inputReader = new Scanner(System.in);

    public static boolean main_menu(){
        Tools.clearScreen();
        int userChoice;

        System.out.println("\t-- MAIN MENU --\n");

        System.out.println("1. Artists");
        System.out.println("2. Tracks");
        System.out.println("3. Disks");
        System.out.println("\n4. Exit\n");

        while (true) {
            System.out.print("Choose command: ");
            userChoice = Integer.parseInt(inputReader.nextLine());

            if (userChoice == 4) {
                return false;
            }
            else if (userChoice > 4){
                System.out.println("Enter valid command (1-4)");
                continue;
            }

            break;
        }

        Tools.clearScreen();
        switch (userChoice) {
            case 1: {
                new ShowArtists(core, null).execute();
            }
            case 2: {
//                new ShowTracks(core, infoPanel).execute();
            }
            case 3: {
//                new ShowDisks(core).execute();
            }
            case 4: {
                return false;
            }
        }

        return true;
    }

    public void initArtistControlBox() {
        artistControlBox = new HBox();
        artistControlBox.setSpacing(20);
        artistControlBox.setAlignment(Pos.CENTER);
        artistControlBox.setPadding(new Insets(20, 20, 20, 20));
    }

    public void initDiskControlBox() {
        diskControlBox = new HBox();
        diskControlBox.setSpacing(20);
        diskControlBox.setAlignment(Pos.CENTER);
        diskControlBox.setPadding(new Insets(20, 20, 20, 20));
    }

    public void initTrackControlBox() {
        trackControlBox = new HBox();
        trackControlBox.setSpacing(20);
        trackControlBox.setAlignment(Pos.CENTER);
        trackControlBox.setPadding(new Insets(20, 20, 20, 20));
    }

    void initArtistsControls() {
        // 1. Init AddArtist
        Button addArtistButton = new Button("Add");

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add new artist");
        dialog.setHeaderText("Provide artist details");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField country = new TextField();
        country.setPromptText("Country");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Country:"), 0, 1);
        grid.add(country, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(name.getText(), country.getText());
            }
            return null;
        });

        addArtistButton.setOnAction(e -> {
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(nameCountry -> {
                new AddArtist(core, new Artist(result.get().getKey(), result.get().getValue())).execute();
            });
        });

        // 2. Show artists
        Button showArtistsButton = new Button("Artists");
        showArtistsButton.setOnAction(e -> {
            new ShowArtists(core, infoPanel).execute();
        });

        // 3. Edit Artist
        Dialog<Artist> editArtistDialog = new Dialog<>();
        editArtistDialog.setTitle("Edit artist");
        editArtistDialog.setHeaderText("Provide new artist details");

        // Set the button types.
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        editArtistDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane editGrid = new GridPane();
        editGrid.setHgap(10);
        editGrid.setVgap(10);
        editGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField editingArtistId = new TextField();
        editingArtistId.setPromptText("Artist Name");
        TextField editNameField = new TextField();
        editNameField.setPromptText("Name");
        TextField editCountryField = new TextField();
        editCountryField.setPromptText("Country");

        editGrid.add(new Label("Artist Name:"), 0, 0);
        editGrid.add(editingArtistId, 1, 0);
        editGrid.add(new Label("Name:"), 0, 1);
        editGrid.add(editNameField, 1, 1);
        editGrid.add(new Label("Country:"), 0, 2);
        editGrid.add(editCountryField, 1, 2);

        editArtistDialog.getDialogPane().setContent(editGrid);

        editArtistDialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                return new Artist(editNameField.getText(), editCountryField.getText());
            }
            return null;
        });

        Button editArtistButton = new Button("Edit");
        editArtistButton.setOnAction(e -> {
            Optional<Artist> artist = editArtistDialog.showAndWait();
            artist.ifPresent(value -> new EditArtist(core, editingArtistId.getText(), value).execute());
        });

        // 4. Delete Artist
        TextInputDialog deleteArtistDialog = new TextInputDialog();
        deleteArtistDialog.setGraphic(null);
        deleteArtistDialog.setContentText("Artist Id:");
        deleteArtistDialog.setHeaderText("Provide artist id to be deleted");
        deleteArtistDialog.setTitle("Delete artist");

        Button deleteArtistButton = new Button("Delete");
        deleteArtistButton.setOnAction(e -> {
            Optional<String> res = deleteArtistDialog.showAndWait();
            res.ifPresent(v -> new DeleteArtist(core, Integer.parseInt(v)-1).execute());
        });

        // 5. Sort artists
        ComboBox<String> artistSortFieldsBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Name",
                        "Country"
                )
        );

        ComboBox<String> artistSortOrderBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "asc",
                        "desc"
                )
        );

        Button sortArtistsButton = new Button("Sort");
        sortArtistsButton.setOnAction(e -> {
            int fieldIndex = artistSortFieldsBox.getSelectionModel().getSelectedIndex() + 1;
            int orderIndex = artistSortOrderBox.getSelectionModel().getSelectedIndex() + 1;
            System.out.println(fieldIndex + " " + orderIndex);
            new SortArtist(core, fieldIndex, orderIndex).execute();
        });

        artistControlBox.getChildren().addAll(
                addArtistButton,
                showArtistsButton,
                editArtistButton,
                deleteArtistButton,
                sortArtistsButton,
                artistSortFieldsBox,
                artistSortOrderBox
        );

    }

    void initDiskControls() {
        // 1. Add disk
        Button addDiskButton = new Button("Add");

        Dialog<Map<String, Object>> addDiskDialog = new Dialog<>();
        addDiskDialog.setTitle("Add disk");
        addDiskDialog.setHeaderText("Provide disk details");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        addDiskDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane addDiskGrid = new GridPane();
        addDiskGrid.setHgap(10);
        addDiskGrid.setVgap(10);
        addDiskGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField addTracksField = new TextField();
        addTracksField.setPromptText("Tracks");
        TextField addNameField = new TextField();
        addNameField.setPromptText("Name");
        TextField addPriceField = new TextField();
        addPriceField.setPromptText("Price");

        addDiskGrid.add(new Label("Name:"), 0, 0);
        addDiskGrid.add(addNameField, 1, 0);
        addDiskGrid.add(new Label("Price:"), 0, 1);
        addDiskGrid.add(addPriceField, 1, 1);
        addDiskGrid.add(new Label("Tracks:"), 0, 2);
        addDiskGrid.add(addTracksField, 1, 2);

        addDiskDialog.getDialogPane().setContent(addDiskGrid);

        addDiskDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("name", addNameField.getText());
                paramsMap.put("price", Double.parseDouble(addPriceField.getText()));
                paramsMap.put("tracks", addTracksField.getText());
                return paramsMap;
            }
            return null;
        });

        addDiskButton.setOnAction(e -> {
            Optional<Map<String, Object>> paramsMapOptional = addDiskDialog.showAndWait();
            if(paramsMapOptional.isPresent()) {
                Map<String, Object> paramsMap = paramsMapOptional.get();
                new AddDisk(core, paramsMap).execute();
            }
        });

        // 2. Show Disks
        Button showDisksButton = new Button("Disks");
        showDisksButton.setOnAction(e -> new ShowDisks(core, infoPanel).execute());

        // 3. Delete Disk
        TextInputDialog deleteDiskDialog = new TextInputDialog();
        deleteDiskDialog.setGraphic(null);
        deleteDiskDialog.setContentText("Disk Id:");
        deleteDiskDialog.setHeaderText("Provide disk id to be deleted");
        deleteDiskDialog.setTitle("Delete disk");

        Button deleteDiskButton = new Button("Delete");
        deleteDiskButton.setOnAction(e -> {
            Optional<String> res = deleteDiskDialog.showAndWait();
            res.ifPresent(v -> new DeleteDisk(core, Integer.parseInt(v)-1).execute());
        });

        // 4. Edit Disk
        Button editDiskButton = new Button("Edit");

        Dialog<Map<String, Object>> editDiskDialog = new Dialog<>();
        editDiskDialog.setTitle("Edit disk");
        editDiskDialog.setHeaderText("Provide disk details");

        // Set the button types.
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        editDiskDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane editDiskGrid = new GridPane();
        editDiskGrid.setHgap(10);
        editDiskGrid.setVgap(10);
        editDiskGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField editingDiskId = new TextField();
        editingDiskId.setPromptText("Disk Name");
        TextField editTracksField = new TextField();
        editTracksField.setPromptText("Tracks");
        TextField editPriceField = new TextField();
        editPriceField.setPromptText("Price");

        editDiskGrid.add(new Label("Disk Name"), 0, 0);
        editDiskGrid.add(editingDiskId, 1, 0);
        editDiskGrid.add(new Label("Price:"), 0, 1);
        editDiskGrid.add(editPriceField, 1, 1);
        editDiskGrid.add(new Label("Tracks:"), 0, 2);
        editDiskGrid.add(editTracksField, 1, 2);

        editDiskDialog.getDialogPane().setContent(editDiskGrid);

        editDiskDialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("id", editingDiskId.getText());
                paramsMap.put("price", editPriceField.getText());
                paramsMap.put("tracks", editTracksField.getText());
                return paramsMap;
            }
            return null;
        });

        editDiskButton.setOnAction(e -> {
            Optional<Map<String, Object>> paramsMapOptional = editDiskDialog.showAndWait();
            if(paramsMapOptional.isPresent()) {
                Map<String, Object> paramsMap = paramsMapOptional.get();
                new EditDisk(core, paramsMap).execute();
            }
        });

        // 5. Sort Disks
        Button sortDisksButton = new Button("Sort");

        TextField diskChoiceField = new TextField();
        diskChoiceField.setPromptText("Disk id");
        diskChoiceField.setMaxWidth(50);

        ComboBox<String> diskSortFieldsBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "[Disk] Name",
                        "[Disk] Price",
                        "[Track] Name",
                        "[Track] Artist",
                        "[Track] Length",
                        "[Track] Genre"
                )
        );

        ComboBox<String> diskSortOrderBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "asc",
                        "desc"
                )
        );

        sortDisksButton.setOnAction(e -> {
            int fieldIndex = diskSortFieldsBox.getSelectionModel().getSelectedIndex() + 1;
            int orderIndex = diskSortOrderBox.getSelectionModel().getSelectedIndex() + 1;
            int diskId = diskChoiceField.getText().isEmpty() ? -1 : Integer.parseInt(diskChoiceField.getText());
            new SortDisk(core, fieldIndex, orderIndex, diskId-1).execute();
        });

        diskControlBox.getChildren().addAll(
                addDiskButton,
                showDisksButton,
                deleteDiskButton,
                editDiskButton,
                sortDisksButton,
                diskChoiceField,
                diskSortFieldsBox,
                diskSortOrderBox
        );

    }

    void initTrackControls() {
        // 1. Add Track
        Button addTrackButton = new Button("Add");

        Dialog<Map<String, Object>> addTrackDialog = new Dialog<>();
        addTrackDialog.setTitle("Add track");
        addTrackDialog.setHeaderText("Provide track details");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        addTrackDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane addTrackGrid = new GridPane();
        addTrackGrid.setHgap(10);
        addTrackGrid.setVgap(10);
        addTrackGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField addTrackName = new TextField();
        addTrackName.setPromptText("Name");
        TextField addTrackArtistId = new TextField();
        addTrackArtistId.setPromptText("Artist Id");
        TextField addTrackLength = new TextField();
        addTrackLength.setPromptText("Length");
        TextField addTrackGenre = new TextField();
        addTrackGenre.setPromptText("Genre Id");

        addTrackGrid.add(new Label("Name:"), 0, 0);
        addTrackGrid.add(addTrackName, 1, 0);
        addTrackGrid.add(new Label("Artist Id:"), 0, 1);
        addTrackGrid.add(addTrackArtistId, 1, 1);
        addTrackGrid.add(new Label("Length:"), 0, 2);
        addTrackGrid.add(addTrackLength, 1, 2);
        addTrackGrid.add(new Label("Genre Id:"), 0, 3);
        addTrackGrid.add(addTrackGenre, 1, 3);

        addTrackDialog.getDialogPane().setContent(addTrackGrid);

        addTrackDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("name", addTrackName.getText());
                paramsMap.put("artistId", Integer.parseInt(addTrackArtistId.getText())-1);
                paramsMap.put("len", Integer.parseInt(addTrackLength.getText()));
                paramsMap.put("genreId", Integer.parseInt(addTrackGenre.getText())-1);
                return paramsMap;
            }
            return null;
        });

        addTrackButton.setOnAction(e -> {
            Optional<Map<String, Object>> paramsMapOptional = addTrackDialog.showAndWait();
            if(paramsMapOptional.isPresent()) {
                Map<String, Object> paramsMap = paramsMapOptional.get();
                new AddTrack(core, paramsMap).execute();
            }
        });

        // 2. Show Tracks
        Button showTracksButton = new Button("Tracks");
        showTracksButton.setOnAction(e -> {
            new ShowTracks(core, infoPanel).execute();
        });

        // 3. Delete Track
        TextInputDialog deleteTrackDialog = new TextInputDialog();
        deleteTrackDialog.setGraphic(null);
        deleteTrackDialog.setContentText("Track Id:");
        deleteTrackDialog.setHeaderText("Provide track id to be deleted");
        deleteTrackDialog.setTitle("Delete track");

        Button deleteTrackButton = new Button("Delete");
        deleteTrackButton.setOnAction(e -> {
            Optional<String> res = deleteTrackDialog.showAndWait();
            res.ifPresent(v -> new DeleteTrack(core, Integer.parseInt(v)-1).execute());
        });

        // 4. Edit Track
        Button editTrackButton = new Button("Edit");

        Dialog<Map<String, Object>> editTrackDialog = new Dialog<>();
        editTrackDialog.setTitle("Edit track");
        editTrackDialog.setHeaderText("Provide track details");

        // Set the button types.
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        editTrackDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane editTrackGrid = new GridPane();
        editTrackGrid.setHgap(10);
        editTrackGrid.setVgap(10);
        editTrackGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField editTrackId = new TextField();
        editTrackId.setPromptText("Track Name");
        TextField editArtistField = new TextField();
        editArtistField.setPromptText("Artist Name");
        TextField editLengthField = new TextField();
        editLengthField.setPromptText("Length");
        TextField editGenreField = new TextField();
        editGenreField.setPromptText("Genre");

        editTrackGrid.add(new Label("Track Name:"), 0, 0);
        editTrackGrid.add(editTrackId, 1, 0);
        editTrackGrid.add(new Label("Artist Name:"), 0, 1);
        editTrackGrid.add(editArtistField, 1, 1);
        editTrackGrid.add(new Label("Length:"), 0, 2);
        editTrackGrid.add(editLengthField, 1, 2);
        editTrackGrid.add(new Label("Genre Id:"), 0, 3);
        editTrackGrid.add(editGenreField, 1, 3);

        editTrackDialog.getDialogPane().setContent(editTrackGrid);

        editTrackDialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("id", editTrackId.getText());
                paramsMap.put("artistId", editArtistField.getText());
                paramsMap.put("len", editLengthField.getText());
                paramsMap.put("genreId", editGenreField.getText());
                return paramsMap;
            }
            return null;
        });

        editTrackButton.setOnAction(e -> {
            Optional<Map<String, Object>> paramsMapOptional = editTrackDialog.showAndWait();
            if(paramsMapOptional.isPresent()) {
                Map<String, Object> paramsMap = paramsMapOptional.get();
                new EditTrack(core, paramsMap).execute();
            }
        });

        // 5. Sort tracks
        Button sortTracksButton = new Button("Sort");

        ComboBox<String> trackSortFieldBox = new ComboBox<>(
                FXCollections.observableArrayList(
                       "Name",
                        "Artist",
                        "Length",
                        "Genre"
                )
        );

        ComboBox<String> trackSortOrderBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "asc",
                        "desc"
                )
        );

        sortTracksButton.setOnAction(e -> {
            int fieldIndex = trackSortFieldBox.getSelectionModel().getSelectedIndex() + 1;
            int orderIndex = trackSortOrderBox.getSelectionModel().getSelectedIndex() + 1;
            new SortTrack(core, fieldIndex, orderIndex).execute();
        });

        trackControlBox.getChildren().addAll(
                addTrackButton,
                showTracksButton,
                deleteTrackButton,
                editTrackButton,
                sortTracksButton,
                trackSortFieldBox,
                trackSortOrderBox
        );
    }

    @Override
    public void init() throws Exception {
        root = new BorderPane();

        VBox infoBox = new VBox();
        infoBox.setSpacing(20);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(0, 20, 0, 20));

        Text infoPanelCaption = new Text("Information panel");

        infoPanel = new TextArea();
        infoPanel.setEditable(false);
        infoPanel.setFocusTraversable(false);
        infoPanel.setMinHeight(300);

        infoBox.getChildren().addAll(
                infoPanelCaption,
                infoPanel
        );

        root.setCenter(infoBox);

        // Init control boxes
        initArtistControlBox();
        initDiskControlBox();
        initTrackControlBox();

        // Root bottom panels switchers
        Button showArtistControlBox = new Button("Artists");
        showArtistControlBox.setOnAction(e -> root.setBottom(artistControlBox));

        Button showDiskControlBox = new Button("Disks");
        showDiskControlBox.setOnAction(e -> root.setBottom(diskControlBox));

        Button showTrackControlBox = new Button("Tracks");
        showTrackControlBox.setOnAction(e -> root.setBottom(trackControlBox));

        Button clearInfoPanel = new Button("Clear Panel");
        clearInfoPanel.setStyle("-fx-background-color: LIGHTBLUE");
        clearInfoPanel.setOnAction(e -> infoPanel.setText(""));

        // Init switchers holder
        HBox sectionSwitchersBox = new HBox();
        sectionSwitchersBox.setSpacing(25);
        sectionSwitchersBox.setAlignment(Pos.CENTER);
        sectionSwitchersBox.setPadding(new Insets(20, 0, 0, 0));
//        sectionSwitchersBox.setMaxHeight(50);

        sectionSwitchersBox.getChildren().addAll(
                showArtistControlBox,
                showDiskControlBox,
                showTrackControlBox,
                clearInfoPanel
        );

        root.setTop(sectionSwitchersBox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("Курсова робота студентки КН-201 Сарахман Софії");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Init controls in application thread
        initArtistsControls();
        initDiskControls();
        initTrackControls();

        core = new Core(infoPanel);
    }

    public static void main(String[] args) {

//        core.getArtists().addAll(Tools.readArtistsFromFile("artists.txt"));
//        core.getTracks().addAll(Tools.readTracksFromFile("tracks.txt", core.getArtists()));
//        core.getDisks().addAll(Tools.readDisksFromFile("disks.txt", core.getTracks()));

        launch(args);
    }
}