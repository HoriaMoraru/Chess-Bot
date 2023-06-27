import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean firstMove = true;
    private boolean enPassant = false;

    public Pawn(PlaySide playSide, Position currentPosition) {
        super(playSide, currentPosition);
    }

    @Override
    protected List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int row = this.currentPosition.getRowIndex();
        int column = this.currentPosition.getColumnIndex();
        /*
         * Check if the pawn is on the first move first for white
         */
        if (this.playSide == PlaySide.WHITE) {
            if (row == 6) {
                if (addMoves(board, moves, row - 1, column)) {
                    addMoves(board, moves, row - 2, column);
                    firstMove = false;
                    enPassant = true;
                }
            } else {
                addMoves(board, moves, row - 1, column);
                firstMove = false;
            }
            /*
             * then for black
             */
        } else {
            if (row == 1) {
                if (addMoves(board, moves, row + 1, column)) {
                    addMoves(board, moves, row + 2, column);
                    firstMove = false;
                    enPassant = true;
                }
            } else {
                addMoves(board, moves, row + 1, column);
            }
        }
        /*
         * Check if the pawn can capture any piece normally (NO EN PASSANT YET) first for white
         */
        /*
         * check for middle pawns
         */
        Piece p;
        if (this.playSide == PlaySide.WHITE) {
            if (row > 0 && row < 7 && column > 0 && column < 7) {
                p = board.getState()[row - 1][column - 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row - 1, column - 1).serializePosition()));
                }
                p = board.getState()[row - 1][column + 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row - 1, column + 1).serializePosition()));
                }

                /*
                 * check for left-most pawn
                 */
            } else if (column == 0) {
                p = board.getState()[row - 1][column + 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row - 1, column + 1).serializePosition()));
                }
                /*
                 * check for right-most pawn
                 */
            } else if (column == 7) {
                p = board.getState()[row - 1][column - 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row - 1, column - 1).serializePosition()));
                }
            }
            /*
             * then for black
             */

            /*
             * check for middle pawns
             */
        } else {
            if (row > 0 && row < 7 && column > 0 && column < 7) {
                p = board.getState()[row + 1][column - 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row + 1, column - 1).serializePosition()));
                }
                p = board.getState()[row + 1][column + 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row + 1, column + 1).serializePosition()));
                }
                /*
                 * check for left-most pawn
                 */
            } else if (column == 7) {
                p = board.getState()[row + 1][column - 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row + 1, column - 1).serializePosition()));
                }
                /*
                 * check for right-most pawn
                 */
            } else if (column == 0) {
                p = board.getState()[row + 1][column + 1];
                if (p != null && p.getPlaySide() != this.playSide) {
                    moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                            new Position(row + 1, column + 1).serializePosition()));
                }
            }
        }
        /*
         * Check for En Passant for white first
         */
        if (this.playSide == PlaySide.WHITE && row == 3) {
            /*
             * check for middle pawns
             */
            if (column > 0 && column < 7) {
                p = board.getState()[row][column - 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row - 1, column - 1).serializePosition()));
                    }
                }
                p = board.getState()[row][column + 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row - 1, column + 1).serializePosition()));
                    }
                }
                /*
                 * check for left-most pawn
                 */
            } else if (column == 0) {
                p = board.getState()[row][column + 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row - 1, column - 1).serializePosition()));
                    }
                }
                /*
                 * check for right-most pawn
                 */
            } else if (column == 7) {
                p = board.getState()[row][column - 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row - 1, column - 1).serializePosition()));
                    }
                }
            }
            /*
             * then for black
             */
        } else if (this.playSide == PlaySide.BLACK && row == 4) {
            /*
             * check for middle pawns
             */
            if (column > 0 && column < 7) {
                p = board.getState()[row][column - 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row + 1, column - 1).serializePosition()));
                    }
                }
                p = board.getState()[row][column + 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row + 1, column + 1).serializePosition()));
                    }
                }
                /*
                 * check for left-most pawn
                 */
            } else if (column == 0) {
                p = board.getState()[row][column + 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row + 1, column - 1).serializePosition()));
                    }
                }
                /*
                 * check for right-most pawn
                 */
            } else if (column == 7) {
                p = board.getState()[row][column - 1];
                if (p != null && p.getPlaySide() != this.playSide && p instanceof Pawn pawn) {
                    if (pawn.enPassant) {
                        moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                                new Position(row + 1, column - 1).serializePosition()));
                    }
                }
            }
        }

        return moves;
    }

    private boolean addMoves(Board board, List<Move> moves, int r, int c) {
        Piece p = board.getState()[r][c];
        if (p == null) {
            moves.add(Move.moveTo(this.currentPosition.serializePosition(),
                    new Position(r, c).serializePosition()));
            return true;
        } else {
            return false;
        }
    }
}