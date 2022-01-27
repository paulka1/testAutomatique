package com.intech.comptabilite.service.entityservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.intech.comptabilite.model.SequenceEcritureComptable;

@SpringBootTest
class SequenceEcritureComptableTest {

	@Autowired
	private SequenceEcritureComptable sequenceEcritureComptable;
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetDernierValeurByCodeAndAnnee() {
		ArrayList<SequenceEcritureComptable> sequenceEcriture = new ArrayList<SequenceEcritureComptable>();
		
		SequenceEcritureComptable sequence = new SequenceEcritureComptable();
		
		sequenceEcriture.add(sequence);

		

	}

}
