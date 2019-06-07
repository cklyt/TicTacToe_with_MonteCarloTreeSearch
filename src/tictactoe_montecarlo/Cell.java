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

/**
 *
 * @author lllyu
 */
public class Cell {
    private int x;
    private int y;
    private  int minimaxValue;
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMinimaxValue(int minimaxValue) {
        this.minimaxValue = minimaxValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMinimaxValue() {
        return minimaxValue;
    }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
