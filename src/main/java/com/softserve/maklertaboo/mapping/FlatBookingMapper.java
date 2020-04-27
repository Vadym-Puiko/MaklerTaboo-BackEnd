package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity}
 * for mapping {@link RequestForFlatDto} into {@link RequestForFlatBooking} and versa.
 *
 * @author Roman Blavatskyi
 */
@Component
@AllArgsConstructor
public class FlatBookingMapper implements MapperToDto<RequestForFlatBooking, RequestForFlatDto>,
        MapperToEntity<RequestForFlatDto, RequestForFlatBooking> {

    private UserRepository userRepository;
    private FlatRepository flatRepository;
    private UserMapper userMapper;
    private FlatMapper flatMapper;

    /**
     * Method for converting {@link RequestForFlatBooking} into {@link RequestForFlatDto}.
     *
     * @param requestForFlat {@link RequestForFlatBooking}
     * @return {@link RequestForFlatDto}
     */
    @Override
    public RequestForFlatDto convertToDto(RequestForFlatBooking requestForFlat) {
        RequestForFlatDto requestForFlatDto = new RequestForFlatDto();

        requestForFlatDto.setId(requestForFlat.getId());
        requestForFlatDto.setStatus(requestForFlat.getStatus());
        requestForFlatDto.setCreationDate(requestForFlat.getCreationDate());
        requestForFlatDto.setVerificationDate(requestForFlat.getVerificationDate());
        requestForFlatDto.setFlat(flatMapper.convertToDto(requestForFlat.getFlat()));
        requestForFlatDto.setAuthor(userMapper.convertToDto(requestForFlat.getAuthor()));
        requestForFlatDto.setIsAgreementAccepted(requestForFlat.getIsAgreementAccepted());
        requestForFlatDto.setIsAgreementCreated(requestForFlat.getIsAgreementCreated());

        return requestForFlatDto;
    }

    /**
     * Method for converting {@link RequestForFlatDto} into {@link RequestForFlatBooking}.
     *
     * @param dto {@link RequestForFlatDto}
     * @return {@link RequestForFlatBooking}
     */
    @Override
    public RequestForFlatBooking convertToEntity(RequestForFlatDto dto) {
        RequestForFlatBooking requestForFlatBooking = new RequestForFlatBooking();

        User user = userRepository.findById(dto.getAuthor().getId())
                .orElseThrow(IllegalAccessError::new);

        requestForFlatBooking.setAuthor(user);

        Flat flat = flatRepository.findById(dto.getFlat().getId())
                .orElseThrow(IllegalAccessError::new);

        requestForFlatBooking.setFlat(flat);

        requestForFlatBooking.setIsAgreementCreated(dto.getIsAgreementCreated());

        requestForFlatBooking.setIsAgreementAccepted(dto.getIsAgreementAccepted());

        return requestForFlatBooking;
    }
}
