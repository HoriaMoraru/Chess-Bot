import java.util.Optional;

public class Move {
    /* Positions (source, destination) are encoded in coordinate notation
     as strings (i.e. "e1", "f6", "a4" etc.) */
    private final Optional<String> source;
    private final Optional<String> destination;

    /* Piece to promote a pawn advancing to last row, or
    *  piece to drop-in (from captured assets) */
    private final Optional<PiecesType> replacement;

    /*
      Use the following 3 constructors for Move:
      moveTo(src, dst), if emitting a standard move (advance, capture, castle)
      promote(src, dst, replace), if advancing a pawn to last row
      resign(), if you want to resign
     */

    public Optional<String> getSource() {
        return source;
    }

    public Optional<String> getDestination() {
        return destination;
    }

    public Optional<PiecesType> getReplacement() {
        return replacement;
    }

    private Move(String source, String destination, PiecesType replacement) {
        this.source = Optional.ofNullable(source);
        this.destination = Optional.ofNullable(destination);
        this.replacement = Optional.ofNullable(replacement);
    }

    public boolean isPawnFirstMove2Steps() {
        return source.isPresent() && destination.isPresent() && replacement.isEmpty()
                && source.get().charAt(1) == '2'
                && destination.get().charAt(1) == '4';
    }

    public boolean isCastling() {
        return source.isPresent() && destination.isPresent() && replacement.isEmpty()
                && source.get().charAt(0) == 'e' && destination.get().charAt(0) == 'g';
    }

    /**
     * Checks whether the move is a usual move/capture
     * @return true if move is NOT a drop-in or promotion, false otherwise
     */
    public boolean isNormal() {
        return source.isPresent() && destination.isPresent() && replacement.isEmpty();
    }

    /**
     * Check whether move is a promotion (pawn advancing to last row)
     * @return true if move is a promotion (promotion field set and source is not null)
     */
    public boolean isPromotion() {
        return source.isPresent() && destination.isPresent() && replacement.isPresent();
    }

    /**
     * Emit a move from src to dst. Validity is to be checked by engine (your implementation)
     * Positions are encoded as stated at beginning of file
     * Castles are encoded as follows:
     * source: position of king
     * destination: final position of king (two tiles away)
     * @param source initial tile
     * @param destination destination tile
     * @return move to be sent to board
     */
    public static Move moveTo(String source, String destination) {
        return new Move(source, destination, null);
    }

    /**
     * Emit a promotion move. Validity is to be checked by engine
     * (i.e. source contains a pawn in second to last row, etc.)
     * @param source initial tile of pawn
     * @param destination next tile (could be diagonal if also capturing)
     * @param replacement piece to promote to (must not be pawn or king)
     * @return move to be sent to board
     */
    public static Move promote(String source, String destination, PiecesType replacement) {
        return new Move(source, destination, replacement);
    }

    public static Move resign() {
        return new Move(null, null, null);
    }

    public Position[] parsePosition() {
        Position[] positions = new Position[2];
        positions[0] = new Position(source.orElse(""));
        positions[1] = new Position(destination.orElse(""));

        return positions;
    }

}
