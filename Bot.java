public class Bot {
    private static final String BOT_NAME = "Gusti";
    private final GameState gs;

    public Bot() {
        /* Initialize custom fields here */
        this.gs = GameState.getInstance();
        this.gs.getBoard().initializeBoard();
    }

    /**
     * Record received move (either by enemy in normal play,
     * or by both sides in force mode) in custom structures
     * @param move received move
     * @param sideToMove side to move (either PlaySide.BLACK or PlaySide.WHITE)
     */
    public void recordMove(Move move, PlaySide sideToMove) {
        /* You might find it useful to also separately record last move in another custom field */
        Position destination = new Position(move.getDestination().orElse(""));
        Piece dp = this.gs.getBoard().getPiece(destination);

        /* Check if the destion has a present piece , if it does then remove it */
        if (dp != null)
        {
            if (sideToMove == PlaySide.WHITE) {
                this.gs.getCapturedBlack().add(dp);
            } else {
                this.gs.getCapturedWhite().add(dp);
            }
            this.gs.getBoard().removePiece(destination);
        }
        this.gs.getBoard().makeMove(move);

        if (move.isPromotion()) {
            PiecesType replacement = move.getReplacement().orElse(null);
            Piece replacePiece = Piece.createPieceFromReplacement(replacement, sideToMove, gs);
            if (replacePiece != null) {
                this.gs.getBoard().addPiece(replacePiece, destination.getRowIndex(), destination.getColumnIndex());
            }
        }

        /* Change the play side */
        if (sideToMove == PlaySide.WHITE) {
            this.gs.setCurrentPlaySide(PlaySide.BLACK);
        }
        else {
            this.gs.setCurrentPlaySide(PlaySide.WHITE);
        }
    }

    /**
     * Calculate and return the bot's next move
     * @return your move
     */
    public Move calculateNextMove() {
        /* Calculate next move for the side the engine is playing (Hint: Main.getEngineSide())
        * Make sure to record your move in custom structures before returning.
        *
        * Return move that you are willing to submit
        * Move is to be constructed via one of the factory methods defined in Move.java */
        return Move.resign();
    }

    public static String getBotName() {
        return BOT_NAME;
    }
}
