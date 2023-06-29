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
    protected Position getCurrentPosition() {
        return this.currentPosition;
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
        for (int c = column + 1; c < ChessCONSTANTS.BOARD_SIZE; c++) {
            if (addMovesUntil(board, moves, row, c)) break;
        }

        /* Check moves to the left */
        for (int c = column - 1; c >= 0; c--) {
            if (addMovesUntil(board, moves, row, c)) break;
        }

        /* Check moves up */
        for (int r = row - 1; r >= 0; r--) {
            if (addMovesUntil(board, moves, r, column)) break;
        }

        /* Check moves down */
        for (int r = row - 1; r >= 0; r--) {
            if (addMovesUntil(board, moves, r, column)) break;
        }
    }

    protected boolean moveWithinBounds(int r, int c) {
        return r >= 0 && r < ChessCONSTANTS.BOARD_SIZE && c >= 0 && c < ChessCONSTANTS.BOARD_SIZE;
    }

    protected static Piece createPieceFromReplacement(PiecesType replacement, PlaySide side, GameState gs)
    {
        if (side == PlaySide.WHITE)
        {
            if (gs.getCapturedWhite().size() == 0)
            {
                return null;
            }
        }
        else
        {
            if (gs.getCapturedBlack().size() == 0)
            {
                return null;
            }
        }
        return switch (replacement) {
            case QUEEN -> retrieveCapturedPiece(PiecesType.QUEEN, side, gs);
            case ROOK -> retrieveCapturedPiece(PiecesType.ROOK, side, gs);
            case BISHOP -> retrieveCapturedPiece(PiecesType.BISHOP, side, gs);
            case KNIGHT -> retrieveCapturedPiece(PiecesType.KNIGHT, side, gs);
            default -> null;
        };
    }

    protected static Piece retrieveCapturedPiece(PiecesType replacement, PlaySide side, GameState gs)
    {
        if (side == PlaySide.WHITE)
        {
            for(Piece p : gs.getCapturedWhite())
            {
                if (p.getType() == replacement)
                {
                    gs.removeFromCapturedWhite(p);
                    return p;
                }
            }
        } else {
            for(Piece p : gs.getCapturedBlack())
            {
                if (p.getType() == replacement)
                {
                    gs.removeFromCapturedBlack(p);
                    return p;
                }
            }
        }
        return null;
    }
}
