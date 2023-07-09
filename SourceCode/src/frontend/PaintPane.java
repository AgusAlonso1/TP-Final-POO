package frontend;

import backend.actions.LastAction;
import backend.actions.ActionType;
import backend.CanvasState;
import backend.actions.ActionManager;
import backend.customExceptions.NothingToDoException;
import backend.model.*;
import frontend.customLayouts.ActionMenu;
import frontend.customLayouts.LayerSelector;
import frontend.customLayouts.TagsBar;
import frontend.customTools.EspecifiedToggleButton;
import frontend.customTools.FrontFigureDrawer;
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

import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd CanvasState -------------------------------------------------------------------------
	private CanvasState canvasState;

	//Canvas versions -------------------------------------------------------------------------
	private ActionManager lastAction;

	// Canvas and related -------------------------------------------------------------------------
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private static final int DEFAULT_LINE_WIDTH = 1;
	private static final int BUTTON_MIN_WIDTH = 90;

	//Error alert contants --------------------------------------------------------------------------------
	private static final String ERROR = "ERROR";

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

	// Figure that is selected on the canvas by the user  -------------------------------------------------------------------------
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
	private final EspecifiedToggleButton copyFormatButton = new EspecifiedToggleButton("Cop. form",ButtonType.MISC);

	//Layers Choice Box button -------------------------------------------------------------------------
	private final LayerSelector layerSelector = new LayerSelector();
	private ChoiceBox<String> layersChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(layerSelector.layersName()));

	// Layer in which user is working on -------------------------------------------------------------------------
	private String selectedLayer = "Layer 1 "; //default layer

	// Layers that are selected -------------------------------------------------------------------------
	private List<String> selectedLayers = layerSelector.layersName();

	// Undo and Redo Button -------------------------------------------------------------------------------------
	private final ActionMenu undoAndRedo;

	//Figure tag constants  ---------------------------------------------------------------------------------------
	private static final int DEFAULT_ROW_HEIGHT = 4;
	private String activeTag = null;
	private static final String TAG_STRING_DELIMITER = "\s";

	// Figure tag button --------------------------------------------------------------------------------------
	private final TextArea tagTextArea = new TextArea();
	private final Button saveTagButton = new Button("Guardar");

	//Figure Tags bar ----------------------------------------------------------------------------------------
	private final TagsBar tagsBar;

	// Starting point to draw a figure -------------------------------------------------------------------------
	private Point startPoint;

	// StatusBar -------------------------------------------------------------------------
	private StatusPane statusPane;

	// CopyFormat variable to see current format selected by user ----------------------------------------------------------
	private Format copiedFormat = null;

	//Current toggled button, default is set to the selection button -------------------------------------------------------------------------
	private EspecifiedToggleButton currentButton = selectionButton;


	public PaintPane(CanvasState canvasState, StatusPane statusPane, ActionMenu undoAndRedo, TagsBar tagsBar) {
		VBox canvasAndLayers = new VBox();
		canvasAndLayers.getChildren().addAll(canvas,layerSelector);

		//CanvasState, StatusPane, and ActionMenu are saved.
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.undoAndRedo = undoAndRedo;

		//Actions that occur in the canvas.
		lastAction = new ActionManager(canvasState);

		//Figure Tags.
		this.tagsBar = tagsBar;
		updateLabels();

		//Array with the buttons that create figures and allow possible changes (select and delete).
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

		// Add the buttons that manipulate figure.
		buttonsBox.getChildren().addAll(toolsArr);

		// Add the buttons that define the format the figure.
		buttonsBox.getChildren().addAll(copyFormatButton, outline, outlineSlider, outlinePicker, fill, fillPicker, layersChoiceBox, tagTextArea, saveTagButton);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(DEFAULT_LINE_WIDTH);

		//Set on action of the buttons ------------------------------------------------------------------------------
		//When a specific button is pressed the "currentButton" field is updated.

		//Buttons to create figures.
		rectangleButton.setOnAction(event -> currentButton = rectangleButton);
		squareButton.setOnAction(event -> currentButton = squareButton);
		ellipseButton.setOnAction(event -> currentButton = ellipseButton);
		circleButton.setOnAction(event -> currentButton = circleButton);

		//Buttons to select figures.
		selectionButton.setOnAction(event -> currentButton = selectionButton);

		//Button to delete figure. Only if the figure is selected can the user delete.
		deleteButton.setOnAction(event -> {
			currentButton = deleteButton;
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
				LastAction lastAct = new LastAction(ActionType.DELETE,selectedFigure,selectedFigure,(canvas,oldFig,newFig) -> canvas.addFigure(oldFig) , (canvas,oldFig,newFig) -> canvas.deleteFigure(oldFig));
				lastAction.saveVersion(lastAct);
				updateLabels();
				selectedFigure = null;
				redrawCanvas();
			} else {
				statusPane.updateStatus("No se selecciono ninguna figura, no se puede eliminar");
			}
		});

		//Figure format modifiers.
		// Allow user to modify the fill and border color as well as allowing the to change the width.
		outlinePicker.setOnAction(event -> {
			if(selectedFigure != null){
				FormatFigure oldFigure = selectedFigure.getFigureCopy();
				selectedFigure.getFormat().setLineColor(outlinePicker.getValue().toString());
				//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
				LastAction lastAct = new LastAction(ActionType.CHANGE_BORDER_COLOR,oldFigure,selectedFigure,(canvas,oldFig,newFig) -> canvas.changeFormat(newFig,oldFig.getFormat()),(canvas,oldFig,newFig) -> canvas.changeFormat(oldFig,newFig.getFormat()));
				lastAction.saveVersion(lastAct);
				updateLabels();
				redrawCanvas();
			}else {
				statusPane.updateStatus("No se selecciono ninguna figura, no se puede cambiar el borde");
			}
		});

		fillPicker.setOnAction(event -> {
			if(selectedFigure != null){
				FormatFigure oldFigure = selectedFigure.getFigureCopy();
				selectedFigure.getFormat().setFillColor(fillPicker.getValue().toString());
				//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
				LastAction lastAct = new LastAction(ActionType.CHANGE_FILL_COLOR,oldFigure,selectedFigure,(canvas,oldFig,newFig) -> canvas.changeFormat(newFig,oldFig.getFormat()),(canvas,oldFig,newFig) -> canvas.changeFormat(oldFig,newFig.getFormat()));
				lastAction.saveVersion(lastAct);
				updateLabels();
				redrawCanvas();
			}else {
				statusPane.updateStatus("No se selecciono ninguna figura, no se puede cambiar relleno");
			}
		});

		outlineSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if(selectedFigure != null) {
				selectedFigure.getFormat().setLineWidth(new_val.doubleValue());
				redrawCanvas();
			}else {
				statusPane.updateStatus("No se selecciono ninguna figura, no se puede cambiar el ancho del borde");
			}
		});

		//Figure tag assigner.
		// Logic linked to tag saver and tag text area
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
				selectedFigure.addTags(undelimitedTags.split(TAG_STRING_DELIMITER));
			}else {
				statusPane.updateStatus("No se selecciono ninguna figura, no se puede asignar etiqueta");
			}
		});


		//Gets the layer the user is working on.
		layersChoiceBox.setOnAction(event -> {
			selectedLayer = layersChoiceBox.getValue();
			//Move selected figure to new layer.
			if(selectedFigure != null){
				FormatFigure copyFigure = selectedFigure.getFigureCopy();
				canvasState.moveFigure(selectedFigure,selectedLayer);
				//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
				LastAction lastAct = new LastAction(ActionType.CHANGE_LAYER,copyFigure,selectedFigure, (canvas,oldFig,newFig) -> canvas.moveFigure(newFig, oldFig.getLayer()), (canvas,oldFig,newFig) -> canvas.moveFigure(oldFig,newFig.getLayer()));
				lastAction.saveVersion(lastAct);
				updateLabels();
				redrawCanvas();
			}
		});

		// Action of the checkbox modifies the view of the paintPane.
		// Depending on the layers that are selected the corresponding figures will be shown.
		for (CheckBox checkBox : layerSelector.getLayers()) {
			checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
				if(ov.getValue()) {
					selectedLayers.add(checkBox.getText());
				}
				else {
					selectedLayers.remove(checkBox.getText());
				}
				redrawCanvas();
			});
		}

		//CopiedFormat Button.
		//When CopyFormatButton is pressed the variable copiedFormat is updated to the selected figure's format.
		copyFormatButton.setOnAction(event -> {
			currentButton = copyFormatButton;
			if(selectedFigure == null) {
				statusPane.updateStatus("Ninguna figura seleccionada, no se puede copiar formato");
			}
			else {
				copiedFormat = selectedFigure.getFormat();
			}
		});

		// Undo and redo button actions.
		this.undoAndRedo.getRedo().setOnAction(event ->{
			try {
				lastAction.redo();
				updateLabels();
				redrawCanvas();
			} catch(NothingToDoException exception) {
				setErrorAlarm(exception.getMessage());
			}
		});

		this.undoAndRedo.getUndo().setOnAction(event ->{
			try {
				lastAction.undo();
				updateLabels();
				redrawCanvas();
			} catch (NothingToDoException exception) {
				setErrorAlarm(exception.getMessage());
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
				newFigure = currentButton.getFigure(new FrontFigureDrawer(gc), new Format(currentFormat.getLineColor(), currentFormat.getFillColor(), currentFormat.getLineWidth()),selectedLayer, startPoint, endPoint);
				canvasState.addFigure(newFigure); //Added figure to the back-end trace of figures.
				startPoint = null; //Reset the start point.
				//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
				LastAction lastAct = new LastAction(ActionType.DRAW,newFigure,newFigure, (canvas,oldFig,newFig) -> canvas.deleteFigure(oldFig), (canvas,oldFig,newFig) -> canvas.addFigure(oldFig));
				lastAction.saveVersion(lastAct);
				lastAction.clearRedo(); //Reset redo versions
				updateLabels();
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
		FormatFigure oldFigure = figure.getFigureCopy();
		figure.setFormat(copiedFormat);
		//oldFig represent old figure and newFig represent new figure after the action has been executed in lambda expression.
		LastAction lastAct = new LastAction(ActionType.COPY_FORMAT,oldFigure,figure, (canvas,oldFig,newFig) -> canvas.changeFormat(newFig,oldFig.getFormat()), (canvas,oldFig,newFig) -> canvas.changeFormat(oldFig,newFig.getFormat()));
		lastAction.saveVersion(lastAct);
		copiedFormat = null;

	}

	private void setErrorAlarm(String message) {
		Alert undoRedoAlert = new Alert(Alert.AlertType.ERROR);
		undoRedoAlert.setTitle(ERROR);
		undoRedoAlert.setHeaderText(message);
		undoRedoAlert.showAndWait();
	}

	private void updateLabels(){
		undoAndRedo.setUndoLabel(lastAction.lastActionUndo());
		undoAndRedo.setRedoLabel(lastAction.lastActionRedo());
	}
}
