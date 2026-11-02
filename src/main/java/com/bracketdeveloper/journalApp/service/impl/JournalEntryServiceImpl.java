package com.bracketdeveloper.journalApp.service.impl;

import com.bracketdeveloper.journalApp.entity.JournalEntry;
import com.bracketdeveloper.journalApp.entity.User;
import com.bracketdeveloper.journalApp.exception.ResourceNotFoundException;
import com.bracketdeveloper.journalApp.repos.JournalEntryRepo;
import com.bracketdeveloper.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bracketdeveloper.journalApp.service.JournalEntryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    @Override
    public JournalEntry createJournal(JournalEntry journalEntry, String username) {
        User user = userService.getByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.createUser(user);
        return journalEntry;
    }
    @Override
    public JournalEntry createJournal(JournalEntry journalEntry) {
        return journalEntryRepo.save(journalEntry);
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
    public void deleteJournalById(String journalId, String username) {
        User user = userService.getByUsername(username);
        if (!journalEntryRepo.existsById(journalId)) {
            throw new ResourceNotFoundException(
                    "Journal entry not found with ID: " + journalId
            );
        }
        user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));
        userService.createUser(user);
        journalEntryRepo.deleteById(journalId);
    }
}
