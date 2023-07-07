package backend.actions;

public enum ActionType {
    DRAW("Dibujar"),
    DELETE("Borrar"),
    CHANGE_FILL_COLOR("Cambiar color de relleno de"),
    CHANGE_BORDER_COLOR("Cambiar color de borde de"),
    COPY_FORMAT("Copiar formato a"),
    CHANGE_LAYER("Cambiar capa de");

    private String message;
    ActionType(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
