package de.zuellich.meal_planner;

import java.security.*;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

/**
 * Taken from:
 * https://stackoverflow.com/questions/23504819/how-to-disable-ssl-certificate-checking-with-spring-resttemplate?noredirect=1&lq=1
 * Helps with self signed certificates and unit tests.
 */
public final class SSLUtil {

  private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER =
      new TrustManager[] {
        new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          public void checkClientTrusted(X509Certificate[] certs, String authType) {}

          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }
      };

  public static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
    // Install the all-trusting trust manager
    final SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, UNQUESTIONING_TRUST_MANAGER, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }

  public static void turnOnSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
    // Return it to the initial state (discovered by reflection, now hardcoded)
    SSLContext.getInstance("SSL").init(null, null, null);
  }

  private SSLUtil() {
    throw new UnsupportedOperationException("Do not instantiate libraries.");
  }
}
