package ar.edu.utn.frsf.isi.dam.del2016.heymozo.imagenes;
 
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
 
class FileCache {
     
    private File cacheDir;
     
    FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Hey_Mozo_Images_cache");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
     
    File getFile(String url) throws UnsupportedEncodingException {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        //String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        String filename = URLEncoder.encode(url,"UTF-8");
        return new File(cacheDir, filename);
    }
     
    void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
 
}