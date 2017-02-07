package ar.edu.utn.frsf.isi.dam.del2016.heymozo.imagenes;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Imagen;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

public class ImageLoader {
     
    private MemoryCache memoryCache = new MemoryCache();
    private FileCache fileCache;
    private Map<Producto, String> imageViews=Collections.synchronizedMap(new WeakHashMap<Producto, String>());
    private ExecutorService executorService;
    private CargarImagenListener listener;
    private Producto producto;
    private int posicion;

    public static final int OK = 0;
    public static final int ERROR = 1;
    private int status = OK;
     
    public ImageLoader(Context context){
        fileCache=new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }

    public void cargarImagen(String url, Producto producto, int posicion, CargarImagenListener listener)
    {
        Log.i("IMAGELOADER","Cargando imagen " + url);
        this.listener = listener;
        this.producto = producto;
        imageViews.put(producto, url);
        Imagen imagen = memoryCache.get(url);
        if(imagen != null) {
            producto.setImagen(imagen);
            status = OK;
            listener.imagenCargada(producto, posicion, status);
            Log.i("IMAGELOADER",url + "Imagen cargada desde Memory Cache");
        }
        else
        {
            encolarPedido(url, producto);
        }
    }
         
    private void encolarPedido(String url, Producto producto)
    {
        ImagenACargar p = new ImagenACargar(url, producto);
        executorService.submit(new CargadorDeImagenes(p));
    }
     
    private Imagen getImagen(String url) throws IOException {
        File f = fileCache.getFile(url);
         
        //from SD cache
        Imagen i = decodeFile(f);
        if(i!=null) {
            Log.i("IMAGELOADER",url + "Imagen cargada desde SD Cache");
            return i;
        }
         
        //from web
        try {
            Imagen imagen;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            InputStream is = new BufferedInputStream(conn.getInputStream());
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();

            imagen = decodeFile(f);
            Log.i("IMAGELOADER",url + "Imagen cargada desde el Servidor");
            return imagen;
        } catch (Throwable ex){
           ex.printStackTrace();
           if(ex instanceof OutOfMemoryError)
               memoryCache.clear();
           return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Imagen decodeFile(File f) {

        try {
            InputStreamReader isw = new InputStreamReader(new FileInputStream(f));
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
            Gson gson = new Gson();
            Imagen imagen = gson.fromJson(sb.toString(), Imagen.class);
            Log.v("imagen_json:", sb.toString());

            byte[] imagenBytes = Base64.decode(imagen.getImagen64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
            imagen.setImagen(bitmap);

            return imagen;
        }
        catch (IOException exception){
            //TODO
        }
        return null;
    }
     
    //Task for the queue
    private class ImagenACargar
    {
        public String url;
        Producto producto;
        ImagenACargar(String u, Producto p){
            url=u;
            producto=p;
        }
    }
     
    private class CargadorDeImagenes implements Runnable {
        ImagenACargar photoToLoad;
        CargadorDeImagenes(ImagenACargar photoToLoad){
            this.photoToLoad=photoToLoad;
        }
         
        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            Imagen imagen = null;
            try {
                imagen = getImagen(photoToLoad.url);
            } catch (IOException e) {
                //TODO
            }
            memoryCache.put(photoToLoad.url, imagen);
            if(imageViewReused(photoToLoad))
                return;
            producto.setImagen(imagen);
            status = OK;
            listener.imagenCargada(producto, posicion, status);
        }
    }
     
    private boolean imageViewReused(ImagenACargar photoToLoad){
        String tag=imageViews.get(photoToLoad.producto);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    //TODO borrar
    /*
    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImagenACargar photoToLoad;
        public BitmapDisplayer(Bitmap b, ImagenACargar p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
        }
    }
    */

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
 
}