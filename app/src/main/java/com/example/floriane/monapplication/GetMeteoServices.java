package com.example.floriane.monapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by floriane on 19/12/16.
 */

public class GetMeteoServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO2 = "com.example.floriane.monapplication.action.FOO2";
    private static final String ACTION_BAZ2 = "com.example.floriane.monapplication.action.BAZ2";

    // TODO: Rename parameters

    public GetMeteoServices() {
        super("GetMeteoServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionMeteo(Context context) {
        Intent intent = new Intent(context, GetMeteoServices.class);
        intent.setAction(ACTION_FOO2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO2.equals(action)) {
                handleActionMeteo();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionMeteo() {
        Log.d(getString(R.string.sc1), "Thread service neme:" + Thread.currentThread().getName());
        URL url = null;
        try{
            url = new URL("http://www.infoclimat.fr/public-api/gfs/json?_ll=44.08588,3.06071&_auth=BR9XQAR6VHYALVRjVSNSewRsV2JcKgUiAX0CYQ1kUC0EZ1U5VTNTOVE2Uy5UewUyU34CYg82ADgGYlcxAHJSLgVlVzMEZVQ0AGlUNlVtUnkEKFcqXGIFIgF9AmwNY1AtBGRVMFUoUzNROFMvVGEFOVN%2FAn0PMQA8Bm1XMgBsUjEFZVczBGVUMwBwVClVYFJuBDJXMFxiBTUBNAJkDWRQZQRhVTRVNVM5USBTM1RjBTFTYgJmDzcAOgZhVy8AclJIBRVXLgQnVHQAOlRwVXhSMwRpV2M%3D&_c=d821b133115cba0b2369fcb901cdad13");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "meteo.json"));
                Log.d(getString(R.string.sc1), "Meteo json downloaded !");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.METEO_UPDATE));
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.

     private void handleActionBaz(String param1, String param2) {
     // TODO: Handle action Baz
     throw new UnsupportedOperationException("Not yet implemented");
     }*/
}

