public class Position {

    private int rowIndex;
    private int columnIndex;

    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
    public Position(String positionAsString) {
        this.rowIndex = positionAsString.charAt(1) - '0';
        switch (positionAsString.charAt(0)) {
            case 'a' -> this.columnIndex = 0;
            case 'b' -> this.columnIndex = 1;
            case 'c' -> this.columnIndex = 2;
            case 'd' -> this.columnIndex = 3;
            case 'e' -> this.columnIndex = 4;
            case 'f' -> this.columnIndex = 5;
            case 'g' -> this.columnIndex = 6;
            case 'h' -> this.columnIndex = 7;
            default ->
                    throw new IllegalStateException("Position deserialization unknown value: " + positionAsString.charAt(0));
        }
    }
    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
    public String serializePosition() {
        final String rowAsString = String.valueOf(this.rowIndex);
        String columnAsString = switch (this.columnIndex) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> throw new IllegalStateException("Position serialization unknown value: " + this.columnIndex);
        };
        return columnAsString + rowAsString;
    }
}
