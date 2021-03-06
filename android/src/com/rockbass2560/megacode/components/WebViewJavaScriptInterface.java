package com.rockbass2560.megacode.components;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.rockbass2560.megacode.Comando;
import com.rockbass2560.megacode.Level;
import com.rockbass2560.megacode.others.CustomCallback;
import com.rockbass2560.megacode.views.fragments.GameFragment;

import java.util.ArrayList;
import java.util.List;

public class WebViewJavaScriptInterface {

    private final static String TAG = WebViewJavaScriptInterface.class.getName();
    private GameFragment libgdxFragment;
    private List<Character> ultimoCodigoGenerado;
    private CustomCallback<String> callback;
    private CustomCallback<Void> callbackTimerStoped;

    public WebViewJavaScriptInterface(){
        ultimoCodigoGenerado = new ArrayList<>();
    }

    public void changeLibgdxFragment(GameFragment libgdxFragment){
        this.libgdxFragment = libgdxFragment;
    }

    public void generarCodigoBlockly(CustomCallback<String> callback){
        this.callback = callback;
    }

    @JavascriptInterface
    public void codigoBlocklyGenerado(String codigoGenerado){
        //eliminar los highlightBlock
        StringBuilder generado = new StringBuilder();
        for(String linea: codigoGenerado.split("\n")){
            if (!linea.trim().startsWith("highlightBlock")) {
                generado.append(linea);
                generado.append("\n");
            }
        }
        callback.processResponse(generado.toString());
    }

    @JavascriptInterface
    public boolean respawn(){
        return libgdxFragment.getGamePlayScreen().level.megaCode.respawn;
    }

    @JavascriptInterface
    public boolean enemigoDeFrente(){
        Level level = libgdxFragment.getGamePlayScreen().level;

        return level.enemyInFront;
    }

    @JavascriptInterface
    public boolean juegoTerminado(){
        return libgdxFragment.getGamePlayScreen().level.victory;
    }

    @JavascriptInterface
    public boolean caminarDerecha(){
        return ejecutarComando(Comando.CAMINAR_DERECHA);
    }

    @JavascriptInterface
    public boolean caminarIzquierda(){
        return ejecutarComando(Comando.CAMINAR_IZQUIERDA);
    }

    @JavascriptInterface
    public boolean disparar(){
        return ejecutarComando(Comando.DISPARAR);
    }

    @JavascriptInterface
    public boolean saltar(){
        return ejecutarComando(Comando.SALTAR);
    }

    @JavascriptInterface
    public boolean recibirComandos(){
        return libgdxFragment.getGamePlayScreen().level.recibirComandos;
    }

    @JavascriptInterface
    public void prepararNivel(){
        ultimoCodigoGenerado.clear();
        libgdxFragment.getGamePlayScreen().level.prepararNivel();
    }

    @JavascriptInterface
    public void finalizarNivel(){
        libgdxFragment.getGamePlayScreen().ejecucionCompletada();
    }

    @JavascriptInterface
    public void timerStoped(){
        if (callbackTimerStoped!=null){
            callbackTimerStoped.processResponse(null);
        }
    }

    @JavascriptInterface
    public boolean reachGoal(){
        return libgdxFragment.getGamePlayScreen().level.victory;
    }

    private boolean ejecutarComando(Comando comando){
        agregarCodigo(comando);

        Level level = libgdxFragment.getGamePlayScreen().level;

        Log.d(TAG, "Comando llamado: "+comando.name());

        level.procesarComando(comando);

        return level.recibirComandos;
    }

    private void agregarCodigo(Comando comando){
        switch (comando.getValue()){
            case "izquierda":
                ultimoCodigoGenerado.add('L');
                break;
            case "derecha":
                ultimoCodigoGenerado.add('R');
                break;
            case "saltar":
                ultimoCodigoGenerado.add('J');
                break;
            case "disparar":
                ultimoCodigoGenerado.add('S');
                break;
        }
    }

    public String getUltimoCodigoGenerado(){
        return TextUtils.join("", ultimoCodigoGenerado);
    }

    public void addTimerStoped(CustomCallback<Void> callback){
        callbackTimerStoped = callback;
    }

}
