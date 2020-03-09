package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestForFlatMapper implements MapperToDto<RequestForFlatVerification, RequestForFlatDto>,
        MapperToEntity<RequestForFlatDto, RequestForFlatVerification> {

    private FlatMapper flatMapper;
    private UserMapper userMapper;

    @Autowired
    public RequestForFlatMapper(FlatMapper flatMapper, UserMapper userMapper) {
        this.flatMapper = flatMapper;
        this.userMapper = userMapper;
    }

    @Override
    public RequestForFlatDto convertToDto(RequestForFlatVerification requestForFlat) {
        RequestForFlatDto requestForFlatDto = new RequestForFlatDto();
        requestForFlatDto.setCreationDate(requestForFlat.getCreationDate());

        requestForFlatDto.setVerificationDate(requestForFlat.getVerificationDate());
        requestForFlatDto.setId(requestForFlat.getId());

        requestForFlatDto.setStatus(requestForFlat.getStatus());

        requestForFlatDto.setFlat(flatMapper.convertToDto(requestForFlat.getFlat()));
        requestForFlatDto.setAuthor(userMapper.convertToDto(requestForFlat.getAuthor()));

        return requestForFlatDto;
    }

    @Override
    public RequestForFlatVerification convertToEntity(RequestForFlatDto dto) {
        return null;
    }
}
