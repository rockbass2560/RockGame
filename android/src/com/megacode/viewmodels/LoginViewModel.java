package com.megacode.viewmodels;

import android.app.Application;

import com.megacode.models.database.Usuario;
import com.megacode.repositories.ConexionRepository;
import com.megacode.repositories.LoginRepository;
import com.megacode.repositories.UsuarioRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;
    private ConexionRepository conexionRepository;
    private UsuarioRepository usuarioRepository;
    private MutableLiveData<Usuario> usuarioMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        conexionRepository = new ConexionRepository(application);
        usuarioRepository = new UsuarioRepository(application);
        usuarioMutableLiveData = loginRepository.getUsuarioLiveData();
    }

    public MutableLiveData<Usuario> loginUsuario(String email, String contrasena){
        return loginRepository.loginUsuario(email, contrasena);
    }

    public void registrarNuevaConexion(){
        conexionRepository.nuevaConexion();
    }

    public MutableLiveData<Usuario> registrarUsuario(Usuario usuario){
        return loginRepository.registrar(usuario);
    }

    public MutableLiveData<Usuario> getUsuarioMutableLiveData(){
        return usuarioMutableLiveData;
    }

    public Usuario getUsuario(){
        return usuarioRepository.obtenerUsuarioSync();
    }
}
