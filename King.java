import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    protected List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = currentPosition.getRowIndex();
        int column = currentPosition.getColumnIndex();

        /* The king can go in any direction , but can move only one step */
        /* We need the GameState , in order to check if the king is in check */
        GameState gs = GameState.getInstance();

        int[] rowOffsets = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] colOffsets = {1, 0, -1, 1, -1, 1, 0, -1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newColumn = column + colOffsets[i];

            if (moveWithinBounds(newRow, newColumn)) {
                addMove(board, moves, newRow, newColumn);
            }
        }
        return moves;
    }

    protected List<Move> getEscapeMoves(Board board) {
        List<Move> moves = getLegalMoves(board);
        List<Move> escapeMoves = new ArrayList<>();

        GameState gs = GameState.getInstance();
        /* Remove the moves that will put the king in check */
        for (Move move : moves) {
            Board copyBoard = new Board(board);
            copyBoard.makeMove(move);
            if (!gs.isCheck(move.parsePosition()[1], playSide, copyBoard)) {
                escapeMoves.add(move);
            }
        }
        return escapeMoves;
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        GameState gs = GameState.getInstance();
        if (gs.isCheck(currentPosition, playSide, board)) {
            return getEscapeMoves(board);
        } else {
            return getPossibleMoves(board);
        }
    }

    @Override
    protected PiecesType getType() {
        return PiecesType.KING;
    }
}
