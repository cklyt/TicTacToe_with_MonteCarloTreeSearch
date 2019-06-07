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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lllyu
 */
public class Board {
    private  List<Cell> emptyCells;
    private  Scanner scanner;
    private CellState[][] board;
    private List<Cell> rootValues;
    public Board(){
        InitializeBoard();
    }
    private void InitializeBoard() {
        this.rootValues = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.board = new CellState[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    }
    
    public void SetupBoard(){
        for(int i =0;i<Constants.BOARD_SIZE;++i){
            for(int j =0;j<Constants.BOARD_SIZE;++j){
                board[i][j] = CellState.EMPTY;
            }
        }
    }
    
    public void Move(Cell cell,CellState player){
        this.board[cell.getX()][cell.getY()] = player;
    }
    
    public void MakeUserInpout(){
        System.out.println("User's move :");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        Cell cell = new Cell(x, y);
        Move(cell, CellState.USER);
        
    }
    
    public boolean isRunning(){
        if(isWinning(CellState.COMPUTER)) return false;
        if(isWinning(CellState.USER)) return false;
        if(getEmptyCells().isEmpty())return false;
        
        return true;
    }
    
    public boolean isWinning(CellState player){
        if(board[0][0]==player&&board[1][1]==player &&board[2][2]==player){
            return true;
        }
        if(board[0][2]==player&&board[1][1]==player &&board[2][0]==player){
            return true;
        }
        for(int i =0;i<Constants.BOARD_SIZE;++i){
            if(board[i][0]==player&&board[i][1]==player&&board[i][2]==player){
                return true;
            }
            if(board[0][i]==player&&board[1][i]==player&&board[2][i]==player){
                return true;
            }
        }       
        return false;
    }  
    
    // <editor-fold desc="Mini-max Operations.">
    public void callMinimax(int depth,CellState player){
        rootValues.clear();
        Minimax(depth, player);   
    }
    
    private int Minimax(int depth,CellState player){
        if(isWinning(CellState.COMPUTER)) return +1;
        if(isWinning(CellState.USER)) return -1;
        
        List<Cell> availableCells = getEmptyCells();
        
        if(availableCells.isEmpty()) return 0;
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        List<Integer> scores = new ArrayList<>();
        for(int i =0;i<availableCells.size();++i){
            //break the loop if alpha is larger than beta
            if(alpha>beta)
                break;
            Cell point = availableCells.get(i);
            if(player==CellState.COMPUTER){
                Move(point, CellState.COMPUTER);
                int currentScore = Minimax(depth+1, CellState.USER);
                alpha = Integer.max(alpha, currentScore);   //update alpha value at maxmizing layer
              
                scores.add(currentScore);
                
                if(depth == 0){
                    point.setMinimaxValue(currentScore);
                    rootValues.add(point);
                }
            }
            else if(player == CellState.USER){
                Move(point, CellState.USER);
                int currentScore = Minimax(depth+1, CellState.COMPUTER);
                beta = Integer.min(beta, currentScore);     //update beta value at minimizing layer                
                scores.add(currentScore);
            }
            
            board[point.getX()][point.getY()]=CellState.EMPTY;
            
        }
        
        if(player == CellState.COMPUTER){
            return returnMax(scores);
        }
        
        return returnMin(scores);
    }
    
    private int returnMax(List<Integer> list){
        int max = Integer.MIN_VALUE;
        int index=Integer.MIN_VALUE;
        for(int i =0;i<list.size();++i){
            if(list.get(i)>max){
                max = list.get(i);
                index = i;
            }
        }
        
        return list.get(index);
    }
    
    private int returnMin(List<Integer> list){
        int min = Integer.MAX_VALUE;
        int index=Integer.MIN_VALUE;
        for(int i =0;i<list.size();++i){
            if(list.get(i)<min){
                min = list.get(i);
                index = i;
            }
        }
        
        return list.get(index);
    }
    
    public Cell getBestMove(){
        int max = Integer.MIN_VALUE;
        int best = Integer.MIN_VALUE;
        for(int i =0;i<rootValues.size();++i){
            if(max<rootValues.get(i).getMinimaxValue()){
                max = rootValues.get(i).getMinimaxValue();
                best = i;
            }
        }
        System.out.println(rootValues.size());
        return rootValues.get(best);
    }
    // </editor-fold>
    
    // <editor-fold desc="Utils.">  
    public Scanner getScanner() {
        return scanner;
    }
    
    public List<Cell> getEmptyCells() {
        emptyCells = new ArrayList<>();
        for(int i =0; i <Constants.BOARD_SIZE;++i){
            for(int j =0;j<Constants.BOARD_SIZE;++j){
                if(this.board[i][j]==CellState.EMPTY){
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        
        return emptyCells;
    }
    
    public CellState[][] getBoard() {
        return board;
    }
    
    public List<Cell> getRootValues() {
        return rootValues;
    }
    
    public void DisplayBoard() {
        System.out.println();

        for(int i =0;i<Constants.BOARD_SIZE;++i){
            for(int j =0;j<Constants.BOARD_SIZE;++j){
                System.out.print(this.board[i][j]+" ");
            }
            System.out.println();
        }
    }
    // </editor-fold>
}
