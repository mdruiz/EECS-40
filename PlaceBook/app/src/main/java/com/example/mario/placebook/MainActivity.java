package com.example.mario.placebook;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public   static  final  String  VIEW_ALL_KEY      = "com.example.mario.placebook.EXTRA_VIEW_ALL";
    private  static  final  int     REQUEST_VIEW_ALL = 1005;
    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    private static final int REQUEST_PLACE_PICKER = 1003;
    private static final int TAKE_PHOTO =1;
    private ArrayList <PlacebookEntry> mPlaceBookEntries = new ArrayList<>();
    public File photoFile;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView mLatitudeText, mLongitudeText,mAltitudeText;
    String Photop,Descript,naming;
    public static int i=0;
    public static int k=0;
    TextView trial;
    public EditText NameField, DescField;
    public  final static String PAR_KEY = "com.example.mario.placebook";
    public String Placebookphoto;


    //  Call  dispatchViewAllPlaces()  when  its  menu  command  is  selected.
    private  void  dispatchViewAllPlaces()
    {
        Intent intent = new  Intent(this , HistoryActivity.class);

        intent.putParcelableArrayListExtra(VIEW_ALL_KEY , mPlaceBookEntries);
        intent.putExtra(PAR_KEY, mPlaceBookEntries);
        try
        {
            startActivityForResult(intent , REQUEST_VIEW_ALL);
        } catch (ActivityNotFoundException a) {}
    }

    @Override
    protected  void  onActivityResult(int  requestCode , int  resultCode , Intent  data)
    {
        if ( resultCode  ==  RESULT_OK  &&  requestCode  ==  REQUEST_VIEW_ALL  && data != null )
        {
            ArrayList <PlacebookEntry > placebookEntrys = data.getParcelableArrayListExtra( VIEW_ALL_KEY );
            mPlaceBookEntries = placebookEntrys;
        }

        if ( resultCode  ==  RESULT_OK  &&  requestCode  ==  REQUEST_IMAGE_CAPTURE   ) {
//  Save  previously  generated  unique  file  path  in  current  Placebook  entry
            Photop=String.valueOf(photoFile);
            //System.out.println("Printing out the photo file path : "+Photop);
        }

        if ( resultCode == RESULT_OK && requestCode == REQUEST_PLACE_PICKER && data != null ) {
            Place place = PlacePicker . getPlace ( data , this ) ;
            Descript=String.valueOf(place.getAddress());
            System.out.println(Descript);
            NameField.setText(String.valueOf(place.getAddress()));
        }
    }

    public void camera(View view)
    {
        dispatchTakePictureIntent();
    }

    public void map(View view){
        launchPlacePicker();
    }
    public void coordinate (View view){
        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        mAltitudeText.setText(String.valueOf(mLastLocation.getAltitude()));
    }
    // Call in itG oo gl eA pi () from MainActivity . onCreate ()
    private void initGoogleApi () {
        
        mGoogleApiClient = new GoogleApiClient
                . Builder ( this )
                . addApi ( Places. GEO_DATA_API )
                . addApi ( Places . PLACE_DETECTION_API )
                . addApi(LocationServices.API)
                . addConnectionCallbacks (this)
                . addOnConnectionFailedListener (this)
                . build ();
        mGoogleApiClient.connect();
    }
    // Call l a u n c h P l a c e P i c k e r () when the Pick -A - Place button is clicked .
    private void launchPlacePicker () {
        PlacePicker. IntentBuilder builder = new PlacePicker . IntentBuilder () ;
        Context context = getApplicationContext () ;
        try {
            startActivityForResult ( builder . build ( context ) , REQUEST_PLACE_PICKER );
        } catch ( GooglePlayServicesRepairableException e) {
// Handle exception - Display a Toast message
        } catch ( GooglePlayServicesNotAvailableException e ) {
// Handle exception - Display a Toast message
        }
    }

    private void dispatchTakePictureIntent()
    {
        Intent  takePictureIntent = new  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//  Ensure  that  there's a  camera  activity  to  handle  the  intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            k++;
            Placebookphoto = ("Hello"+k+".jpg");
            photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Placebookphoto);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getApplicationContext(),"Camera not found",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGoogleApi();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = (TextView) findViewById(R.id.txtGpsLongitudeContent);
            mLongitudeText = (TextView) findViewById(R.id.txtGpsLatitudeContent);
            mAltitudeText = (TextView) findViewById(R.id.txtGpsAltitudeContent);
            NameField = (EditText) findViewById(R.id.txtPlaceContent);
            DescField = (EditText) findViewById(R.id.edit_place_desc);
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public  boolean  onCreateOptionsMenu(Menu  menu)
    {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return  super.onCreateOptionsMenu(menu);
    }
    @Override
    public  boolean  onOptionsItemSelected(MenuItem  item)
    {
        switch ( item.getItemId() ) {
            case R.id.action_settings :
/** Code  to  show  settings    **/
                ; return  true;
            case R.id.action_new_place:
/** Code  to  add a new  place  **/
                Addnewplace();
                ; return  true;
            case R.id.action_view_all :
/** Code  to  show  all  places  **/
                dispatchViewAllPlaces();
                return  true;

            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    private void Addnewplace()
    {
        PlacebookEntry NewItem = new PlacebookEntry();
        NewItem.setPhotoPath(Photop);
        String tempDescript = String.valueOf(DescField.getText());
        NewItem.setDescription(tempDescript);
        String tempName = String.valueOf(NameField.getText());
        NewItem.setName(tempName);
        NewItem.setId(i);
        i++;
        mPlaceBookEntries.add(NewItem);

        Intent mIntent = new Intent(this,HistoryActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(PAR_KEY, NewItem);
        mIntent.putExtra(PAR_KEY,mPlaceBookEntries);

        //startActivity(mIntent);
    }


}
