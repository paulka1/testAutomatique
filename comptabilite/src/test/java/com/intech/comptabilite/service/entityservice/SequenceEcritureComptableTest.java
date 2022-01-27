package com.intech.comptabilite.service.entityservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.service.exceptions.NotFoundException;

@SpringBootTest
class SequenceEcritureComptableTest {

	@Autowired
	private SequenceEcritureComptable sequenceEcritureComptable;
	
	@Autowired
	SequenceEcritureComptableService sequenceEcritureComptableService;


}
