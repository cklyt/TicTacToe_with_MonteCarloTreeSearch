/*
 * Copyright 2019 lllyu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tictactoe_montecarlo;

import java.util.Random;

/**
 *
 * @author lllyu
 */
public class Game {
    private Board board;
    private Random random; 
    public Game(){
        InitializeGame();
        DisplayBoard();
        MakeFirstMove();
        PlayGame();
        CheckStatus();
    }
    
    private void PlayGame(){
        while(board.isRunning()){
            System.out.println("User move:");
            Cell userCell = new Cell(board.getScanner().nextInt(), board.getScanner().nextInt());
            if(!ValidateMove(userCell)){
                System.out.println("Not Valid Move!TryAgain:");
                continue;
            }
            board.Move(userCell,CellState.USER);
            board.DisplayBoard();           
            
            if(!board.isRunning()){
                break;
            }
            
            board.callMinimax(0,CellState.COMPUTER);
            
            for(Cell cell :board.getRootValues()){
                System.out.println("Cell values: "+cell+" minimaxValue:"+cell.getMinimaxValue());
            }
            
            board.Move(board.getBestMove(),CellState.COMPUTER);
            board.DisplayBoard();
        }
        
    }
    
    private void CheckStatus(){
        if(board.isWinning(CellState.COMPUTER))
            System.out.println("Computer has won...");
        else if(board.isWinning(CellState.USER))
            System.out.println("User has won...");
        else
            System.out.println("It is a draw...");
    }
    
    private void InitializeGame() {
        this.board = new Board();
        this.random = new Random();
        this.board.SetupBoard();
    }
    
    private boolean ValidateMove(Cell cell){
        if(cell.getX()>=Constants.BOARD_SIZE||cell.getY()>=Constants.BOARD_SIZE||cell.getX()<0||cell.getY()<0){
            return false;
        }
        
        if(board.getBoard()[cell.getX()][cell.getY()]!=CellState.EMPTY){
            return false;
        }
        return true;
    }
    
    private void DisplayBoard() {
        this.board.DisplayBoard();
    }

    private void MakeFirstMove() {
        System.out.println("Who starts? 1- Computer; 2 - User");
        int choice = board.getScanner().nextInt();
        
        if(choice ==1){
            //Cell cell =new Cell(random.nextInt(Constants.BOARD_SIZE), random.nextInt(Constants.BOARD_SIZE));
            Cell cell =new Cell(0, 0);
            board.Move(cell,CellState.COMPUTER);
            board.DisplayBoard();
        }
    }
}
