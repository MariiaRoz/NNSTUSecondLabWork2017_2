package org.nnstu.launcher;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.nnstu.launcher.util.RunnableServerInstance;
import org.nnstu.launcher.util.ServerId;
import org.nnstu.launcher.util.ServerStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Small UI for launching servers
 *
 * @author Roman Khlebnov
 */
public class MainLauncherUI extends Application {
    private static final String PACKAGE_NAME = "org";
    private static final String FIRST_COLUMN_ID = "Server";
    private static final String SECOND_COLUMN_ID = "Status";
    private static final String STAGE_TITLE = "Server Manager";

    private ServerLookupService lookupService;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    public void start(Stage primaryStage) throws Exception {
        // Data preparation
        lookupService = new ServerLookupService(PACKAGE_NAME);
        final ObservableList<ServerDataModel> serverIdsAndStates = FXCollections.observableArrayList();

        Collection<RunnableServerInstance> servers = lookupService.getAllServerInstances();

        for (RunnableServerInstance instance : servers) {
            serverIdsAndStates.add(new ServerDataModel(instance.getServerId(), ServerStatus.STOPPED));
        }

        // Actual stage rendering
        Scene managerScene = new Scene(new Group(), 620, 455);

        final TableView<ServerDataModel> tableView = prepareTableView(serverIdsAndStates);

        // Formatting section
        final HBox buttonsPane = new HBox();
        buttonsPane.getChildren().addAll(prepareButtons(tableView, serverIdsAndStates));

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableView, buttonsPane);

        ((Group) managerScene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setTitle(STAGE_TITLE);
        primaryStage.setScene(managerScene);
        primaryStage.show();
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     * <p>
     * <p>
     * The implementation of this method provided by the Application class does nothing.
     * </p>
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        lookupService.stopExecution();
    }

    /**
     * This method is used to prepare table with data
     *
     * @param data {@link ObservableList} to be shown on table
     * @return prepared {@link TableView}
     */
    private TableView<ServerDataModel> prepareTableView(ObservableList<ServerDataModel> data) {
        if (data == null || data.isEmpty()) {
            return new TableView<>();
        }

        final TableView<ServerDataModel> serversTable = new TableView<>(data);
        serversTable.setEditable(false);

        final TableColumn<ServerDataModel, String> serverInfoColumn = new TableColumn<>(FIRST_COLUMN_ID);
        serverInfoColumn.setMinWidth(400.0);
        serverInfoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));
        final TableColumn<ServerDataModel, String> serverStatusColumn = new TableColumn<>(SECOND_COLUMN_ID);
        serverStatusColumn.setMinWidth(200.0);
        serverStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        serverStatusColumn.setCellFactory(param -> {
            TableCell cell = new TableCell<ServerDataModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : getString());
                    setGraphic(null);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem();
                }
            };

            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        serversTable.getColumns().setAll(Arrays.asList(serverInfoColumn, serverStatusColumn));

        return serversTable;
    }

    /**
     * Method to prepare form buttons
     *
     * @param serversTable {@link TableView} to store and check server statuses
     * @param data         {@link ObservableList} with servers data
     * @return {@link List} of {@link Button} to control servers
     */
    private List<Button> prepareButtons(TableView<ServerDataModel> serversTable, ObservableList<ServerDataModel> data) {
        final Button launchAll = new Button("Launch All Servers");
        launchAll.setMinSize(200.0, 30.0);
        final Button launchSelected = new Button("Launch Selected Server");
        launchSelected.setMinSize(200.0, 30.0);
        final Button stopAll = new Button("Stop All Servers");
        stopAll.setMinSize(200.0, 30.0);

        launchAll.setOnAction(event -> {
            if (!lookupService.isLaunchingLocked()) {
                lookupService.simultaneousLaunch();

                for (ServerDataModel value : data) {
                    value.setStatus(ServerStatus.LAUNCHED);
                }

                launchAll.setDisable(lookupService.isLaunchingLocked());
                launchSelected.setDisable(lookupService.isLaunchingLocked());
            }
        });

        launchSelected.setOnAction(event -> {
            if (!lookupService.isLaunchingLocked()) {
                ServerDataModel selectedServer = serversTable.getSelectionModel().getSelectedItem();

                if (selectedServer != null) {
                    lookupService.launchSingleInstance(selectedServer.getServerId());
                    selectedServer.setStatus(ServerStatus.LAUNCHED);
                }

                launchAll.setDisable(lookupService.isLaunchingLocked());
                launchSelected.setDisable(lookupService.isLaunchingLocked());
            }
        });

        stopAll.setOnAction(event -> {
            lookupService.stopExecution();

            for (ServerDataModel value : data) {
                value.setStatus(ServerStatus.STOPPED);
            }

            launchAll.setDisable(lookupService.isLaunchingLocked());
            launchSelected.setDisable(lookupService.isLaunchingLocked());
        });

        return Arrays.asList(launchAll, launchSelected, stopAll);
    }

    /**
     * {@link ObservableList} information data model
     *
     * @author Roman Khlebnov
     */
    public static class ServerDataModel {
        private final ServerId serverId;
        private final SimpleStringProperty info;
        private final SimpleStringProperty status;

        ServerDataModel(ServerId info, ServerStatus status) {
            this.serverId = info;
            this.info = new SimpleStringProperty(info.toString());
            this.status = new SimpleStringProperty(status.getValue());
        }

        public ServerId getServerId() {
            return serverId;
        }

        public String getInfo() {
            return info.get();
        }

        public SimpleStringProperty infoProperty() {
            return info;
        }

        public void setInfo(ServerId info) {
            this.info.set(info.toString());
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(ServerStatus status) {
            this.status.set(status.getValue());
        }
    }
}
