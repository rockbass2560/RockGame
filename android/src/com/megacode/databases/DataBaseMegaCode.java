package com.megacode.databases;

import android.content.Context;

import com.megacode.dao.UsuarioDao;
import com.megacode.models.database.Usuario;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class}, version = 1)
public abstract class DataBaseMegaCode extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();

    private static DataBaseMegaCode INSTANCE;

    public static DataBaseMegaCode getDataBaseMegaCode(final Context context){
        if (INSTANCE==null){
            synchronized (DataBaseMegaCode.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context, DataBaseMegaCode.class,
                            "DataBaseMegaCode").build();
                }
            }
        }

        return INSTANCE;
    }
}