public class Board  {

    private Piece[][] state;

    public Board() {
        state = new Piece[ChessCONSTANTS.BOARD_SIZE][ChessCONSTANTS.BOARD_SIZE];
    }

    public Board(Board old) {
        state = new Piece[ChessCONSTANTS.BOARD_SIZE][ChessCONSTANTS.BOARD_SIZE];

        for (int row = 0; row < ChessCONSTANTS.BOARD_SIZE; row++) {
            System.arraycopy(old.state[row], 0, state[row], 0, ChessCONSTANTS.BOARD_SIZE);
        }
    }

    public Piece[][] getState() {
        return this.state;
    }

    public Piece getPiece(Position position) {
        return this.state[position.getRowIndex()][position.getColumnIndex()];
    }

    public void removePiece(Position position) {
        this.state[position.getRowIndex()][position.getColumnIndex()] = null;
    }

    public void addPiece(Piece piece, int row, int column) {
        this.state[row][column] = piece;
    }

    public void addPawns(final PlaySide playSide) {
        int row = playSide == PlaySide.WHITE ? 1 : 6;

        for (int i = 0; i < ChessCONSTANTS.BOARD_SIZE; i++) {
            addPiece(new Pawn(playSide, new Position(row, i)), row, i);
        }
    }

    public void addAllPiecesExceptPawns(final PlaySide playSide) {
        int row = playSide == PlaySide.WHITE ? 0 : 7;

        for (int i = 0; i < ChessCONSTANTS.BOARD_SIZE; i++) {
            if (i == 0 || i == 7) {
                addPiece(new Rook(playSide, new Position(row, i)), row, i);
            } else if (i == 1 || i == 6) {
                addPiece(new Knight(playSide, new Position(row, i)), row, i);
            } else if (i == 2 || i == 5) {
                addPiece(new Bishop(playSide, new Position(row, i)), row, i);
            } else if (i == 3) {
                addPiece(new Queen(playSide, new Position(row, i)), row, i);
            } else {
                addPiece(new King(playSide, new Position(row, i)), row, i);
            }
        }
    }

    public Board initializeBoard() {
        /* Adding the pawns for both sides */
        addPawns(PlaySide.WHITE);
        addPawns(PlaySide.BLACK);
        /* Adding rest of the pieces for both sides */
        addAllPiecesExceptPawns(PlaySide.WHITE);
        addAllPiecesExceptPawns(PlaySide.BLACK);

        return this;
    }
}
