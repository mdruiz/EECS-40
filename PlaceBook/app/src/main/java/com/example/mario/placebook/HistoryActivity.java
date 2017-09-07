package com.example.mario.placebook;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class  HistoryActivity  extends  ActionBarActivity  implements  ActionMode.Callback   {
    private  ListView  mListview;
    protected  Object  mActionMode;
    public  int  selectedItem =  -1;
    private ArrayList <PlacebookEntry > mPlaceBookEntries = new ArrayList<>();
    //private ArrayList<String> SArraylist = new ArrayList<>();
    //private PlacebookEntry tempEntry;
    public  final static String PAR_KEY = "com.example.mario.placebook";
    Context context;

    @Override
    protected  void  onCreate(Bundle  savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        //mPlaceBookEntries = intent.getParcelableArrayListExtra( MainActivity.VIEW_ALL_KEY );
        Bundle b = getIntent().getExtras();
        mPlaceBookEntries =  b.getParcelableArrayList(PAR_KEY);
        mListview = (ListView) findViewById(R.id.listview);
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,R.layout.row_layout, mPlaceBookEntries);
        mListview.setAdapter(adapter);



        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
                if (mActionMode != null)
                    return false;
                selectedItem = position;
                mActionMode = HistoryActivity.this.startActionMode( HistoryActivity.this );
                view.setSelected(true);
                return  true;
            }
        });
    }





    @Override
    public  boolean  onCreateActionMode(ActionMode  mode , Menu  menu) {
        MenuInflater  inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.rowselection , menu);
        return  true;
    }
    @Override
    public  boolean  onPrepareActionMode(ActionMode  mode , Menu  menu) {
        return  false;
    }
    @Override
    public  boolean  onActionItemClicked(ActionMode  mode , MenuItem  item) {

        switch (item.getItemId()) {
            case R.id.action_delete_place:
//  Delete  Item
                mPlaceBookEntries.remove(selectedItem);
                Intent  intent = new  Intent();
                intent.putParcelableArrayListExtra(MainActivity.VIEW_ALL_KEY , mPlaceBookEntries);
                setResult( Activity.RESULT_OK , intent );

                mListview = (ListView) findViewById(R.id.listview);
                MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,R.layout.row_layout, mPlaceBookEntries);
                mListview.setAdapter(adapter);

                mode.finish();
                return  true;
            default: return  false;
        }
    }
    @Override
    public  void  onDestroyActionMode(ActionMode  mode) {
        mActionMode   = null;
        selectedItem =  -1;

    }
    @Override
    protected  void  onDestroy() {
        super.onDestroy();
        Intent  intent = new  Intent();
        intent.putParcelableArrayListExtra(MainActivity.VIEW_ALL_KEY , mPlaceBookEntries);
        setResult( Activity.RESULT_OK , intent );
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<PlacebookEntry> {
        private final Context context;
        private final List<PlacebookEntry> mAdaptedPlacebookEntries;

        public MySimpleArrayAdapter(Context context, int Resource,List<PlacebookEntry> object) {
            super(context, Resource, object);
            this.context = context;
            this.mAdaptedPlacebookEntries = object;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //mAdaptedPlacebookEntries.get(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.row_txtPlace);
            TextView textDesc = (TextView) rowView.findViewById(R.id.row_txtPlaceDesc);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.row_image_view);
            textView.setText(mPlaceBookEntries.get(position).getName());
            textDesc.setText(mPlaceBookEntries.get(position).getDescription());
                if(mPlaceBookEntries.get(position).getPhotoPath() != null )
                {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mPlaceBookEntries.get(position).getPhotoPath(), options);
                //int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth())  ) ;
                int nh = (int) (bitmap.getHeight() / 8);
                int nw = (int) (bitmap.getWidth() / 8);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, nw, nh, true);
                imageView.setImageBitmap(scaled);
                }
            return rowView;
        }
    }

}
