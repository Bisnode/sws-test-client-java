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
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

public class SwsSoapHandler implements SOAPHandler {

   @Override
   public Set<QName> getHeaders() {
      return null;  //TODO: gotta implement
   }

   @Override
   public boolean handleMessage(MessageContext context) {
      HttpsURLConnection connection = (HttpsURLConnection)context.get("http.connection");

      try {
         CertificateHelper.getInstance().handle(connection);
      }
      catch (KeyManagementException | NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      return true;
   }

   @Override
   public boolean handleFault(MessageContext context) {
      return true;
   }

   @Override
   public void close(MessageContext context) {
      //TODO: gotta implement
   }
}
