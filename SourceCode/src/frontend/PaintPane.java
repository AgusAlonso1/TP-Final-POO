package frontend;

import backend.ButtonType;
import backend.CanvasAction;
import backend.CanvasState;
import backend.CanvasVersions;
import backend.model.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd CanvasState -------------------------------------------------------------------------
	private CanvasState canvasState;

	//Canvas versions -------------------------------------------------------------------------
	private CanvasVersions canvasVersions = new CanvasVersions();

	// Canvas and related -------------------------------------------------------------------------
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private int DEFAULT_LINE_WIDTH = 1;
	private int BUTTON_MIN_WIDTH = 90;

	// Default value of fill color, line color and line width -------------------------------------------------------------------------
	private static final Color DEFAULT_LINE_COLOUR = Color.BLACK;
	private static final Color DEFAULT_FILL_COLOUR = Color.YELLOW;
	private static final String SELECTED_LINE_COLOR_HX = Color.RED.toString();
	private static final double LINE_WIDTH = 1.0;

	// Current format of shapes to draw -------------------------------------------------------------------------
	private Format currentFormat = new Format(DEFAULT_LINE_COLOUR.toString(), DEFAULT_FILL_COLOUR.toString(), LINE_WIDTH );

	// Side Menu Buttons for drawing, selecting and deleting -------------------------------------------------------------------------
	private final EspecifiedToggleButton selectionButton = new EspecifiedToggleButton("Seleccionar", ButtonType.MISC);
	private final EspecifiedToggleButton rectangleButton = new EspecifiedToggleButton("Rectángulo", ButtonType.RECTANGLE);
	private final EspecifiedToggleButton circleButton = new EspecifiedToggleButton("Círculo", ButtonType.CIRCLE);
	private final EspecifiedToggleButton squareButton = new EspecifiedToggleButton("Cuadrado", ButtonType.SQUARE);
	private final EspecifiedToggleButton ellipseButton = new EspecifiedToggleButton("Elipse", ButtonType.ELLIPSE);
	private final EspecifiedToggleButton deleteButton = new EspecifiedToggleButton("Borrar", ButtonType.MISC);

	// Selected figure -------------------------------------------------------------------------
	private FormatFigure selectedFigure;

	// Format Outline Constants -------------------------------------------------------------------------
	private final static double MIN_OUTLINE_WIDTH = 1.0;
	private final static double MAX_OUTLINE_WIDTH = 50.0;
	private final static double DEFAULT_OUTLINE_WIDTH = 25.0;

	// Format Outline Buttons -------------------------------------------------------------------------
	private final Label outline = new Label("Borde");
	private final Slider outlineSlider = new Slider(MIN_OUTLINE_WIDTH, MAX_OUTLINE_WIDTH, DEFAULT_OUTLINE_WIDTH);
	private final ColorPicker outlinePicker = new ColorPicker(DEFAULT_LINE_COLOUR);

	// Format Color Fill Button  -------------------------------------------------------------------------
	private final Label fill = new Label("Relleno");
	private final ColorPicker fillPicker = new ColorPicker(DEFAULT_FILL_COLOUR);

	//Copy button ----------------------------------------------------------------------------
	private final ToggleButton copyFormatButton = new ToggleButton("Cop. form");

	//Layers Choice Box -------------------------------------------------------------------------
	private final LayerSelector layerSelector = new LayerSelector();
	private ChoiceBox<String> layersChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(layerSelector.layersName()));

	// Layer in which user is working on -------------------------------------------------------------------------
	private String selectedLayer;

	// Layers that are selected -------------------------------------------------------------------------
	private List<String> selectedLayers = layerSelector.layersName();

	// Undo and Redo Button -------------------------------------------------------------------------------------
	private ActionMenu undoAndRedo;

	private Iterable<FormatFigure> currentFigures;

	//Tag assignment area ---------------------------------------------------------------------------------------
	private int DEFAULT_ROW_HEIGHT = 4;
	private TextArea tagTextArea = new TextArea();
	private Button saveTagButton = new Button("Guardar");
	private String activeTag = null;

	//Tags bar ----------------------------------------------------------------------------------------
	private TagsBar tagsBar;

	// Start point for a figure to draw -------------------------------------------------------------------------
	private Point startPoint;

	// StatusBar -------------------------------------------------------------------------
	private StatusPane statusPane;

	private Format copiedFormat = null;

	//Current toggled button, default is set to the selection button -------------------------------------------------------------------------
	private EspecifiedToggleButton currentButton = selectionButton;


	public PaintPane(CanvasState canvasState, StatusPane statusPane, ActionMenu undoAndRedo, TagsBar tagsBar) {
		VBox canvasAndLayers = new VBox();
		canvasAndLayers.getChildren().addAll(canvas,layerSelector);

		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.undoAndRedo = undoAndRedo;
		this.tagsBar = tagsBar;
		updateLabels();

		EspecifiedToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();

		//Created Toggle group for the shapes, delete and selection buttons.
		for (EspecifiedToggleButton tool : toolsArr) {
			tool.setMinWidth(BUTTON_MIN_WIDTH);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		//Personalize Copy Format Button.
		copyFormatButton.setMinWidth(BUTTON_MIN_WIDTH);
		copyFormatButton.setCursor(Cursor.HAND);

		//Personalize Line Width Button.
		outlineSlider.setShowTickMarks(true);
		outlineSlider.setShowTickLabels(true);
		outlineSlider.setCursor(Cursor.HAND);

		//Personalize Layers choice box.
		layersChoiceBox.setMinWidth(BUTTON_MIN_WIDTH);
		layersChoiceBox.setCursor(Cursor.HAND);
		layersChoiceBox.setValue("Layer 1 ");
		selectedLayer = layersChoiceBox.getValue();

		//Adding of all buttons of side-bar.
		VBox buttonsBox = new VBox(10);
		// Add the bottons that manipulate figure.
		buttonsBox.getChildren().addAll(toolsArr);
		// Add the bottons that define the format the figure.
		buttonsBox.getChildren().addAll(copyFormatButton, outline, outlineSlider, outlinePicker, fill, fillPicker, layersChoiceBox, tagTextArea, saveTagButton);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(DEFAULT_LINE_WIDTH);

		//Set on action of the buttons ------------------------------------------------------------------------------
		//When a specific button is pressed the "currentButton" field is updated.
		rectangleButton.setOnAction(event -> currentButton = rectangleButton);
		squareButton.setOnAction(event -> currentButton = squareButton);
		ellipseButton.setOnAction(event -> currentButton = ellipseButton);
		circleButton.setOnAction(event -> currentButton = circleButton);

		//Selected button actions
		selectionButton.setOnAction(event -> currentButton = selectionButton);

		//Delete button actions
		deleteButton.setOnAction(event -> {
			currentButton = deleteButton;
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure,selectedLayer);
				canvasVersions.saveVersion(CanvasAction.DELETE,selectedFigure.getShapeName(),canvasState.getFiguresCopy(selectedLayers));
				updateLabels();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		//Figure format modifier
		outlinePicker.setOnAction(event -> {
			if(selectedFigure != null){
				selectedFigure.getFormat().setLineColor(outlinePicker.getValue().toString());
				canvasVersions.saveVersion(CanvasAction.CHANGE_BORDER_COLOR,selectedFigure.getShapeName(),canvasState.getFiguresCopy(selectedLayers));
				updateLabels();
				redrawCanvas();
			}
		});
		fillPicker.setOnAction(event -> {
			if(selectedFigure != null){
				selectedFigure.getFormat().setFillColor(fillPicker.getValue().toString());
				canvasVersions.saveVersion(CanvasAction.CHANGE_FILL_COLOR,selectedFigure.getShapeName(),canvasState.getFiguresCopy(selectedLayers));
				updateLabels();
				redrawCanvas();
			}
		});
		outlineSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if(selectedFigure != null) {
				selectedFigure.getFormat().setLineWidth(new_val.doubleValue());
				redrawCanvas();
			}
		});

		//Logic linked to tag saver and tag text area
		tagTextArea.setWrapText(true);
		tagTextArea.setPrefRowCount(DEFAULT_ROW_HEIGHT);
		this.tagsBar.getAllTagsButton().setOnAction(event -> {
			if(tagsBar.getAllTagsButton().isSelected()) {
				activeTag = null;
				redrawCanvas();
			}
		});
		this.tagsBar.getSpecificTagsButton().setOnAction(event -> {
			if(tagsBar.getSpecificTagsButton().isSelected()) {
				activeTag = tagsBar.getSpecificTagText();
				redrawCanvas();
			}
		});
		saveTagButton.setOnAction(event -> {
			String undelimitedTags = tagTextArea.getText();
			if(selectedFigure != null) {
				selectedFigure.addTags(undelimitedTags.split("\s"));
			}

		});


		//gets the layer user is working on.
		layersChoiceBox.setOnAction(event -> {
			String oldLayer = selectedLayer;
			selectedLayer = layersChoiceBox.getValue();
			//Move selected figure to new layer.
			if(selectedFigure != null){
				canvasState.moveFigure(selectedFigure,oldLayer,selectedLayer);
				canvasVersions.saveVersion(CanvasAction.CHANGE_LAYER,selectedFigure.getShapeName(),canvasState.getFiguresCopy(selectedLayers));
				updateLabels();
				redrawCanvas();
			}
		});

		// Action of the checkbox modifies the view of the paintPane.
		for (CheckBox checkBox : layerSelector.getLayers()) {
			checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
				if(ov.getValue()) {
					selectedLayers.add(checkBox.getText());
				}
				else {
					selectedLayers.remove(checkBox.getText());
				}
				updateLabels();
				redrawCanvas();
			});
		}


		//When CopyFormatButton is pressed the variable copiedFormat is updated to the selected figure's format
		copyFormatButton.setOnAction(event -> {
			if(selectedFigure == null) {
				statusPane.updateStatus("Ninguna figura seleccionada");
			}
			else {
				copiedFormat = selectedFigure.getFormat();
			}
		});

		// Undo and redo button actions
		this.undoAndRedo.getRedo().setOnAction(event ->{
			if(canvasVersions.canRedo()){
				canvasVersions.redo();
				canvasState.redoFigureChange();
				updateLabels();
				redrawCanvas();
			}
		});

		this.undoAndRedo.getUndo().setOnAction(event ->{
			if(canvasVersions.canUndo()){
				canvasVersions.undo();
				canvasState.undoFigureChange();
				updateLabels();
				redrawCanvas();
			}
		});
		//------------------------------------------------------------------------------

		//Movement of the mouse ----------------------------------------------------------------

		//Selection of Point to draw.
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		//Shape drawing logic.
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) { //Ver
				return;
			}
			//Change format field to the new specified format.
			currentFormat.setLineWidth(outlineSlider.getValue());
			currentFormat.setLineColor(outlinePicker.getValue().toString());
			currentFormat.setFillColor(fillPicker.getValue().toString());
			FormatFigure newFigure = null;
			if (currentButton.isAFigureButton()) {
				//Get the corresponding figure of the button toggled.
				newFigure = currentButton.getFigure(new FrontFigureDrawer(gc), new Format(currentFormat.getLineColor(), currentFormat.getFillColor(), currentFormat.getLineWidth()), startPoint, endPoint);
				canvasState.addFigure(newFigure, selectedLayer); //Added figure to the back-end trace of figures.
				startPoint = null; //Reset the start point.
				canvasVersions.saveVersion(CanvasAction.DRAW,newFigure.getShapeName(), canvasState.getFiguresCopy(selectedLayers));
				canvasVersions.clearRedo(); //Reset redo versions
				redrawCanvas(); //Redraw the canvas.
			}
		});

		//Label updater logic with movement of the mouse.
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			FormatFigure mouse = findSelectedFigure(eventPoint);
			if(mouse == null){
				statusPane.updateStatus(eventPoint.toString());
			} else {
				statusPane.updateStatus(mouse.toString());//Modifies message on the bottom of the app
			}
		});

		// Selection of shape.
		canvas.setOnMouseClicked(event -> {
			tagTextArea.clear();
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				selectedFigure = findSelectedFigure(eventPoint);
				if(selectedFigure == null){
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				else{
					applyFormat(selectedFigure);
					tagTextArea.insertText(0, selectedFigure.getTags());
					statusPane.updateStatus("Se seleccionó: %s".formatted(selectedFigure)); //Modifies message on the bottom of the app
				}
				redrawCanvas();
			}
		});

		//Move shape logic
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && selectedFigure != null) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.moveFigure(diffX,diffY);
				redrawCanvas();
			}
		});
		// -----------------------------------------------------------------------------

		setLeft(buttonsBox);
		setRight(canvasAndLayers);
	}

	// Draws the shapes
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		currentFigures = canvasVersions.getCurrentVersion();
		for (FormatFigure figure : canvasState.figures(selectedLayers)) {
			if (selectedFigure == figure && shouldDrawByTag(figure)) {
				figure.drawFigure(SELECTED_LINE_COLOR_HX);
			} else if(shouldDrawByTag(figure)){
				figure.drawFigure();
			}
		}
	}
	private boolean shouldDrawByTag(FormatFigure figure) {
		if(activeTag == null) {
			return true;
		}
		return figure.getTags().contains(activeTag);
	}
	private FormatFigure findSelectedFigure(Point eventPoint){
		FormatFigure figureToReturn = null;
		for(FormatFigure figure : canvasState.figures(selectedLayer)) {
			if(figure.pointIsIn(eventPoint)) {
				figureToReturn=figure;
			}
		}
		return figureToReturn;
	}
	private void applyFormat(FormatFigure figure) {
		if(copiedFormat == null) {
			return;
		}
		figure.setFormat(copiedFormat);
		canvasVersions.saveVersion(CanvasAction.COPY_FORMAT, figure.getShapeName(), canvasState.getFiguresCopy(selectedLayers));
		copiedFormat = null;

	}

	private void updateLabels(){
		undoAndRedo.setUndoLabel(canvasVersions.lastActionUndo());
		undoAndRedo.setRedoLabel(canvasVersions.lastActionRedo());
	}
}
