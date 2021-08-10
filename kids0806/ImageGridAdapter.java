package com.example.kids0806;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

// 앨범 adapter

public class ImageGridAdapter extends BaseAdapter {
    String[] ran_img2;
    Context context = null;
    String[] filename1;

    ArrayList<listVO> grid_list = new ArrayList<>();


    // ----------------------------------------------------
    // imageIDs는 이미지 파일들의 리소스 ID들을 담는 배열입니다.
    // 이 배열의 원소들은 자식 뷰들인 ImageView 뷰들이 무엇을 보여주는지를
    // 결정하는데 활용될 것입니다.

    int[] imageIDs = null;

    public ImageGridAdapter(Context context, int[] imageIDs){
        this.context = context;
        this.imageIDs = imageIDs;
    }

    @Override
    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();


        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.simple_image, parent, false);
        }

        ImageView imageView1 = convertView.findViewById(R.id.imageView);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams clsParam = convertView.getLayoutParams( );
        clsParam.width = 255;
        clsParam.height = 255;
        convertView.setLayoutParams( clsParam );


        listVO list = grid_list.get(position);
        imageView1.setImageDrawable(list.getGridview());

//        ImageClickListener imageViewClickListener
//                = new ImageClickListener(context, ran_img2[position]);
//        imageView1.setOnClickListener(imageViewClickListener);
//
        return convertView;
    }

    public void addview(Drawable icon){
        listVO listViewItem = new listVO();

        listViewItem.setGridview(icon);

        grid_list.add(listViewItem);
    }


}