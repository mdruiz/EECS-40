package com.example.mario.placebook;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gaurav Venkatesh on 5/27/2015.
 */
public  class  PlacebookEntry  implements Parcelable {
    public   long     id;
    private        String   name;
    private        String   description;
    private        String   photoPath;

    public  PlacebookEntry( Parcel source )
    {
        this.id            = source.readLong();
        this.name          = source.readString();
        this.description = source.readString();
        this.photoPath    = source.readString();
    }
    public  PlacebookEntry()
    {
    }

    public String getName(){return this.name;}
    public String getDescription() {return this.description;}
    public String getPhotoPath() {return this.photoPath;}
    public long getId(){return this.id;}


    public void setName(String names ) {this.name = names; }
    public void setPhotoPath(String paths)
    {
        this.photoPath=paths;
    }
    public void setDescription(String desc)
    {
        this.description = desc;
    }
    public void setId(int i){ this.id = i;}

    @Override
    public  void  writeToParcel(Parcel  dest , int  flags)
    {
        dest.writeLong   (this.id                );
        dest.writeString(this.name.toString());
        dest.writeString(this.description     );
        dest.writeString(this.photoPath       );
    }
    @Override
    public  int  describeContents()
    {
        return  0;
    }


    public  static  final  Parcelable.Creator <PlacebookEntry > CREATOR = new  Parcelable.Creator <PlacebookEntry >()
    {
        @Override
        public  PlacebookEntry  createFromParcel(Parcel  source)
        {
            return  new  PlacebookEntry(source);
        }
        @Override
        public  PlacebookEntry[]  newArray(int  size)
        {
            return  new  PlacebookEntry[size];
        }
    };
}
