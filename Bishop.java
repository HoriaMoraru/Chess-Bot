import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = this.currentPosition.getRowIndex();
        int column = this.currentPosition.getColumnIndex();

        /*
         * The Bishop can move diagonally in any direction
         * Check for all the diagonals
         */

        mimicBishopMovement(board, moves, row, column);

        return moves;
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.BISHOP;
    }
}
