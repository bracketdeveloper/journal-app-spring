package com.bracketdeveloper.journalApp.service;

import com.bracketdeveloper.journalApp.entity.JournalEntry;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {
    JournalEntry createJournal(JournalEntry journalEntry);
    List<JournalEntry> getAllJournals();
    Optional<JournalEntry> getJournalById(String journalId);
    void deleteJournalById(String journalId);
}
