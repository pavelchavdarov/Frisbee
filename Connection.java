package RGS_FRISBEE;

import RGS_COMMON_UTILS.ConnectionInterface;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.sql.Blob;

/**
 * Created by p.chavdarov on 18/01/2017.
 */


public class Connection implements ConnectionInterface {
    public void sendFile(Blob pBlob) throws Exception{}
    public Blob getFile() throws Exception{return null;}

    public HttpURLConnection getConnection(String pUrl, String pMethod, String pContentType) throws Exception{
        URL url = null;
        // пока заглушка
        //proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.17.46", 8080));
        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.5.19", 8888));
//        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.101.20.32", 3128));
        System.err.println("Connecting to " + pUrl + " ...");
        url = new URL(pUrl);

        conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod(pMethod);
        if(!pMethod.equals("GET")){
            conn.setRequestProperty("Content-Type", pContentType/*"application/json"*/);
            if(pContentType.equals("application/json"))
                conn.setRequestProperty("charset", "utf-8");
        }
        System.err.println("Connection prepared...");
        return conn;
    }
}
