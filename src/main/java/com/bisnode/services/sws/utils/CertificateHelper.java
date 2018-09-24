/*==========================================================================
 * Copyright (c) 2018, Bisnode Norge AS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Bisnode Norge AS ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with Bisnode Norge AS.
=============================================================================*/
package com.bisnode.services.sws.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

final class CertificateHelper {

   private static CertificateHelper instance = new CertificateHelper();
   private X509TrustManager trustManager = new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }

      public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) {
      }

      public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) {
      }
   };

   private CertificateHelper() {
   }

   static CertificateHelper getInstance() {
      return instance;
   }

   void handle(HttpsURLConnection connection) throws KeyManagementException, NoSuchAlgorithmException {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, new TrustManager[]{this.trustManager}, null);
      connection.setSSLSocketFactory(ctx.getSocketFactory());
      connection.setHostnameVerifier((paramString, paramSSLSession) -> true);
   }
}
