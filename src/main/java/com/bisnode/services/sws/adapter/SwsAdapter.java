/*==========================================================================
 * Copyright (c) 2018, Bisnode Norge AS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Bisnode Norge AS ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with Bisnode Norge AS.
=============================================================================*/
package com.bisnode.services.sws.adapter;

import com.bisnode.services.sws.generated.company.ForetakInfo;
import com.bisnode.services.sws.generated.company.ForetakSok;
import com.bisnode.services.sws.generated.company.ForetakSokResponse;
import com.bisnode.services.sws.generated.company.HentForetak;
import com.bisnode.services.sws.generated.company.HentForetakResponse;
import com.bisnode.services.sws.generated.person.HentPerson;
import com.bisnode.services.sws.generated.person.HentPersonResponse;
import com.bisnode.services.sws.generated.person.PersonInfo;
import com.bisnode.services.sws.generated.person.PersonSok;
import com.bisnode.services.sws.generated.person.PersonSokResponse;

public class SwsAdapter {

   private final PersonInfo personInfo;
   private final ForetakInfo foretakInfo;
   private final com.bisnode.services.sws.generated.person.BrukerAutorisasjon authPerson;
   private final com.bisnode.services.sws.generated.company.BrukerAutorisasjon authForetak;

   public SwsAdapter(PersonInfo personInfo, ForetakInfo foretakInfo, com.bisnode.services.sws.generated.person.BrukerAutorisasjon authPerson,
                     com.bisnode.services.sws.generated.company.BrukerAutorisasjon authForetak) {
      this.personInfo = personInfo;
      this.foretakInfo = foretakInfo;
      this.authPerson = authPerson;
      this.authForetak = authForetak;
   }

   public PersonSokResponse sokPerson(PersonSok personSok) {
      return personInfo.sokPerson(personSok, authPerson);
   }

   public HentPersonResponse hentPersoninfo(HentPerson hentPerson) {
      return personInfo.hentPersoninfo(hentPerson, authPerson, null);
   }

   public ForetakSokResponse sokForetak(ForetakSok foretakSok) {
      return foretakInfo.sokForetak(authForetak, foretakSok);
   }

   public HentForetakResponse hentForetakinfo(HentForetak hentForetak) {
      return foretakInfo.hentForetakinfo(authForetak, hentForetak);
   }
}
