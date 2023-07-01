import java.util.ArrayList;
import java.util.List;

public class GameState {
    /* GameState class is a SINGLETON class , we do not need more instances of this class per game */
    private static GameState instance = null;
    private final Board board = new Board();
    private PlaySide currentPlaySide = PlaySide.WHITE;
    private boolean castlingWhite = true;
    private boolean castlingBlack = true;
    private final List<Piece> capturedWhite = new ArrayList<>();
    private final List<Piece> capturedBlack = new ArrayList<>();

    private GameState() { }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public Board getBoard() {
        return this.board;
    }

    public PlaySide getCurrentPlaySide() {
        return this.currentPlaySide;
    }

    public boolean isCastlingWhite() {
        return this.castlingWhite;
    }

    public boolean isCastlingBlack() {
        return this.castlingBlack;
    }

    public void setCastling(PlaySide side) {
        if (side == PlaySide.WHITE) {
            this.castlingWhite = false;
        } else {
            this.castlingBlack = false;
        }
    }

    public void setCurrentPlaySide(PlaySide currentPlaySide) {
        this.currentPlaySide = currentPlaySide;
    }

    public void captureWhite(Piece piece) {
        this.capturedWhite.add(piece);
    }

    public void captureBlack(Piece piece) {
        this.capturedBlack.add(piece);
    }

    public boolean isCheck(Position kingPosition, PlaySide side, Board currentBoard) {
        for (int r = 0; r < ChessCONSTANTS.BOARD_SIZE; r++) {
            for (int c = 0; c < ChessCONSTANTS.BOARD_SIZE; c++) {
                Piece p = currentBoard.getState()[r][c];
                if (p != null && p.getPlaySide() != side) {
                    List<Move> moves = p.getLegalMoves(board);

                    for (Move move : moves) {
                        if (move.parsePosition()[1] == kingPosition) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckMate(PlaySide side, Board currentBoard) {
        return currentBoard.getAllLegalMoves(side).isEmpty();
    }

    public boolean isGameOver() {
        return isCheckMate(PlaySide.WHITE, board) || isCheckMate(PlaySide.BLACK, board);
    }
}
