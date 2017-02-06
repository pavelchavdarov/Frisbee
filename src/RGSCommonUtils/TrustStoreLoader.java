package RGSCommonUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by p.chavdarov on 06/02/2017.
 */
public class TrustStoreLoader {
    public static SSLContext getSSLContext(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, trustManagers, null);
//        SSLContext.setDefault(sc);
        return context;
    }

    /**
     * Creates SSLContext with TrustManager using keyStore as trusted store
     * @param keyStore
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static SSLContext getTLSContext(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, trustManagers, null);
//        SSLContext.setDefault(sc);
        return context;
    }

    /**
     * Creates KeyStore instance from key store loaded as resource
     * @param trustStoreName
     * @param password
     * @return
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static KeyStore loadTrustStore(String trustStoreName, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream keystoreStream = TrustStoreLoader.class.getResourceAsStream(trustStoreName);
        if (keystoreStream == null){
            IOException ex = new IOException("Trusted storage " + trustStoreName + " can`t be opened or not found");
            throw ex;
        }
        keystore.load(keystoreStream, password.toCharArray());
        return keystore;



    }
}
