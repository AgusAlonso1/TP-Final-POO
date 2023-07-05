package frontend;

import backend.ButtonType;
import backend.CanvasState;
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

import java.util.ArrayList;
import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd CanvasState.
	private CanvasState canvasState;

	// Canvas and related.
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	// Default value of fill color, line color and line width.
	private static final Color DEFAULT_LINE_COLOUR = Color.BLACK;
	private static final Color DEFAULT_FILL_COLOUR = Color.YELLOW;
	private static final String SELECTED_LINE_COLOR_HX = Color.RED.toString();
	private static final double LINE_WIDTH = 1.0;

	// Current format of shapes to draw.
	private Format currentFormat = new Format(DEFAULT_LINE_COLOUR.toString(), DEFAULT_FILL_COLOUR.toString(), LINE_WIDTH );

	// Side Menu Toggle Buttons.
	private final EspecifiedToggleButton selectionButton = new EspecifiedToggleButton("Seleccionar", ButtonType.MISC);
	private final EspecifiedToggleButton rectangleButton = new EspecifiedToggleButton("Rectángulo", ButtonType.RECTANGLE);
	private final EspecifiedToggleButton circleButton = new EspecifiedToggleButton("Círculo", ButtonType.CIRCLE);
	private final EspecifiedToggleButton squareButton = new EspecifiedToggleButton("Cuadrado", ButtonType.SQUARE);
	private final EspecifiedToggleButton ellipseButton = new EspecifiedToggleButton("Elipse", ButtonType.ELLIPSE);
	private final EspecifiedToggleButton deleteButton = new EspecifiedToggleButton("Borrar", ButtonType.MISC);

	//Buttons and sliders for assignment.
	private final ToggleButton copyFormatButton = new ToggleButton("Cop. form");
	private final Label outline = new Label("Borde");

	// Format constants
	private final static double MIN_OUTLINE_WIDTH = 1.0;
	private final static double MAX_OUTLINE_WIDTH = 50.0;
	private final static double DEFAULT_OUTLINE_WIDTH = 25.0;

	// Format Buttons
	private final Slider outlineSlider = new Slider(MIN_OUTLINE_WIDTH, MAX_OUTLINE_WIDTH, DEFAULT_OUTLINE_WIDTH);
	private final ColorPicker outlinePicker = new ColorPicker(DEFAULT_LINE_COLOUR);
	private final Label fill = new Label("Relleno");
	private final ColorPicker fillPicker = new ColorPicker(DEFAULT_FILL_COLOUR);

	//Layers Choice Box
	private final LayerSelector layerSelector = new LayerSelector();
	private ChoiceBox<String> layersChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(layerSelector.layersName()));

	// Layer in which user is working on
	private String selectedLayer;

	// Layers that are selected.
	private List<String> selectedLayers = layerSelector.layersName();

	// Start point for a figure to draw.
	private Point startPoint;

	// Selected figure.
	private FormatFigure selectedFigure;

	// StatusBar.
	private StatusPane statusPane;

	private Format copiedFormat = null;

	//Current toggled button, default is set to the selection button.
	private EspecifiedToggleButton currentButton = selectionButton;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		VBox canvasAndLayers = new VBox();
		canvasAndLayers.getChildren().addAll(canvas,layerSelector);

		this.canvasState = canvasState;
		this.statusPane = statusPane;
		EspecifiedToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();

		//Created Toggle group for the shapes, delete and selection buttons.
		for (EspecifiedToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		//Personalize Copy Format Button.
		copyFormatButton.setMinWidth(90);
		copyFormatButton.setCursor(Cursor.HAND);

		//Personalize Line Width Button.
		outlineSlider.setShowTickMarks(true);
		outlineSlider.setShowTickLabels(true);
		outlineSlider.setCursor(Cursor.HAND);

		//Personalize Layers choice box.
		layersChoiceBox.setMinWidth(90);
		layersChoiceBox.setCursor(Cursor.HAND);
		layersChoiceBox.setValue("Layer 1 ");
		selectedLayer = layersChoiceBox.getValue();

		//Adding of all buttons of side-bar.
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(copyFormatButton, outline, outlineSlider, outlinePicker, fill, fillPicker, layersChoiceBox);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		//When a specific button is pressed the "currentButton" field is updated.
		rectangleButton.setOnAction(event -> currentButton = rectangleButton);
		squareButton.setOnAction(event -> currentButton = squareButton);
		ellipseButton.setOnAction(event -> currentButton = ellipseButton);
		circleButton.setOnAction(event -> currentButton = circleButton);
		selectionButton.setOnAction(event -> currentButton = selectionButton);
		deleteButton.setOnAction(event -> currentButton = deleteButton);

		//gets the layer user is working on.
		layersChoiceBox.setOnAction(event -> {
			String oldLayer = selectedLayer;
			selectedLayer = layersChoiceBox.getValue();
			//Move selected figure to new layer.
			if(selectedFigure != null){
				canvasState.moveFigure(selectedFigure,oldLayer,selectedLayer);
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
				newFigure = currentButton.getFigure(new FrontFigureDrawer(gc),new Format(currentFormat.getLineColor(),currentFormat.getFillColor(),currentFormat.getLineWidth()), startPoint, endPoint);
			} else {
				return;
			}
			canvasState.addFigure(newFigure,selectedLayer); //Added figure to the back-end trace of figures.
			startPoint = null; //Reset the start point.
			redrawCanvas(); //Redraw the canvas.
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
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				selectedFigure = findSelectedFigure(eventPoint);
				if(selectedFigure == null){
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				else{
					applyFormat(selectedFigure);
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

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure,selectedLayer);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvasAndLayers);
	}

	// Draws the shapes
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (FormatFigure figure : canvasState.figures(selectedLayers)) {
			if (selectedFigure == figure) {
				figure.drawFigure(SELECTED_LINE_COLOR_HX);
			} else {
				figure.drawFigure();
			}
		}
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
		copiedFormat = null;
	}
}
