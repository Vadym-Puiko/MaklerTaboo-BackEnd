package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.service.ComplaintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {
    private final ComplaintService complaintService;

    @Autowired
    ComplaintController(ComplaintService complaintService){
        this.complaintService=complaintService;
    }

    @PutMapping
    public void saveComplaint(ComplaintDto complaintDto){
        complaintService.saveComplaint(complaintDto);
    }

    @GetMapping
    private List<ComplaintDto> getAllComplaints(){
        return complaintService.getAllComplaint();
    }
}
