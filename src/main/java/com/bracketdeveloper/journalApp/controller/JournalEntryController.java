package com.bracketdeveloper.journalApp.controller;

import com.bracketdeveloper.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bracketdeveloper.journalApp.service.JournalEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> allJournals = journalEntryService.getAllJournals();
        if(allJournals!=null && !allJournals.isEmpty()){
            return new ResponseEntity<>(allJournals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.createJournal(journalEntry);
            return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable String journalId) {
        Optional<JournalEntry> journalById = journalEntryService.getJournalById(journalId);
        if (journalById.isPresent()) return new ResponseEntity<JournalEntry>(journalById.get(), HttpStatus.OK);
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String journalId) {
        journalEntryService.deleteJournalById(journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{journalId}")
    public ResponseEntity<?> updateJournalById(@PathVariable String journalId, @RequestBody JournalEntry newEntry) {
        JournalEntry journalEntry = journalEntryService.getJournalById(journalId).orElse(null);
        if (journalEntry != null) {
            journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : journalEntry.getContent());
            journalEntryService.createJournal(journalEntry);
            return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
