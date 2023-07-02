package backend;

import backend.model.*;

public enum ButtonType { //Enun to diferentiate button types.
    RECTANGLE {
        @Override
        public Figure buildFigure(Point firstPoint, Point secondPoint) {
            return new Rectangle(firstPoint,secondPoint);
        }
    },
    SQUARE {
        @Override
        public Figure buildFigure(Point firstPoint, Point secondPoint) {
            return new Square(firstPoint,secondPoint);
        }
    },
    ELLIPSE {
        @Override
        public Figure buildFigure(Point firstPoint, Point secondPoint) {
            return new Ellipse(firstPoint,secondPoint);
        }
    },
    CIRCLE {
        @Override
        public Figure buildFigure(Point firstPoint, Point secondPoint) {
            return new Circle(firstPoint,secondPoint);
        }
    },
    MISC {
        @Override
        public Figure buildFigure(Point firstPoint, Point secondPoint) {
            return null;
        }
    };

    public abstract Figure buildFigure(Point firstPoint, Point secondPoint);

    public boolean isAFigureType() {
        return this != MISC;
    }
}
