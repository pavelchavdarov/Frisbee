package RGS_FRISBEE;

import RGS_COMMON_UTILS.ConnectionInterface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.sql.Blob;

/**
 * Created by p.chavdarov on 18/01/2017.
 */


public class FBConnection implements ConnectionInterface {
    private HttpURLConnection conn;
    private Proxy proxy;

    public FBConnection(String pUrl, String pMethod, String pContentType) throws Exception {
        this.getConnection("https://37.230.211.37:3056", "POST", "text/xml");
    }

    public FBConnection() throws Exception {
        this.getConnection("https://37.230.211.37:3056", "POST", "text/xml");
    }

    public HttpURLConnection getConnection(String pUrl, String pMethod, String pContentType) throws Exception{
        URL url = null;
        // пока заглушка
        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.17.46", 8080));
//        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.5.19", 8888));
//        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.101.20.32", 3128));
        System.err.println("Connecting to " + pUrl + " ...");
        url = new URL(pUrl);

        conn = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod(pMethod);
        conn.setRequestProperty("Content-Type", pContentType); // по протоколу eKassir должен быть text/xml
        conn.setRequestProperty("charset", "utf-8");

        System.err.println("Connection prepared...");
        return conn;
    }

    public void sendData(String pData) throws Exception{
        OutputStreamWriter outstrean = new OutputStreamWriter (conn.getOutputStream ());
        BufferedWriter wr = new BufferedWriter (outstrean);
        wr.write(pData);
        wr.flush();
    }
    public String getData() throws Exception{
        String result="";
        char[] cbuf = new char[1];
        if (conn != null) {
            InputStreamReader instrean = null;
            try {
                instrean = new InputStreamReader(conn.getInputStream(), "utf-8");
            } catch (IOException e) {
                instrean = new InputStreamReader(conn.getErrorStream(), "utf-8");
                BufferedReader in = new BufferedReader(instrean);
                while (in.read(cbuf) != -1) {
                    result += String.valueOf(cbuf);
                }
                Exception ex = new Exception(result);
                throw ex;

            }
            BufferedReader in = new BufferedReader(instrean);

            while (in.read(cbuf) != -1) {
                result += String.valueOf(cbuf);
            }

        }

        conn.disconnect();
        return result;
    }

    public void sendFile(Blob pBlob) throws Exception{}
    public Blob getFile() throws Exception{return null;}
}
