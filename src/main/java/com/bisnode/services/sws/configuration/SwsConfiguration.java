/*==========================================================================
 * Copyright (c) 2018, Bisnode Norge AS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Bisnode Norge AS ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with Bisnode Norge AS.
=============================================================================*/
package com.bisnode.services.sws.configuration;

import com.bisnode.services.sws.adapter.SwsAdapter;
import com.bisnode.services.sws.utils.SwsService;
import com.bisnode.services.sws.utils.SwsSoapHandler;
import com.bisnode.services.sws.generated.company.ForetakInfo;
import com.bisnode.services.sws.generated.person.PersonInfo;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.handler.soap.SOAPHandler;

@Configuration
public class SwsConfiguration {

   @Value("${sws.access.user}")
   private String user;
   @Value("${sws.access.pass}")
   private String pass;
   @Value("${sws.access.person.address}")
   private String personAddress;
   @Value("${sws.access.company.address}")
   private String companyAddress;

   @Bean
   public SwsAdapter swsAdapter() {
      return new SwsAdapter(personInfoClient(), foretakInfoClient(), authPerson(), authForetak());
   }

   @Bean
   public PersonInfo personInfoClient() {
      return (PersonInfo)personInfoClientFactoryBean().create();
   }

   @Bean
   public ForetakInfo foretakInfoClient() {
      return (ForetakInfo)foretakInfoClientFactoryBean().create();
   }

   @Bean
   public SOAPHandler soapHandler() {
      return new SwsSoapHandler();
   }

   @Bean
   @Autowired
   public SwsService swsService(SwsAdapter swsAdapter) {
      return new SwsService(swsAdapter);
   }

   @Bean
   public JaxWsProxyFactoryBean personInfoClientFactoryBean() {
      JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
      jaxWsProxyFactoryBean.setServiceClass(PersonInfo.class);
      jaxWsProxyFactoryBean.setAddress(personAddress);

      // add our handler
      jaxWsProxyFactoryBean.getHandlers().add(soapHandler());

      return jaxWsProxyFactoryBean;
   }

   @Bean
   public JaxWsProxyFactoryBean foretakInfoClientFactoryBean() {
      JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
      jaxWsProxyFactoryBean.setServiceClass(ForetakInfo.class);
      jaxWsProxyFactoryBean.setAddress(companyAddress);
      return jaxWsProxyFactoryBean;
   }

   @Bean
   public com.bisnode.services.sws.generated.person.BrukerAutorisasjon authPerson() {
      com.bisnode.services.sws.generated.person.BrukerAutorisasjon authPerson = new com.bisnode.services.sws.generated.person.BrukerAutorisasjon();
      authPerson.setBrukerID(user);
      authPerson.setPassord(pass);
      return authPerson;
   }

   @Bean
   public com.bisnode.services.sws.generated.company.BrukerAutorisasjon authForetak() {
      com.bisnode.services.sws.generated.company.BrukerAutorisasjon authForetak = new com.bisnode.services.sws.generated.company.BrukerAutorisasjon();
      authForetak.setBrukerID(user);
      authForetak.setPassord(pass);
      return authForetak;
   }
}
