package org.nnstu.launcher;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.nnstu.launcher.util.RunnableServerInstance;
import org.nnstu.launcher.util.ServerId;
import org.nnstu.launcher.util.ServerStatus;

import java.util.Collection;

/**
 * Small UI for launching servers
 *
 * @author Roman Khlebnov
 */
public class MainLauncherUI extends Application {
    private static final String PACKAGE_NAME = "org";
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
        final String firstColumnId = "Server";
        final String secondColumnId = "Status";
        final ObservableList<ObservableServerPOJO> serverIdsAndStates = FXCollections.observableArrayList();

        Collection<RunnableServerInstance> servers = lookupService.getAllServerInstances();

        for (RunnableServerInstance instance : servers) {
            serverIdsAndStates.add(new ObservableServerPOJO(instance.getServerId(), ServerStatus.STOPPED));
        }

        // Actual stage rendering
        Scene managerScene = new Scene(new Group(), 620, 455);

        final TableView<ObservableServerPOJO> serversTable = new TableView<ObservableServerPOJO>(serverIdsAndStates);
        serversTable.setEditable(false);

        final TableColumn<ObservableServerPOJO, String> serverInfoColumn = new TableColumn<ObservableServerPOJO, String>(firstColumnId);
        serverInfoColumn.setMinWidth(400.0);
        serverInfoColumn.setCellValueFactory(new PropertyValueFactory<ObservableServerPOJO, String>("info"));
        final TableColumn<ObservableServerPOJO, String> serverStatusColumn = new TableColumn<ObservableServerPOJO, String>(secondColumnId);
        serverStatusColumn.setMinWidth(200.0);
        serverStatusColumn.setCellValueFactory(new PropertyValueFactory<ObservableServerPOJO, String>("status"));

        serversTable.getColumns().setAll(serverInfoColumn, serverStatusColumn);

        // Buttons section
        final Button launchAll = new Button("Launch All Servers");
        launchAll.setMinSize(200.0, 30.0);
        final Button launchSelected = new Button("Launch Selected Server");
        launchSelected.setMinSize(200.0, 30.0);
        final Button stopAll = new Button("Stop All Servers");
        stopAll.setMinSize(200.0, 30.0);

        launchAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (!lookupService.isLaunchingLocked()) {
                    lookupService.simultaneousLaunch();

                    for (ObservableServerPOJO value : serverIdsAndStates) {
                        value.setStatus(ServerStatus.LAUNCHED);
                    }

                    setLaunchButtonsState(lookupService.isLaunchingLocked(), launchAll, launchSelected);
                }
            }
        });

        launchSelected.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (!lookupService.isLaunchingLocked()) {
                    ObservableServerPOJO selectedServer = serversTable.getSelectionModel().getSelectedItem();

                    if (selectedServer != null) {
                        lookupService.launchSingleInstance(selectedServer.getServerId());
                        selectedServer.setStatus(ServerStatus.LAUNCHED);
                    }

                    setLaunchButtonsState(lookupService.isLaunchingLocked(), launchAll, launchSelected);
                }
            }
        });

        stopAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                lookupService.stopExecution();

                for (ObservableServerPOJO value : serverIdsAndStates) {
                    value.setStatus(ServerStatus.STOPPED);
                }

                setLaunchButtonsState(lookupService.isLaunchingLocked(), launchAll, launchSelected);
            }
        });

        // Formatting section
        final HBox buttonsPane = new HBox();
        buttonsPane.getChildren().addAll(launchAll, launchSelected, stopAll);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(serversTable, buttonsPane);

        ((Group) managerScene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setTitle("Server Manager");
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
     * Utility method to explicitly block user input
     *
     * @param buttons {@link Button} to be blocked
     */
    private void setLaunchButtonsState(boolean state, Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(state);
        }
    }

    /**
     * Observable server information structure
     *
     * @author Roman Khlebnov
     */
    public static class ObservableServerPOJO {
        private final ServerId serverId;
        private final SimpleStringProperty info;
        private final SimpleStringProperty status;

        public ObservableServerPOJO(ServerId info, ServerStatus status) {
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
