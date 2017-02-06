package RGSCommonUtils;

import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.Registry;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by p.chavdarov on 06/02/2017.
 */
public class ConnectionInterfaceImpSLLWithTrustStore extends ConnectionInterfaceImp{

    public void initConnection(String trustStoreResouce, String trustStorePassword) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, KeyManagementException {
        KeyStore trustStore = TrustStoreLoader.loadTrustStore(trustStoreResouce,trustStorePassword);
        SSLContext context = TrustStoreLoader.getTLSContext(trustStore);
        SSLConnectionSocketFactory SSLsf = new SSLConnectionSocketFactory(context, new DefaultHostnameVerifier());

        Registry<ConnectionSocketFactory> registry  = RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("https", SSLsf)
                                .register("http", new PlainConnectionSocketFactory())
                                .build();
        HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);


        this.httpClient = HttpClientBuilder.create()
//                            .disableRedirectHandling()
                            .setConnectionManager(ccm)
                            .build();
    }
}

