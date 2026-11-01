package com.bracketdeveloper.journalApp.service.impl;

import com.bracketdeveloper.journalApp.entity.JournalEntry;
import com.bracketdeveloper.journalApp.exception.ResourceNotFoundException;
import com.bracketdeveloper.journalApp.repos.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import com.bracketdeveloper.journalApp.service.JournalEntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    JournalEntryRepo journalEntryRepo;

    @Override
    public JournalEntry createJournal(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
        return journalEntry;
    }

    @Override
    public List<JournalEntry> getAllJournals() {
        return journalEntryRepo.findAll();
    }

    @Override
    public Optional<JournalEntry> getJournalById(String journalId) {
        return journalEntryRepo.findById(journalId);
    }

    @Override
    public void deleteJournalById(String journalId) {
        if (!journalEntryRepo.existsById(journalId)) {
            throw new ResourceNotFoundException(
                    "Journal entry not found with ID: " + journalId
            );
        }

        journalEntryRepo.deleteById(journalId);
    }
}
