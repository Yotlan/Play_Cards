package fr.playcards;

import fr.playcards.cardgame.*;
import fr.playcards.room.*;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class PlayCardsController {

    @FXML
    public ObservableList<IRoom> observableRoomList = FXCollections.observableArrayList();
    @FXML
    public TableView<IRoom> roomTable = new TableView<IRoom>();
    @FXML
    public TableColumn<IRoom, String> roomColumn = new TableColumn<IRoom, String>("Room");
    @FXML
    public TableColumn<IRoom, String> cardGameColumn = new TableColumn<IRoom, String>("Card Game");
    @FXML
    public TableColumn<IRoom, String> nbPlayerColumn = new TableColumn<IRoom, String>("Nb Players");

    @FXML
    public void initialize() {
        roomColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(data.getValue().getRoom());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        cardGameColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(data.getValue().getCardgame());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        nbPlayerColumn.setCellValueFactory(data -> {
            try {
                return new ReadOnlyStringWrapper(Integer.toString(data.getValue().getNbplayer()) + "/" + Integer.toString(data.getValue().getCurrentCardGame().getMaxPlayer()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        roomTable.getColumns().clear();
        roomTable.getColumns().addAll(roomColumn, cardGameColumn, nbPlayerColumn);

        addButtonToTable();
    }

    //SRC : https://o7planning.org/11529/javafx-alert-dialog
    private void showAlertWithoutHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Too many player");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("The card game room is already full !");

        alert.showAndWait();
    }

    //SRC : https://riptutorial.com/javafx/example/27946/add-button-to-tableview
    private void addButtonToTable() {
        TableColumn<IRoom, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<IRoom, Void>, TableCell<IRoom, Void>> cellFactory = new Callback<TableColumn<IRoom, Void>, TableCell<IRoom, Void>>() {
            @Override
            public TableCell<IRoom, Void> call(final TableColumn<IRoom, Void> param) {
                final TableCell<IRoom, Void> cell = new TableCell<IRoom, Void>() {

                    private final Button btn = new Button("Join");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            IRoom data = getTableView().getItems().get(getIndex());
                            Integer nbPlayer = 0;
                            Integer maxPlayer = 0;
                            try {
                                nbPlayer = data.getNbplayer();
                                maxPlayer = data.getCurrentCardGame().getMaxPlayer();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            if(nbPlayer < maxPlayer) {
                                try {
                                    data.connect("Test");
                                    roomTable.refresh();
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

        roomTable.getColumns().add(colBtn);

    }

    @FXML
    public void onCreateButtonClick() {
        try {
            IRoom room =
                    (IRoom) Naming.lookup(
                            "play-cards/1099/createroom");
            observableRoomList.add(room);
            roomTable.setItems(observableRoomList);
            roomTable.refresh();
        } catch (Exception e) {
            System.out.println("erreur" + e);
        }
    }

}