package com.rockbass2560.megacode.models.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.rockbass2560.megacode.models.Usuario;

public class NivelTerminado {
    @Exclude
    public String id;
    public int nivelId;
    public boolean terminado;
    public int puntaje;

    public NivelTerminado(){

    }
}
