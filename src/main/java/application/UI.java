package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static chess.Color.BLACK;
import static chess.Color.RED;
import static java.lang.System.out;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		out.print("\033[H\033[2J");
		out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
		}
	}

	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		out.println();
		printCapturedPieces(captured);
		out.println();
		out.println("Turn : " + chessMatch.getTurn());
		if (!chessMatch.getCheckMate()) {
			out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if (chessMatch.getCheck()) {
				out.println("CHECK!");
			}
		} else {
			out.println("CHECKMATE!");
			out.println("Winner: " + chessMatch.getCurrentPlayer());
		}
	}

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			out.println();
		}
		out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			out.println();
		}
		out.println("  a b c d e f g h");
	}

	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == RED) {
				out.print(ANSI_RED + piece + ANSI_RESET);
			} else {
				out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		out.print(" ");
	}

	private static void printCapturedPieces(List<ChessPiece> captured) {
		out.println("Captured pieces:");
		out.print("Red: ");
		out.print(ANSI_RED);
		out.println(Arrays.toString(captured.stream().filter(x -> x.getColor() == RED).toArray()));
		out.print(ANSI_RESET);
		out.print("Black: ");
		out.print(ANSI_YELLOW);
		out.println(Arrays.toString(captured.stream().filter(x -> x.getColor() == BLACK).toArray()));
		out.print(ANSI_RESET);
	}
}