package com.intech.comptabilite.service.entityservice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.intech.comptabilite.model.CompteComptable;
import com.intech.comptabilite.model.EcritureComptable;
import com.intech.comptabilite.model.JournalComptable;
import com.intech.comptabilite.model.LigneEcritureComptable;
import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.repositories.EcritureComptableRepository;
import com.intech.comptabilite.repositories.JournalComptableRepository;
import com.intech.comptabilite.repositories.SequenceEcritureComptableRepository;
import com.intech.comptabilite.service.exceptions.NotFoundException;


@SpringBootTest
public class EcritureComptableServiceTest {
	
	@Autowired
	private EcritureComptableService ecritureComptableService;
	
	@Autowired
	private SequenceEcritureComptableRepository repository;
	
	@MockBean
	JournalComptableRepository h;
	
	@MockBean
	EcritureComptableRepository ecritureComptableRepository;
	
	@Autowired
	private JournalComptableService journalComptableService;
	
	@MockBean
	private SequenceEcritureComptableRepository mockRepository;

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assertions.assertTrue(ecritureComptableService.isEquilibree(vEcriture));

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assertions.assertFalse(ecritureComptableService.isEquilibree(vEcriture));
    }

    @Test
    public void testgetTotalDebit(){
    	EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Debit total > 0");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "100"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "20", "7"));
        Assertions.assertEquals(130, ecritureComptableService.getTotalDebit(vEcriture).intValue());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Débit total = 0");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "0", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "0", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "0", "2"));
        Assertions.assertEquals(BigDecimal.valueOf(0),ecritureComptableService.getTotalDebit(vEcriture));
    }
    
    @Test
    public void testgetTotalCredit(){
    	EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assertions.assertEquals(341, ecritureComptableService.getTotalCredit(vEcriture).intValue());
     } 
  
	@Test
	public void testDeleteEcritureComptabe() throws Exception {	
		EcritureComptable ecritureComptable = new EcritureComptable();
        String refString = "BQ-2016/00001";
        ecritureComptable.setReference(refString);
		
		var listJournal = journalComptableService.getListJournalComptable();
		ecritureComptableService.deleteEcritureComptable(0);
		assertEquals(0, listJournal.size());
		verify(h).findAll();
	}
		
	@Test
    public void testGetEcritureComptableByRef() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        String refString = "BQ-2016/00001";
        ecritureComptable.setReference(refString);

       Mockito.when(ecritureComptableRepository.getByReference(refString))
                 .thenReturn(java.util.Optional.of(ecritureComptable));

       Assertions.assertDoesNotThrow(() -> ecritureComptableService.getEcritureComptableByRef(refString));

    }

    @Test
    public void testGetEcritureComptableByRefIfNotExist() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        String ref = "BQ-2016/00001";
        ecritureComptable.setReference(ref);

        Mockito.when(ecritureComptableRepository.getByReference(ref))
                 .thenReturn(java.util.Optional.of(ecritureComptable));

       String badRefString = "TESBADREF";

       Assertions.assertThrows(NotFoundException.class, () -> ecritureComptableService.getEcritureComptableByRef(badRefString));

    }
    
}
