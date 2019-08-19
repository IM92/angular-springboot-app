package com.springbackend.spring.controller;

import com.springbackend.spring.dto.FeedbackDTO;
import com.springbackend.spring.email.FeedbackSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class FeedbackController {
    @Autowired
    private FeedbackSender feedbackSender;

    @PostMapping
    public void sendFeedback(@RequestBody FeedbackDTO feedbackViewModel,
                             BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException("Feedback has errors; Can not send feedback;");
        }

        this.feedbackSender.sendFeedback(
                feedbackViewModel.getEmail(),
                feedbackViewModel.getName(),
                feedbackViewModel.getFeedback()
        );
    }
}
