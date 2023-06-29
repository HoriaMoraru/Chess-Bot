import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = this.currentPosition.getRowIndex();
        int column = this.currentPosition.getColumnIndex();

        /*
        * The Queen can move in any direction, just like the King , but can move more than one step
        * The queen is essentially a combination of the Rook and the Bishop
        */

        mimicBishopMovement(board, moves, row, column);
        mimicRookMovement(board, moves, row, column);

        return moves;
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.QUEEN;
    }
}
