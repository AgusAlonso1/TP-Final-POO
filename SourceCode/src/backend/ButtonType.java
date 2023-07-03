package backend;

import backend.model.*;

public enum ButtonType { //Enun to diferentiate button types.
    RECTANGLE {
        @Override
        public FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint) {
            return new Rectangle(format,firstPoint,secondPoint);
        }
    },
    SQUARE {
        @Override
        public FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint) {
            return new Square(format, firstPoint, secondPoint);
        }
    },
    ELLIPSE {
        @Override
        public FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint) {
            return new Ellipse(format, firstPoint,secondPoint);
        }
    },
    CIRCLE {
        @Override
        public FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint) {
            return new Circle(format, firstPoint,secondPoint);
        }
    },
    MISC {
        @Override
        public FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint) {
            return null;
        }
    };

    public abstract FormatFigure buildFigure(Format format, Point firstPoint, Point secondPoint);

    public boolean isAFigureType() {
        return this != MISC;
    }
}
