package com.example.the_start;

import com.example.the_start.R;

public class btn_Class {

    private int index;
    private boolean isClicked;
    private int btn_score;
    private int bg_clicked1= R.color.blue;
    private int bg_clicked2=R.color.red;

    public btn_Class(int index,boolean isClicked){
        this.index=index;
        this.isClicked=isClicked;

    }

    public boolean getCLicked(){
        return isClicked;
    }
    public int getIndex(){
        return index;
    }
    //public void setIndex(int index){
     //   this.index=index;
   // }
    public int getBtn_score(){
        return btn_score;
    }
    public void setBtn_score(int btn_score){
        this.btn_score=btn_score;
    }


}



