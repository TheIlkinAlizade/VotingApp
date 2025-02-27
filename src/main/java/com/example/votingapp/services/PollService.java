package com.example.votingapp.services;


import com.example.votingapp.model.OptionVote;
import com.example.votingapp.model.Poll;
import com.example.votingapp.repository.PollRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {
    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPool(Poll poll) {
        return pollRepository.save(poll);
    }


    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(long id) {
        return pollRepository.findById(id);
    }

    public void vote(long pollId, int optionIndex) {
        //Get the Poll from DB
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(()-> new RuntimeException("Poll not found"));
        //Get ALL Options
        List<OptionVote> options = poll.getOptions();
        //If Index not valid show error
        if(optionIndex < 0 || optionIndex >= options.size()) {
            throw new IllegalArgumentException("Invalid option index");
        }
        //Get Selected Option
        OptionVote selectedOption = options.get(optionIndex);
        //Increment vote for Selection
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);
        //Save Incremented vote option
        pollRepository.save(poll);
    }
}
