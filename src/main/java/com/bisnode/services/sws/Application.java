/*==========================================================================
 * Copyright (c) 2018, Bisnode Norge AS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Bisnode Norge AS ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with Bisnode Norge AS.
=============================================================================*/
package com.bisnode.services.sws;

import com.bisnode.services.sws.generated.company.ForetakData;
import com.bisnode.services.sws.generated.company.ForetakSok;
import com.bisnode.services.sws.generated.company.ForetakSokResponse;
import com.bisnode.services.sws.generated.company.HentForetakResponse;
import com.bisnode.services.sws.generated.person.HentPersonResponse;
import com.bisnode.services.sws.generated.person.PersonSok;
import com.bisnode.services.sws.generated.person.PersonSokResponse;
import com.bisnode.services.sws.generated.person.Personalia;
import com.bisnode.services.sws.utils.SwsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@SpringBootApplication
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Bean
   CommandLineRunner lookup(SwsService swsService) {
      return args -> {
         processPerson(swsService);
         processCompany(swsService);
      };
   }

   private void processPerson(SwsService swsService) {
      // 1. identify person based on name and birth date, retrieve internal reference
      PersonSokResponse personSokResponse = swsService.personSearch(getPersonSearchRequest());
      Personalia personalia = personSokResponse.getPersonalia().get(0);
      Long internalRef = personalia.getInternref();

      // 2. fetch person information based in retrieved internal reference
      HentPersonResponse hentPersonResponse = swsService.personInfo(internalRef);

      // 3. output person name
      System.out.println(hentPersonResponse.getNavnAdresse().getNavn());

      // 4. output messages (if any)
      List<com.bisnode.services.sws.generated.person.Meldinger> messages = hentPersonResponse.getMeldinger();

      if (messages != null && messages.size() > 0) {
         for (com.bisnode.services.sws.generated.person.Meldinger msg : hentPersonResponse.getMeldinger()) {
            System.out.println(msg.getMeldingsKode() + ": " + msg.getMeldingsTekst());
         }
      }
   }

   private PersonSok getPersonSearchRequest() {
      PersonSok personSok = new PersonSok();
      personSok.setFornavn("demo");
      personSok.setEtternavn("person");

      XMLGregorianCalendar calendar = null;
      try {
         calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
         calendar.setDay(17);
         calendar.setMonth(DatatypeConstants.MAY);
         calendar.setYear(1945);
      }
      catch (DatatypeConfigurationException e) {
         e.printStackTrace();
      }

      personSok.setFodselsdato(calendar);

      return personSok;
   }

   private void processCompany(SwsService swsService) {
      // 1. identify company based on name and address, retrieve organization number
      ForetakSokResponse foretakSokResponse = swsService.companySearch(getCompanySearchRequest());
      ForetakData foretakData = foretakSokResponse.getForetakData().get(0);
      Integer orgNo = foretakData.getOrgnr();

      // 2. fetch company information based in retrieved organization number
      HentForetakResponse hentForetakResponse = swsService.companyInfo(orgNo);

      // 3. output company name
      System.out.println(hentForetakResponse.getNavnAdresse().getNavn());

      // 4. output messages (if any)
      List<com.bisnode.services.sws.generated.company.Meldinger> messages = hentForetakResponse.getMeldinger();

      if (messages != null && messages.size() > 0) {
         for (com.bisnode.services.sws.generated.company.Meldinger msg : hentForetakResponse.getMeldinger()) {
            System.out.println(msg.getMeldingsKode() + ": " + msg.getMeldingsTekst());
         }
      }
   }

   private ForetakSok getCompanySearchRequest() {
      ForetakSok foretakSok = new ForetakSok();
      foretakSok.setNavn("svar direkte");
      foretakSok.setAdresse("c/o testgruppen");
      return foretakSok;
   }
}
