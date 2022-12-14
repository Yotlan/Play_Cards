package fr.playcards;

//Import part
import fr.playcards.cardgame.*;
import fr.playcards.client.Client;
import fr.playcards.client.IClient;
import fr.playcards.room.*;
import fr.playcards.server.IServer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

/*
@author Yotlan LE CROM

This is the controller of playcards-view.fxml file and PlayCardsApplication, PlayCardsApplicationLocal frame.
 */

public class PlayCardsController {
    @FXML
    public Label pseudo = new Label("Pseudo");
    @FXML
    public TextField playerPseudo = new TextField();
    @FXML
    private ChoiceBox cardGameChoiceBox;
    @FXML
    public ObservableList<IRoom> observableRoomList;
    @FXML
    public TableView<IRoom> roomTable = new TableView<IRoom>();
    @FXML
    public TableColumn<IRoom, String> roomColumn = new TableColumn<IRoom, String>("Room");
    @FXML
    public TableColumn<IRoom, String> cardGameColumn = new TableColumn<IRoom, String>("Card Game");
    @FXML
    public TableColumn<IRoom, String> nbPlayerColumn = new TableColumn<IRoom, String>("Nb Players");
    public IServer mainServer;
    public IClient mainClient;

    /*
    The constructor link this controller with the server and create one client per frame.
     */

    public PlayCardsController(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            mainServer = (IServer) registry.lookup("play-cards/1099/connecting");
            mainClient = new Client();
        } catch (Exception e) {
            System.out.println("PlayCardsController Constructor Error : "+e);
        }
    }

    /*
    This method initialize the data for the controller.
     */

    @FXML
    public void initialize() {
        //Set column value
        roomColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(data.getValue().getRoom());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        cardGameColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(data.getValue().getCardGame());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        nbPlayerColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(Integer.toString(data.getValue().getNbPlayer()) + "/" + Integer.toString(data.getValue().getCurrentCardGame().getMaxPlayer()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        //Add all the column to shared table
        roomTable.getColumns().clear();
        roomTable.getColumns().addAll(roomColumn, cardGameColumn, nbPlayerColumn);
        try {
            observableRoomList = FXCollections.observableArrayList(mainClient.getObservableRoomList());
        } catch(Exception e) {
            e.printStackTrace();
        }

        //Add join button to shared table
        addButtonToTable();

        //Add all the created room to the table
        roomTable.setItems(observableRoomList);
        roomTable.refresh();

        //Create a thread who called refresh method each 10 seconds
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                refresh();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /*
    This method create a pop-up to alert the user of something. This method was created thanks to this source :
    https://o7planning.org/11529/javafx-alert-dialog
     */

    private void showAlertWithoutHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("The card game room is already full or you don't set your pseudo !");
        alert.showAndWait();
    }

    /*
    This method add a join button to the shared table by create an empty column and add to each cell a callback behavior
    to click on the join button to do something. This method was created thanks to this source :
    https://riptutorial.com/javafx/example/27946/add-button-to-tableview
     */

    private void addButtonToTable() {
        //Create an empty column
        TableColumn<IRoom, Void> colBtn = new TableColumn("Button Column");
        Callback<TableColumn<IRoom, Void>, TableCell<IRoom, Void>> cellFactory = new Callback<TableColumn<IRoom, Void>, TableCell<IRoom, Void>>() {
            @Override
            public TableCell<IRoom, Void> call(final TableColumn<IRoom, Void> param) {

                //Create a cell with a specific behavior we decide
                final TableCell<IRoom, Void> cell = new TableCell<IRoom, Void>() {

                    //Create a join button with a specific action we decide
                    private final Button btn = new Button("Join");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            IRoom data = getTableView().getItems().get(getIndex());
                            Integer nbPlayer = 0;
                            Integer maxPlayer = 0;
                            String gameTitle = "";
                            try {
                                nbPlayer = data.getNbPlayer();
                                maxPlayer = data.getCurrentCardGame().getMaxPlayer();
                                gameTitle = data.getCurrentCardGame().getName();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }

                            //Verify if we can join the associated game
                            if(nbPlayer < maxPlayer
                                    && !playerPseudo.getText().isBlank()
                            ) {
                                try {
                                    data.connect(playerPseudo.getText());
                                    refresh();
                                    roomTable.refresh();

                                    //Set the client's pseudo with the pseudo set in the pseudo input field
                                    mainClient.setClientPseudo(playerPseudo.getText());
                                    try {

                                        //Launch the corresponding frame depending on the game choose in the choice box
                                        if(gameTitle.equals("Triple Triade - Final Fantasy 8")) {
                                            new FF8TripleTriadeFrame(gameTitle+" "+playerPseudo.getText(), data.getCurrentCardGame(), mainClient).start();
                                        } else if(gameTitle.equals("Triple Triade - Final Fantasy 14")) {
                                            new FF14TripleTriadeFrame(gameTitle+" "+playerPseudo.getText(), data.getCurrentCardGame(), mainClient).start();
                                        } else if(gameTitle.equals("Koi Koi Wars - Sakura Wars")) {
                                            new KoiKoiWarsFrame(gameTitle+" "+playerPseudo.getText(), data.getCurrentCardGame(), mainClient).start();
                                        }
                                    }
                                    catch (Exception e) {
                                        System.out.println("PlayCardsController addButtonToTable method Error : "+e);
                                        e.printStackTrace();
                                    }
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                showAlertWithoutHeaderText();
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        //Add button column to the shared table
        roomTable.getColumns().add(colBtn);
    }

    /*
    This method is the behavior of the create button. His goal is to create a room in the server, and then add it in
    the shared table to able all clients to see it.
     */

    @FXML
    public void onCreateButtonClick() {
        try {
            if(cardGameChoiceBox.getValue().toString().equals("FF8TripleTriade")) {
                String FF8TTUUID = UUID.randomUUID().toString();
                mainServer.createRoom(new FF8TripleTriade(FF8TTUUID),mainClient);
            } else if(cardGameChoiceBox.getValue().toString().equals("FF14TripleTriade")) {
                String FF14TTUUID = UUID.randomUUID().toString();
                mainServer.createRoom(new FF14TripleTriade(FF14TTUUID),mainClient);
            } else if(cardGameChoiceBox.getValue().toString().equals("KoiKoiWars")) {
                String KKWUUID = UUID.randomUUID().toString();
                mainServer.createRoom(new KoiKoiWars(KKWUUID),mainClient);
            }
            refresh();
        } catch (Exception e) {
            System.out.println("PlayCardsController onCreateButtonClick method Error : " + e);
        }
    }

    /*
    This method is called each 10 seconds and update all the room who's created by all users.
     */

    @FXML
    public void refresh() {
        try{
            observableRoomList = FXCollections.observableArrayList(mainClient.getObservableRoomList());
            roomTable.setItems(observableRoomList);
            roomTable.refresh();
        } catch(Exception e) {
            System.out.println("PlayCardsController refresh method Error : "+e);
        }
    }

}