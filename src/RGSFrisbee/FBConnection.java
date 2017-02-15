package RGSFrisbee;

import RGSCommonUtils.ConnectionInterfaceImpSLLWithTrustStore;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;

import static RGSCommonUtils.oraDAO.Stream2Clob;


public class FBConnection extends ConnectionInterfaceImpSLLWithTrustStore {

//    public void initConnection() {
//        this.httpClient = HttpClientBuilder.create().build();
//    }

    public FBConnection() {
    }

//    private Clob responceToClob(CloseableHttpResponse response) throws IOException, SQLException  {
//        char[] cbuf = new char[1];
//        oracle.jdbc.OracleConnection oraConn =
////                (oracle.jdbc.OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@test03.msk.russb.org:1521:rbotest2","ibs","12ibs");
//                (oracle.jdbc.OracleConnection)new OracleDriver().defaultConnection();
//
//        Clob result =  oracle.sql.CLOB.createTemporary(oraConn, true, oracle.sql.CLOB.DURATION_SESSION);
//        Writer wrc = result.setCharacterStream(1);
//        try{
//            HttpEntity resEntity = response.getEntity();
//            if (resEntity  != null){
//                InputStreamReader inStream = new InputStreamReader(resEntity.getContent());
//                BufferedReader br = new BufferedReader(inStream);
//                while(br.read(cbuf) != -1){
//                    wrc.write(cbuf);
//                }
//                wrc.flush();
//            }
//        }finally{
//            response.close();
//            wrc.close();
//        }
//
//        return result;
//    }

    private HttpPost initRequest(HttpPost request){
        RequestConfig config;
        if(this.proxy != null)
            config = RequestConfig.custom().setProxy(this.proxy).build();
        else
            config = RequestConfig.custom().build();
        request.setConfig(config);

        request.setHeader("Content-Type", "text/xml");
        request.setHeader("charset", "utf-8");
        request.setHeader("eKassir-Version", "3");

        request.setHeader("eKassir-PointID", "2665");
        request.setHeader("eKassir-Password",
                "4QrcOUm6Wau+VuBX8g+IPg==");
        return request;
    }


    public String POST_Request(String p_uri, Object... p_objects) throws IOException {
        String result = "";
        HttpPost request = new HttpPost(p_uri);
        request = initRequest(request);
//        RequestConfig config;
//        if(this.proxy != null)
//            config = RequestConfig.custom().setProxy(this.proxy).build();
//        else
//            config = RequestConfig.custom().build();
//        request.setConfig(config);
//
//        request.setHeader("Content-Type", "text/xml");
//        request.setHeader("charset", "utf-8");
//        request.setHeader("eKassir-Version", "3");
//
//        request.setHeader("eKassir-PointID", "2665");
//        request.setHeader("eKassir-Password",
//                          "4QrcOUm6Wau+VuBX8g+IPg==");

        if (p_objects.length > 0 ) {
            HttpEntity reqEntity =
                    EntityBuilder.create().setText((String) p_objects[0])
                    .setContentType(ContentType.create("text/xml", Charset.forName("utf-8")))
                    .build();
                    //new StringEntity((String) p_objects[0], ContentType.create("text/xml", Charset.forName("utf-8")));//EntityBuilder.create()

            request.setEntity(reqEntity);
            CloseableHttpResponse responce = httpClient.execute(target, request);
            result = responceToString(responce);
        }

        return result;
    }

    public InputStream POST_RequestStream(String p_uri, Object... p_objects) throws IOException {
        HttpPost request = new HttpPost(p_uri);
        request = initRequest(request);

        if (p_objects.length > 0 ) {
            HttpEntity reqEntity = EntityBuilder.create()
                    .setText((String) p_objects[0])
                    .setContentType(ContentType.create("text/xml", Charset.forName("utf-8")))
                    .build();

            request.setEntity(reqEntity);
            CloseableHttpResponse responce = httpClient.execute(target, request);

            return responce.getEntity().getContent();
        }
        else{
            throw new IOException("Empty content for request entity!");
        }

    }

    @Override
    public Clob POST_RequestDBClob(String p_uri, Object... p_objects) throws IOException, SQLException {
        Clob result = null;
        HttpPost request = new HttpPost(p_uri);
        request = initRequest(request);

        if (p_objects.length > 0 ) {

            HttpEntity reqEntity = EntityBuilder.create()
                    .setText((String) p_objects[0])
                    .setContentType(ContentType.create("text/xml", Charset.forName("utf-8")))
                    .build();

            request.setEntity(reqEntity);
            CloseableHttpResponse responce = httpClient.execute(target, request);
            result = Stream2Clob(responce.getEntity().getContent());
        }

        return result;
    }

//    private HttpURLConnection conn;
//    private Proxy proxy;
//
//    public FBConnection(String pUrl, String pMethod, String pContentType) throws Exception {
//        this.getConnection("https://37.230.211.37:3056", "POST", "text/xml");
//    }
//
//    public FBConnection() throws Exception {
//        this.getConnection("https://37.230.211.37:3056", "POST", "text/xml");
//    }
//
//    public HttpURLConnection getConnection(String pUrl, String pMethod, String pContentType) throws Exception{
//        URL url = null;
//        // пока заглушка
//        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.17.46", 8080));
////        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.95.5.19", 8888));
////        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.101.20.32", 3128));
//        System.err.println("Connecting to " + pUrl + " ...");
//        url = new URL(pUrl);
//
//        //Создаем новый trust менеджер который доверяет всем сертификатам
//        TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                    public void checkClientTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                    public void checkServerTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }
//        };
//        //Ферификатор имен хостов
//        HostnameVerifier verifier = new HostnameVerifier() {
//            public boolean verify(String string, SSLSession sSLSession) {
//                return true;
//            }
//        };
//
//        // Устанавливаем новый trust менеджер и верификатор хостов
//        try {
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier(verifier);
//        } catch (Exception e) {
//        }
//
//
//        conn = (HttpURLConnection) url.openConnection(proxy);
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//        conn.setUseCaches(false);
//        conn.setConnectTimeout(60000);
//        conn.setRequestMethod(pMethod);
//        conn.setRequestProperty("Content-Type", pContentType); // по протоколу eKassir должен быть text/xml
//        conn.setRequestProperty("charset", "utf-8");
//        conn.setRequestProperty("eKassir-Version", "utf-8");
//
//
//        System.err.println("Connection prepared...");
//        return conn;
//    }
//
//    public void sendData(String pData) throws Exception{
//        OutputStreamWriter outstrean = new OutputStreamWriter (conn.getOutputStream ());
//        BufferedWriter wr = new BufferedWriter (outstrean);
//        wr.write(pData);
//        wr.flush();
//    }
//    public String getData() throws Exception{
//        String result="";
//        char[] cbuf = new char[1];
//        if (conn != null) {
//            InputStreamReader instrean = null;
//            try {
//                instrean = new InputStreamReader(conn.getInputStream(), "utf-8");
//            } catch (IOException e) {
//                e.printStackTrace();
//                instrean = new InputStreamReader(conn.getErrorStream(), "utf-8");
//                BufferedReader in = new BufferedReader(instrean);
//                while (in.read(cbuf) != -1) {
//                    result += String.valueOf(cbuf);
//                }
//                Exception ex = new Exception(result);
//                throw ex;
//
//            }
//            BufferedReader in = new BufferedReader(instrean);
//
//            while (in.read(cbuf) != -1) {
//                result += String.valueOf(cbuf);
//            }
//
//        }
//
//        conn.disconnect();
//        return result;
//    }
//
//    public void sendFile(Blob pBlob) throws Exception{}
//    public Blob getFile() throws Exception{return null;}
}
