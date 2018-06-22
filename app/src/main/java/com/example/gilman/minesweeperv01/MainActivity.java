package com.example.gilman.minesweeperv01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  View.OnLongClickListener, View.OnClickListener  {

    ArrayList<LinearLayout> rows;
    LinearLayout root;
    public int size=7;
    public int m, n;
    public int NO_OF_MINES=5;
    //Button button;
    public mines[][] board;
    int[] X_arr={-1, -1, -1, 0, 0, 1, 1, 1};
    int[] Y_arr={-1, 0, 1, -1, 1, -1, 0, 1};
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root=findViewById(R.id.rootLayout);
        random=new Random();
        setupBoard();
    }

    public void setupBoard(){
        Log.d("MainActivity", "InMainActivity");
        rows=new ArrayList<>();
        board=new mines[size][size];
        root.removeAllViews();

        for(int i=0;i<size;i++){
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            root.addView(linearLayout);
            rows.add(linearLayout);
        }

        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                mines mine = new mines(this);
                mine.setOnLongClickListener(this);
                mine.setOnClickListener(this);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                mine.setLayoutParams(layoutParams);
                LinearLayout row=rows.get(i);
                row.addView(mine);
                mine.index_X = i;
                mine.index_Y = j;
                board[i][j]=mine;

            }
        }

        int i=0;
        while(i<NO_OF_MINES){
            int x=getRandom(size);
            int y=getRandom(size);
            if (board[x][y].getValue()!= -1) {
                board[x][y].setValue(-1);
                //board[x][y].setCompoundDrawablesWithIntrinsicBounds(R.drawable.mine,0,0,0);
                board[x][y].setText("-1");
                i++;

            }

        }

        for(int ii = 0; ii < size; ++ii){
            for(int j = 0; j < size; ++j){

                setNeighbours(board[ii][j]);

            }
        }
    }

    public void show(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                board[i][j].setText(board[i][j].getValue()+"");
        }
    }

    public int getRandom(int num){

        return random.nextInt(num);
    }

    public void setNeighbours(mines mine) {
        int x = mine.getIndexX();
        int y = mine.getIndexY();


        //int  m, n;
        if (mine.getValue() == -1) {
            for (int i = 0; i < X_arr.length; i++) {
                m = x + X_arr[i];
                n = y + Y_arr[i];
                if (checkBound(m, n) && board[m][n].getValue() != -1) {

                    board[m][n].increment();
                    //board[m][n].setText(board[m][n].getValue() + "");

                }
//
            }
            //show();
        }
    }


    public boolean checkBound(int x, int y){
        if((x>=0 && y>=0) && (x<size && y<size))
            return true;


        return false;
    }


    @Override
    public void onClick(View view) {
        mines mine=(mines) view;
        revealTiles(mine);

    }


    @Override
    public boolean onLongClick(View view) {
        Log.d("longClick", "In Long Click");
        mines mine=(mines) view;
        if(mine.isFlagged()){
            mine.setFlag();
            mine.setText(mine.getValue()+"");
        }
        else{
            mine.setFlag();
            mine.setText("F");
        }

        return true;
    }

    public void revealTiles(mines mine){
        int x = mine.getIndexX();
        int y = mine.getIndexY();

        if(mine.getValue()==-1){
            Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
            //for(int i=0;i<.0)
            displayMines();
            setFalse();
        }
        else if(mine.isFlagged()){
            return;
        }
        else if(mine.getValue()>0 && !mine.isRevealed){
            mine.setText(mine.getValue()+"");
            mine.isRevealed = true;
            mine.setEnabled(false);
            //mine.toggleRevealed();
        }
        else if(mine.getValue()==0){


            for (int i = 0; i < X_arr.length; i++) {
                int m = x + X_arr[i];
                int n = y + Y_arr[i];
                if(checkBound(m,n)) {
                    //board[m][n].isRevealed = true;
                    //if (board[m][n].getValue() != -1) {

                        if (board[m][n].getValue() == 0 && !board[m][n].isRevealed) {
                            board[m][n].isRevealed = true;
                            revealTiles(board[m][n]);

                        } else if (board[m][n].getValue() > 0) {

                            board[m][n].setText(board[m][n].getValue() + " ");
                            board[m][n].isRevealed = true;
                            board[m][n].setEnabled(false);
                        }

                    //}
                    board[m][n].isRevealed=true;
                }
                //board[m][n].isRevealed = true;
            }
            mine.isRevealed=true;
        }
    }

    public void setFalse(){
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                board[i][j].setEnabled(false);
    }

    public void displayMines(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j].getValue()==-1)
                    board[i][j].setText("-1");
            }
        }
    }
}
