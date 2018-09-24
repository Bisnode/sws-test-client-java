/*==========================================================================
 * Copyright (c) 2018, Bisnode Norge AS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Bisnode Norge AS ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with Bisnode Norge AS.
=============================================================================*/
package com.bisnode.services.sws.utils;

import com.bisnode.services.sws.adapter.SwsAdapter;
import com.bisnode.services.sws.generated.company.ForetakSok;
import com.bisnode.services.sws.generated.company.ForetakSokResponse;
import com.bisnode.services.sws.generated.company.HentForetak;
import com.bisnode.services.sws.generated.company.HentForetakResponse;
import com.bisnode.services.sws.generated.person.HentPerson;
import com.bisnode.services.sws.generated.person.HentPersonResponse;
import com.bisnode.services.sws.generated.person.LigningVerdier;
import com.bisnode.services.sws.generated.person.PersonSok;
import com.bisnode.services.sws.generated.person.PersonSokResponse;

public class SwsService {

   private final SwsAdapter swsAdapter;

   public SwsService(SwsAdapter swsAdapter) {
      this.swsAdapter = swsAdapter;
   }

   public PersonSokResponse personSearch(PersonSok personSok) {
      return swsAdapter.sokPerson(personSok);
   }

   public HentPersonResponse personInfo(Long internalRef) {
      HentPerson hentPerson = new HentPerson();
      hentPerson.setInternref(internalRef);

      // define products/data to be included in response
      hentPerson.setNavnAdresse(true);
      hentPerson.setTidligereNavnAdresse(true);
      hentPerson.setNaringsInteresser(true);
      hentPerson.setDisponibelInntekt(true);
      hentPerson.setLigning(LigningVerdier.LIGNING_BRUTTO_GJELDSGRAD);
      hentPerson.setBeta(com.bisnode.services.sws.generated.person.BetaVerdier.DETALJER_SAMMENDRAG);

      return swsAdapter.hentPersoninfo(hentPerson);
   }

   public ForetakSokResponse companySearch(ForetakSok foretakSok) {
      return swsAdapter.sokForetak(foretakSok);
   }

   public HentForetakResponse companyInfo(Integer orgNo) {
      HentForetak hentForetak = new HentForetak();
      hentForetak.setOrgnr(orgNo);

      // define products/data to be included in response
      hentForetak.setNavnAdresse(true);
      hentForetak.setGrunnfakta(true);
      hentForetak.setJuridiskVerv(true);
      hentForetak.setAksjonarDatter(true);
      hentForetak.setBeta(com.bisnode.services.sws.generated.company.BetaVerdier.DETALJER_SAMMENDRAG);
      hentForetak.setOkonomiDetaljer(3);

      return swsAdapter.hentForetakinfo(hentForetak);
   }
}
