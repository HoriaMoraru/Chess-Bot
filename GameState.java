import java.util.ArrayList;
import java.util.List;

public class GameState {
    /* GameState class is a SINGLETON class , we do not need more instances of this class per game */
    private static GameState instance = null;
    private Board board = new Board();
    private PlaySide currentPlaySide = PlaySide.WHITE;
    private PlaySide check = PlaySide.NONE;
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

    public PlaySide getCheck() {
        return this.check;
    }

    public boolean isCastlingWhite() {
        return this.castlingWhite;
    }

    public boolean isCastlingBlack() {
        return this.castlingBlack;
    }

    public List<Piece> getCapturedWhite() {
        return this.capturedWhite;
    }

    public List<Piece> getCapturedBlack() {
        return this.capturedBlack;
    }

    public void setCurrentPlaySide(PlaySide currentPlaySide) {
        this.currentPlaySide = currentPlaySide;
    }

    public void removeFromCapturedWhite(Piece piece) {
        this.capturedWhite.remove(piece);
    }

    public void removeFromCapturedBlack(Piece piece) {
        this.capturedBlack.remove(piece);
    }

    public void isCheck(Position kingPosition, PlaySide side) {
        this.check = PlaySide.NONE;
        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < ChessCONSTANTS.BOARD_SIZE; r++) {
            for (int c = 0; c < ChessCONSTANTS.BOARD_SIZE; c++) {
                Piece p = this.board.getState()[r][c];
                if (p != null && p.getPlaySide() != side) {
                    moves.addAll(p.getLegalMoves(this.board));
                }
            }
        }
        for (Move move : moves) {
            if (move.getDestination().orElse("").equals(kingPosition.serializePosition())) {
                this.check = side;
                break;
            }
        }
    }
}
