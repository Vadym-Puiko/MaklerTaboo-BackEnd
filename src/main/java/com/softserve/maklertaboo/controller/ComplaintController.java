package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.service.ComplaintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {
    private final ComplaintService complaintService;

    @Autowired
    ComplaintController(ComplaintService complaintService){
        this.complaintService=complaintService;
    }

    @PostMapping
    public void saveComplaint(ComplaintDto complaintDto){
        complaintService.saveComplaint(complaintDto);
    }

    @GetMapping
    private List<ComplaintDto> getAllComplaints(){
        return complaintService.getAllComplaint();
    }
}
