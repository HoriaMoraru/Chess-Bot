import java.util.List;

public abstract class Piece {

    protected final PlaySide playSide;
    protected Position currentPosition;

    protected Piece(PlaySide playSide, Position currentPosition) {
        this.playSide = playSide;
        this.currentPosition = currentPosition;
    }
    protected PlaySide getPlaySide() {
        return this.playSide;
    }
    protected void changePosition(Position newPosition) {
        this.currentPosition = newPosition;
    }
    protected abstract List<Move> getLegalMoves(Board board);
    protected abstract PiecesType getType();

    protected boolean addMovesUntil(Board board, List<Move> moves, int r, int c) {
        Piece p = board.getState()[r][c];
        if (p == null) {
            moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition()));
        } else {
            if (p.getPlaySide() != this.playSide) {
                moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                        new Position(r, c).serializePosition()));
            }
            return true;
        }
        return false;
    }

    protected void addMove(Board board, List<Move> moves, int r, int c) {
        Piece p = board.getState()[r][c];
        if (p == null) {
            moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition()));
        } else {
            if (p.getPlaySide() != this.playSide) {
                moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                        new Position(r, c).serializePosition()));
            }
        }
    }

    protected void mimicBishopMovement(Board board, List<Move> moves, int row, int column) {
        /* Top Right */
        for (int r = row + 1, c = column + 1; moveWithinBounds(r, c); r++, c++) {
            if (addMovesUntil(board, moves, r, c)) break;
        }

        /* Top Left */
        for (int r = row + 1, c = column - 1; moveWithinBounds(r, c); r++, c--) {
            if (addMovesUntil(board, moves, r, c)) break;
        }

        /* Bottom Right */
        for (int r = row - 1, c = column + 1; moveWithinBounds(r, c); r--, c++) {
            if (addMovesUntil(board, moves, r, c)) break;
        }

        /* Bottom Left */
        for (int r = row - 1, c = column - 1; moveWithinBounds(r, c); r--, c--) {
            if (addMovesUntil(board, moves, r, c)) break;
        }
    }

    protected void mimicRookMovement(Board board, List<Move> moves, int row, int column) {
        /* Check moves to the right */
        for (int c = column + 1; moveWithinBounds(row, c); c++) {
            if (addMovesUntil(board, moves, row, c)) break;
        }

        /* Check moves to the left */
        for (int c = column - 1; moveWithinBounds(row, c); c--) {
            if (addMovesUntil(board, moves, row, c)) break;
        }

        /* Check moves up */
        for (int r = row + 1; moveWithinBounds(r, column); r++) {
            if (addMovesUntil(board, moves, r, column)) break;
        }

        /* Check moves down */
        for (int r = row - 1; moveWithinBounds(r, column); r--) {
            if (addMovesUntil(board, moves, r, column)) break;
        }
    }

    protected boolean moveWithinBounds(int r, int c) {
        return r >= 0 && r < ChessCONSTANTS.BOARD_SIZE && c >= 0 && c < ChessCONSTANTS.BOARD_SIZE;
    }

    protected static Piece createPieceForReplacement(PiecesType replacement, PlaySide side, Position pos)
    {
        return switch (replacement) {
            case QUEEN -> new Queen(side, pos);
            case ROOK -> new Rook(side, pos);
            case BISHOP -> new Bishop(side, pos);
            case KNIGHT -> new Knight(side, pos);
            default -> null;
        };
    }
}
