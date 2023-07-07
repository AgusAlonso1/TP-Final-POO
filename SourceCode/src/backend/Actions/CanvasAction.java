package backend.Actions;

public enum CanvasAction {
    DRAW("Dibujar",false),
    DELETE("Borrar",false),
    CHANGE_FILL_COLOR("Cambiar color de relleno de",true),
    CHANGE_BORDER_COLOR("Cambiar color de borde de",true),
    COPY_FORMAT("Copiar formato a",true),
    CHANGE_LAYER("Cambiar capa de",true);

    private String message;
    private boolean modifier;
    CanvasAction(String message, boolean modifier) {
        this.message = message;
        this.modifier = modifier;
    }

    public boolean isAFormatModifier(){
        return modifier;
    }

    @Override
    public String toString() {
        return message;
    }

}
