package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestForFlatMapper implements MapperToDto<RequestForFlatVerification, RequestForFlatDto>,
        MapperToEntity<RequestForFlatDto, RequestForFlatVerification> {

    private FlatMapper flatMapper;

    @Autowired
    public RequestForFlatMapper(FlatMapper flatMapper) {
        this.flatMapper = flatMapper;
    }

    @Override
    public RequestForFlatDto convertToDto(RequestForFlatVerification requestForFlat) {
        RequestForFlatDto requestForFlatDto = new RequestForFlatDto();
        requestForFlatDto.setCreationDate(requestForFlat.getCreationDate());

        requestForFlatDto.setVerificationDate(requestForFlat.getVerificationDate());
        requestForFlatDto.setId(requestForFlat.getId());

        requestForFlatDto.setStatus(requestForFlat.getStatus());

        requestForFlatDto.setFlat(flatMapper.convertToDto(requestForFlat.getFlat()));

        return requestForFlatDto;
    }

    @Override
    public RequestForFlatVerification convertToEntity(RequestForFlatDto dto) {
        return null;
    }
}
