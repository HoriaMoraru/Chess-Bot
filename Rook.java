import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int row = currentPosition.getRowIndex();
        int column = currentPosition.getColumnIndex();

        /* The rook can move vertically or horizontally in any direction */

        mimicRookMovement(board, moves, row, column);

        return moves;
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.ROOK;
    }
}
