package backend;

import backend.model.*;

public enum ButtonType { //Enun to diferentiate button types.
    RECTANGLE {
        @Override
        public FormatFigure buildFigure(FigureDrawer fg,Format format, String layer, Point firstPoint, Point secondPoint) {
            return new Rectangle(fg, format,layer,firstPoint,secondPoint);
        }
    },
    SQUARE {
        @Override
        public FormatFigure buildFigure(FigureDrawer fg, Format format, String layer, Point firstPoint, Point secondPoint) {
            return new Square(fg, format,layer, firstPoint, secondPoint);
        }
    },
    ELLIPSE {
        @Override
        public FormatFigure buildFigure(FigureDrawer fg, Format format, String layer, Point firstPoint, Point secondPoint) {
            return new Ellipse(fg, format, layer, firstPoint,secondPoint);
        }
    },
    CIRCLE {
        @Override
        public FormatFigure buildFigure(FigureDrawer fg, Format format, String layer, Point firstPoint, Point secondPoint) {
            return new Circle(fg, format,layer, firstPoint,secondPoint);
        }
    },
    MISC {
        @Override
        public FormatFigure buildFigure(FigureDrawer fg, Format format, String layer, Point firstPoint, Point secondPoint) {
            return null;
        }
    };

    public abstract FormatFigure buildFigure(FigureDrawer fg, Format format, String layer, Point firstPoint, Point secondPoint);

    public boolean isAFigureType() {
        return this != MISC;
    }
}
