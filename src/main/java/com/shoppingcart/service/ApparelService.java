package com.shoppingcart.service;

import com.shoppingcart.dto.requestDto.ApparelDto;
import com.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;


public interface ApparelService {

    ApparelDto createApparel(ApparelDto apparelDto, Integer productId) throws ResourceNotFoundException;

    List<ApparelDto> searchByApparelDesign(String design) throws ResourceNotFoundException;

    List<ApparelDto> getAllApparels() throws ResourceNotFoundException;

    List<ApparelDto> getApparelByBrand(String brand) throws ResourceNotFoundException;
}
