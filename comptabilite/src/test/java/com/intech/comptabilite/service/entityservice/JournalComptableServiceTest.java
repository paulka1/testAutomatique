package com.intech.comptabilite.service.entityservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.intech.comptabilite.model.JournalComptable;
import com.intech.comptabilite.repositories.JournalComptableRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JournalComptableServiceTest {

	@Autowired
	private JournalComptableService journalComptableService;
	
	@MockBean
	JournalComptableRepository h;
	
	@Test
	public void getListJournalComptable() {
		
	Iterable<JournalComptable> Journals = new ArrayList<JournalComptable>();
	when(h.findAll()).thenReturn(Journals);
	
	var listJournal = journalComptableService.getListJournalComptable();
	
	assertEquals(0, listJournal.size());
	verify(h).findAll();
	}
	
	
	@Test
	public void getByCode() {
		ArrayList<JournalComptable> listJournals = new ArrayList<JournalComptable>();
		JournalComptable journalComptables = new JournalComptable("BQ-2016/00001", "Test get by Code");
		listJournals.add(journalComptables);

		JournalComptable journalComptable = journalComptableService.getByCode(listJournals, "BQ-2016/00001");

		assertEquals("BQ-2016/00001", journalComptable.getCode());

	}

	@Test
	public void getByCodeNotFound() {
		ArrayList<JournalComptable> listJournals = new ArrayList<JournalComptable>();
		listJournals.add(new JournalComptable("BQ-2016/00001", "Test get by Code"));

		JournalComptable journalComptable = journalComptableService.getByCode(listJournals, "WRONG REF");
		
		assertNull(journalComptable);

	}


}
