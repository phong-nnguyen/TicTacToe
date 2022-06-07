package test.src;

import java.util.Scanner;
public class test {

    public static int userInputs(){
        Scanner Input = new Scanner(System.in);
        return Input.nextInt() - 1;
    }

    public static class aiMove{
        int row, col;
    }

    public static void initializeBoard(char[][] textOut) {
        for (int x = 0; x < 3; x++) {
            for (int i = 0; i < 3; i++) {
                textOut[x][i] = '.';
            }
        }

    }

    public static void printBoard(char[][] textOut){
        for (int x = 0; x < 3; x++){
            for (int i = 0; i < 3; i++) {
                System.out.print(textOut[x][i]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public static boolean changeBoard (char[][] textOut, int x, int a) {
        if (x % 2 == 0) {
            if (textOut[a / 3][a % 3] != 'o') {
                if (textOut[a / 3][a % 3] != 'x') {
                    textOut[a / 3][a % 3] = 'x';
                    printBoard(textOut);
                    return true;
                } else {
                    printBoard(textOut);
                    System.out.println("Selected index already has a value");
                    return false;
                }
            } else {
                printBoard(textOut);
                System.out.println("Selected index already has a value");
                return false;
            }
        } else {
            if (textOut[a / 3][a % 3] != 'x') {
                if (textOut[a / 3][a % 3] != 'o') {
                    textOut[a / 3][a % 3] = 'o';
                    printBoard(textOut);
                    return true;
                } else {
                    printBoard(textOut);
                    System.out.println("Selected index already has a value");
                    return false;
                }
            } else {
                printBoard(textOut);
                System.out.println("Selected index already has a value");
                return false;
            }
        }
    }

    public static int winCon(char [][] board){

        //Check row
        for (int x = 0; x<3; x++){
            if(board[0][x] == board[1][x] && board[1][x] == board[2][x]){
                if(board[0][x] == 'x'){
                    return -10;
                } else if(board[0][x] == 'o'){
                    return 10;
                }
            }
        }

        //Check column
        for (int x = 0; x<3; x++){
            if(board[x][0] == board[x][1] && board[x][1] == board[x][2]){
                if(board[x][0] == 'x'){
                    return -10;
                } else if(board[x][0] == 'o'){
                    return 10;
                }
            }
        }

        //Check diagonal
        int a = board[0][0];

        for(int x=0; x<3; x++){
            if(board[x][x] == '.'){
                break;
            } else if(board[x][x] != a){
                break;
            } else if(x==2){
                if(a == 'x'){
                    return -10;
                } else if(a == 'o'){
                    return 10;
                }

            }
        }

        //Check anti-diagonal\
        int b = board[0][2];

        for(int x=0; x< 3; x++){
            if (board[x][2-x] == '.'){
                break;
            } else if(board[x][2-x] != b){
                break;
            } else if (x == 2){
                if(b == 'x'){
                    return -10;
                } else if (b == 'o'){
                    return 10;
                }
            }
        }
        return 0;
    }

    static boolean movesLeft(char[][] board){
        for(int x = 0 ; x < 3; x++){
            for(int y = 0 ; y < 3 ; y++){
                if(board[x][y] == '.'){
                    return true;
                }
            }
        }
        return false;
    }


    public static int minimax(char[][] board, int depth, boolean isMax){

        int score = winCon(board);

        if(score == 10) {
            return score;
        }
        if(score == -10){
            return score;
        }
        if(movesLeft(board) == false){
            return 0;
        }

        if(isMax){
            int maxEval = -1000;
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    if(board[x][y] == '.'){
                        board[x][y] = 'o';
                        maxEval = Math.max(maxEval, minimax(board, depth + 1, !isMax));
                        board[x][y] = '.';
                    }
                }
            }
            return maxEval;
        } else{
            int minEval = 1000;
          for(int x = 0; x < 3; x++){
              for(int y = 0; y < 3; y++){
                  if(board[x][y] == '.'){
                      board[x][y] = 'x';
                      minEval = Math.min(minEval, minimax(board, depth + 1, !isMax));
                      board[x][y] = '.';
                  }
              }
          }
          return minEval;
        }
    }

    public static aiMove findBestMove(char[][] board){
        int maxEval = -1000;
        aiMove bestMove = new aiMove();
        bestMove.row = -1;
        bestMove.col = -1;

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(board[x][y] == '.'){
                    board[x][y] = 'o';
                    int eval = minimax(board, 0, false);
                    board[x][y] = '.';
                    if(eval > maxEval){
                        bestMove.row = x;
                        bestMove.col = y;
                        maxEval = eval;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void gameContinuation(char[][] board) {

            for (int x = 0; x < 9; x++) {
                if(x % 2 == 0){
                    if(changeBoard(board, x, userInputs()) == false){
                        x--;
                    }
                } else if(x % 2 != 0){
                    aiMove test = findBestMove(board);
                    board[test.row][test.col] = 'o';
                    printBoard(board);
                    }
                if (winCon(board) == -10){
                    System.out.println("Player X has won");
                    System.exit(0);
                } else if(winCon(board) == 10){
                    System.out.println("Player O has won");
                    System.exit(0);
                }
            }
            System.out.println("Game has finished");
        }

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        initializeBoard(board);
        printBoard(board);
        findBestMove(board);
        gameContinuation(board);
        }
    }
