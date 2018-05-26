package com.example.m999g.memecreator;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


public class BottomPictureFragment extends Fragment{


    private static TextView topText;
    private  static TextView bottomText;
    private static ImageView imageView;
    Uri imageuri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_fragment,container,false);
        topText=view.findViewById(R.id.textView);
        bottomText=view.findViewById(R.id.textView2);
        imageView = view.findViewById(R.id.Image);
        return view;
    }
    public void setMemeText(String top,String bottom){
        topText.setText(top);
        bottomText.setText(bottom);
    }
    public void BlackColor(){
        int black = Color.BLACK;
        topText.setTextColor(black);
        bottomText.setTextColor(black);
    }
    public void WhiteColor(){
        int white = Color.WHITE;
        topText.setTextColor(white);
        bottomText.setTextColor(white);
    }
    public void setImage(Uri uri){
        imageView.setImageURI(uri);
    }
    public void scaleTypeMethod(ImageView.ScaleType scaleType){
        imageView.setScaleType(scaleType);
    }
}
