package com.example.gilman.minesweeperv01;

import android.content.Context;
import android.widget.Button;

public class mines extends android.support.v7.widget.AppCompatButton {
    int value;
    int index_X, index_Y;
    boolean isRevealed;
    boolean flag;



    public mines(Context context) {
        super(context);
        value=0;
        isRevealed=false;
        flag=false;
    }

    public void setValue(int val){
        this.value=val;
    }

    public int getIndexX(){
        return this.index_X;
    }

    public int getIndexY(){
        return this.index_Y;
    }

    public void increment(){
        this.value++;
    }

    public int getValue(){
        return this.value;
    }

    public void toggleRevealed(){

            this.isRevealed=true;

    }

    public boolean isFlagged(){
        return flag;
    }

    public void setFlag(){
        if(isFlagged())
            flag=false;
        flag=true;
    }





    public void setOnClickListener() {

    }
}
