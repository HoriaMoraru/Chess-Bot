import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = currentPosition.getRowIndex();
        int column = currentPosition.getColumnIndex();

        /*
         * The knight can move either 2 rows up/down and 1 column left/right
         * OR
         * 2 columns left/right and 1 row top/up
         */

        /* Up */
        int[] rowOffsets = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colOffsets = {1, -1, 1, -1, 2, -2, 2, -2};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newColumn = column + colOffsets[i];

            if (moveWithinBounds(newRow, newColumn)) {
                addMove(board, moves, newRow, newColumn);
            }
        }

        return moves;
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.KNIGHT;
    }
}
