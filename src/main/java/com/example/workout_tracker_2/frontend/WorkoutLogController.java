package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.model.WorkoutLogNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.math.BigDecimal;

public class WorkoutLogController {

    @FXML
    private TreeTableView<WorkoutLogNode> workoutLogTreeTable;

    @FXML
    private TreeTableColumn<WorkoutLogNode, String> nameColumn;

    @FXML
    private TreeTableColumn<WorkoutLogNode, String> dateColumn;

    @FXML
    private TreeTableColumn<WorkoutLogNode, String> valueColumn;

    @FXML
    private TreeTableColumn<WorkoutLogNode, Integer> repsColumn;

    @FXML
    private TreeTableColumn<WorkoutLogNode, Integer> setsColumn;

    @FXML
    private TreeTableColumn<WorkoutLogNode, BigDecimal> weightColumn;

    @FXML
    public void initialize() {
        // Set up cell factories
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().valueProperty());
        repsColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().repsProperty().asObject());
        setsColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().setsProperty().asObject());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().weightProperty());

        // Create root node
        TreeItem<WorkoutLogNode> root = new TreeItem<>(new WorkoutLogNode("Workout Logs", null, null, null, null, null));
        root.setExpanded(true);

        // Set the root to the TreeTableView
        workoutLogTreeTable.setRoot(root);
        workoutLogTreeTable.setShowRoot(false);
    }

    @FXML
    public void goToMain() throws Exception {
        WorkoutApp.showMainFrame();
    }
}