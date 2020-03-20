package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestForFlatMapper implements MapperToDto<RequestForFlatVerification, RequestForFlatDto>,
        MapperToEntity<RequestForFlatDto, RequestForFlatVerification> {


    private UserRepository userRepository;
    private FlatRepository flatRepository;

    @Autowired
    public RequestForFlatMapper(UserRepository userRepository, FlatRepository flatRepository) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
    }

    @Override
    public RequestForFlatDto convertToDto(RequestForFlatVerification requestForFlat) {
        RequestForFlatDto requestForFlatDto = new RequestForFlatDto();

        requestForFlatDto.setCreationDate(requestForFlat.getCreationDate());

        requestForFlatDto.setVerificationDate(requestForFlat.getVerificationDate());

        requestForFlatDto.setId(requestForFlat.getId());

        requestForFlatDto.setStatus(requestForFlat.getStatus());

        requestForFlatDto.setFlatId(requestForFlat.getFlat().getId());

        requestForFlatDto.setAuthorId(requestForFlat.getAuthor().getId());

        return requestForFlatDto;
    }

    @Override
    public RequestForFlatVerification convertToEntity(RequestForFlatDto dto) {
        RequestForFlatVerification requestForFlatVerification = new RequestForFlatVerification();

        requestForFlatVerification.setCreationDate(dto.getCreationDate());

        User user = userRepository.findById(dto.getAuthorId()).orElseThrow(IllegalAccessError::new);

        requestForFlatVerification.setAuthor(user);

        Flat flat = flatRepository.findById(dto.getFlatId()).orElseThrow(IllegalAccessError::new);

        requestForFlatVerification.setFlat(flat);

        return requestForFlatVerification;
    }
}
