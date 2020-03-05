package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForLandlordDto;
import com.softserve.maklertaboo.entity.request.RequestForLandlordVerification;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestForLandlordMapper implements MapperToDto<RequestForLandlordVerification, RequestForLandlordDto>,
        MapperToEntity<RequestForLandlordDto, RequestForLandlordVerification> {

    private UserMapper userMapper;

    @Autowired
    public RequestForLandlordMapper(UserMapper flatMapper) {
        this.userMapper = flatMapper;
    }

    @Override
    public RequestForLandlordDto convertToDto(RequestForLandlordVerification requestForUser) {
        RequestForLandlordDto requestForLandlordDto = new RequestForLandlordDto();
        requestForLandlordDto.setCreationDate(requestForUser.getCreationDate());

        requestForLandlordDto.setVerificationDate(requestForUser.getVerificationDate());
        requestForLandlordDto.setId(requestForUser.getId());

        requestForLandlordDto.setStatus(requestForUser.getStatus());

        requestForLandlordDto.setUser(userMapper.convertToDto(requestForUser.getUser()));

        return requestForLandlordDto;
    }

    @Override
    public RequestForLandlordVerification convertToEntity(RequestForLandlordDto dto) {
        return null;
    }
}
