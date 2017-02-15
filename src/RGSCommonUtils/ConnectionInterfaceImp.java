package RGSCommonUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Created by p.chavdarov on 25/01/2017.
 */
public class ConnectionInterfaceImp implements ConnectionInterface{
    protected CloseableHttpClient httpClient;
    protected HttpHost target;
    protected HttpHost proxy;

    protected String responceToString(CloseableHttpResponse response) throws IOException{
        String result = "";
        Header[] headers = response.getAllHeaders();
        char[] cbuf = new char[10000];
        try{
            HttpEntity resEntity = response.getEntity();
            if (resEntity  != null){
                InputStreamReader inStream = new InputStreamReader(resEntity.getContent(),"windows-1251");
                BufferedReader br = new BufferedReader(inStream);
                int r = 0;
                while((r=br.read(cbuf)) != -1){
                    result+=String.valueOf(cbuf,0,r);
                }
            }
        }finally{
            response.close();
        }
        return result;
    }


    @Override
    public String GET_Request(String p_uri, Object... p_objects) throws IOException {
        HttpGet request = new HttpGet(p_uri);
        RequestConfig config;
        if(this.proxy != null)
            config = RequestConfig.custom().setProxy(this.proxy).build();
        else
            config = RequestConfig.custom().build();
        request.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(request);
//        Если редирект
        if(response.getStatusLine().getStatusCode() == 302){
            String newUrl = response.getHeaders("Location")[0].getValue();
//          на всякий случай заменим (для прокси)
            request = new HttpGet(newUrl.replace("https://", "http://"));
            request.setConfig(config);
            response = httpClient.execute(request);
        }

        return responceToString(response);
    }

    @Override
    public InputStream GET_RequestStream(String uri, Object... objects) throws IOException {
        return null;
    }

    @Override
    public Blob GET_RequestDB(String uri, Object... objects) {
        return null;
    }



    @Override
    public String POST_Request(String p_uri, Object... p_objects) throws IOException {
        String result = "";
        HttpPost request = new HttpPost(p_uri);
        RequestConfig config;
        if(this.proxy != null)
            config = RequestConfig.custom().setProxy(this.proxy).build();
        else
            config = RequestConfig.custom().build();
        request.setConfig(config);

        request.setHeader("Content-Type", (String)p_objects[0]);

        if (p_objects.length > 0 ) {
            HttpEntity reqEntity = EntityBuilder.create()
                    .setText(((String) p_objects[1])).build();
            request.setEntity(reqEntity);
            CloseableHttpResponse responce = httpClient.execute(target, request);
            result = responceToString(responce);
        }

        return result;
    }

    @Override
    public InputStream POST_RequestStream(String uri, Object... objects) throws IOException {
        return null;
    }

    @Override
    public Blob POST_RequestDBBlob(String uri, Object... objects) throws IOException, SQLException {
        return null;
    }

    @Override
    public Clob POST_RequestDBClob(String uri, Object... objects) throws IOException, SQLException {
        return null;
    }

    @Override
    public String PUT_Request(String uri, String contentType, Object... objects) {
        return null;
    }

    @Override
    public Blob PUT_RequestDB(String uri, String contentType, Object... objects) {
        return null;
    }

    @Override
    public void initConnection() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public void initConnection(String trustStore, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, KeyManagementException {

    }

    @Override
    public void closeConnection() throws IOException {
        this.httpClient.close();
    }

    @Override
    public void setProxy(String url, int port, String schema) {
        this.proxy = new HttpHost(url,port,schema);
    }

    @Override
    public void setTarget(String url, int port, String schema) {
        this.target = new HttpHost(url,port,schema);
    }

    @Override
    public HttpHost getTarget() {
        return this.target;
    }
}
