package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.service.ComplaintService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PostMapping("/createflatcommentcomplaint")
    public ResponseEntity<Object> saveComplaintFlatComment(@RequestBody ComplaintDtoId complaintDtoId){
        complaintService.checkComplaintFlatComment(complaintDtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/createusercommentcomplaint")
    public ResponseEntity<Object> saveComplaintUserComment(@RequestBody ComplaintDtoId complaintDtoId){
        complaintService.checkComplaintUserComment(complaintDtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getall")
    private List<ComplaintDto> getAllComplaints(){
        return complaintService.getAllComplaint();
    }
}
