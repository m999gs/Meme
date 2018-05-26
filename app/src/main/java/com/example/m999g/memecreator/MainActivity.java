package com.example.m999g.memecreator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity implements TopSectionFragment.TopSectionInterface {


    private static final int READ_MEMORY_PERMISSIONS_REQUEST = 1;
    private static int PICK_IMAGE = 1;
    private static Activity activity;
    private static BottomPictureFragment bottomPictureFragment;

    public static int extraSmall= 18;
    public static int small= 22;
    public static int medium= 28;
    public static int large= 32;
    public static int extraLarge= 36;

    //image scaling types
    private static ImageView.ScaleType centerCrop = ImageView.ScaleType.CENTER_CROP;
    private static ImageView.ScaleType center = ImageView.ScaleType.CENTER;
    private static ImageView.ScaleType centerInside = ImageView.ScaleType.CENTER_INSIDE;
    private static ImageView.ScaleType fitcenter = ImageView.ScaleType.FIT_CENTER;

    @Override
    public void createMeme(String top, String Bottom) {
        bottomPictureFragment = (BottomPictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        bottomPictureFragment.setMemeText(top, Bottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissionToAccessStorage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BottomPictureFragment ob = new BottomPictureFragment();
        switch (item.getItemId()) {
            case R.id.White:
                ob.WhiteColor();
                return true;
            case R.id.Black:
                ob.BlackColor();
                return true;
            case R.id.centerCrop:
                ob.scaleTypeMethod(centerCrop);
                return true;
            case R.id.fitCenter:
                ob.scaleTypeMethod(fitcenter);
                return true;
            case R.id.Center:
                ob.scaleTypeMethod(center);
                return true;
            case R.id.centerInside:
                ob.scaleTypeMethod(centerInside);
                return true;
            case R.id.addImage:
                addImageMethod();
                return true;
            case R.id.extraSmall:
                ob.setTextSize(extraSmall);
                return true;
            case R.id.small:
                ob.setTextSize(small);
                return true;
            case R.id.Medium:
                ob.setTextSize(medium);
                return true;
            case R.id.large:
                ob.setTextSize(large);
                return true;
            case R.id.extraLarge:
                ob.setTextSize(extraLarge);
                return true;
            case R.id.saveImage:
                if (bottomPictureFragment==null){
                    Toast.makeText(this,"Please add some text",Toast.LENGTH_SHORT).show();
                }
                else{
                    activity = bottomPictureFragment.getActivity();
                    takeAndSaveScreenShot(activity);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void addImageMethod() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageuri = data.getData();
            BottomPictureFragment ob = new BottomPictureFragment();
            ob.setImage(imageuri);

        }
    }

    public void takeAndSaveScreenShot(Activity mActivity) {
        View MainView = mActivity.getWindow().getDecorView();
        MainView.setDrawingCacheEnabled(true);
        MainView.buildDrawingCache();
        Bitmap MainBitmap = MainView.getDrawingCache();
        Rect frame = new Rect();

        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //to remove statusBar from the taken sc
        int statusBarHeight = frame.centerY() - 330;
        //using screen size to create bitmap
        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap OutBitmap = Bitmap.createBitmap(MainBitmap, 0, statusBarHeight, width, height - statusBarHeight);
        MainView.destroyDrawingCache();
        try {

            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            //you can also using current time to generate name
            String name = "Mohit";
            File file = new File(path, name + ".png");
            fOut = new FileOutputStream(file);

            OutBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            Toast.makeText(getApplicationContext(), "image Saved", Toast.LENGTH_SHORT).show();

            fOut.flush();
            fOut.close();

            //this line will add the saved picture to gallery
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToAccessStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_MEMORY_PERMISSIONS_REQUEST);
        }
    }

}
