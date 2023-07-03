package frontend;

import backend.ButtonType;
import backend.CanvasState;
import backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private final EspecifiedToggleButton selectionButton = new EspecifiedToggleButton("Seleccionar", ButtonType.MISC);
	private final EspecifiedToggleButton rectangleButton = new EspecifiedToggleButton("Rectángulo", ButtonType.RECTANGLE);
	private final EspecifiedToggleButton circleButton = new EspecifiedToggleButton("Círculo", ButtonType.CIRCLE);
	private final EspecifiedToggleButton squareButton = new EspecifiedToggleButton("Cuadrado", ButtonType.SQUARE);
	private final EspecifiedToggleButton ellipseButton = new EspecifiedToggleButton("Elipse", ButtonType.ELLIPSE);
	private final EspecifiedToggleButton deleteButton = new EspecifiedToggleButton("Borrar", ButtonType.MISC);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	private EspecifiedToggleButton currentButton = selectionButton; //Default button is the selection button.

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		EspecifiedToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (EspecifiedToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
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

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY())); //Selection of Point to draw.

		//Shape drawing logic.
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) { //Ver
				return;
			}
			Figure newFigure = null;
			if (currentButton.isAFigureButton()) {
				newFigure = currentButton.getFigure(startPoint,endPoint);
			} else {
				return;
			}
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas(); //draws the figure.
		});

		// Label updater logic with movement of the mouse.
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure);
				}
			}
			if(found){
				statusPane.updateStatus(label.toString()); //Modifies message on the bottom of the app
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		// Selection of shape.
		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure);
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString()); //Modifies message on the bottom of the app
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		//Move shape logic
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.moveFigure(diffX,diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	// Draws the shapes
	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) { //Looks for the figure that is selected.
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor); //negro
			}
			gc.setFill(fillColor); //amarillo

			if(figure instanceof Rectangle) { //dibuja rectangle
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
				gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
			} else if(figure instanceof Circle) {
				Circle circle = (Circle) figure;
				double diameter = circle.getRadius() * 2;
				gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
				gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
			} else if(figure instanceof Square) {
				Square square = (Square) figure;
				gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
				gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
			} else if(figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
				gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
			}
		}
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.pointIsIn(eventPoint);
	}

}
