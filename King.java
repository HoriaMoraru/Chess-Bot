import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = currentPosition.getRowIndex();
        int column = currentPosition.getColumnIndex();

        /* The king can go in any direction , but can move only one step */

        /* We need the GameState , in order to check if the king is in check */
        GameState gs = GameState.getInstance();
        PlaySide check = gs.getCheck();
        if (check == this.getPlaySide()) {
            /* The king is in check , so it can move only to the squares that are not under attack */
            int[] rowOffsets = {1, 1, 1, 0, 0, -1, -1, -1};
            int[] colOffsets = {1, 0, -1, 1, -1, 1, 0, -1};


        }
        else {
            int[] rowOffsets = {1, 1, 1, 0, 0, -1, -1, -1};
            int[] colOffsets = {1, 0, -1, 1, -1, 1, 0, -1};

            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = row + rowOffsets[i];
                int newColumn = column + colOffsets[i];

                if (moveWithinBounds(newRow, newColumn)) {
                    addMove(board, moves, newRow, newColumn);
                }
            }
        }

        return moves;
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.KING;
    }
}
