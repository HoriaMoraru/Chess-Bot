import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean firstMove = true;
    private boolean enPassant = false;

    public Pawn(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.PAWN;
    }

    protected boolean isEnPassant() {
        return enPassant;
    }

    protected void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = currentPosition.getRowIndex();
        int column = currentPosition.getColumnIndex();

        int direction = playSide == PlaySide.WHITE ? 1 : -1;

        /* First move of the pawn can be 2 steps */
        if (firstMove) {
            Piece inbetweenPiece = board.getState()[row + direction][column];
            int newRow = row + 2 * direction;
            if (moveWithinBounds(newRow, column) && inbetweenPiece == null) {
                addMovePawnSpecific(board, moves, newRow, column);
            }
            firstMove = false;
        }

        /* Move 1 step forward */
        int newRow = row + direction;
        int[] colOffsets = {1, -1};

        if (moveWithinBounds(newRow, column)) {
            addMovePawnSpecific(board, moves, newRow, column);
        }

        /* Check if there are any captures available for the pawn */
        for (int colOffset : colOffsets) {
            int newColumn = column + colOffset;
            if (moveWithinBounds(newRow, newColumn)) {
                addMovePawnCapture(board, moves, newRow, newColumn);
            }
        }

        return moves;
    }

    private void addMovePawnSpecific(Board board, List<Move> moves, int r, int c) {
        Piece p = board.getState()[r][c];
        if (p == null) {
            pawnNormalOrPromotion(moves, r, c);
        }
    }

    private void addMovePawnCapture(Board board, List<Move> moves, int r, int c) {
        Piece p = board.getState()[r][c];
        Piece pEnpassant = board.getState()[currentPosition.getRowIndex()][c];
        if (p != null && p.getPlaySide() != this.playSide) {
            pawnNormalOrPromotion(moves, r, c);
        }
        /* En passant */
        else if (checkEnPassant(p, pEnpassant)) {
            moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition()));
        }
    }

    private boolean checkEnPassant(Piece p, Piece enPassant)
    {
        return p == null && enPassant != null && enPassant.getPlaySide() != this.playSide &&
                enPassant.getType() == PiecesType.PAWN && ((Pawn) enPassant).isEnPassant();
    }

    private void pawnNormalOrPromotion(List<Move> moves, int r, int c) {
        if (r == ChessCONSTANTS.PAWN_WHITE_PROMOTION_POSITION ||
                r == ChessCONSTANTS.PAWN_BLACK_PROMOTION_POSITION) {
            /* Promotion
             * Promotion is always a QUEEN, because it is the most powerfull piece of the board
             */
            moves.add(Move.promote(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition(), PiecesType.QUEEN));
        }
        else {
            moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition()));
        }
    }
}