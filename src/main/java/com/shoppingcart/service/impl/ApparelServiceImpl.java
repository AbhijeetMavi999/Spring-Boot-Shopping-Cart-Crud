package com.shoppingcart.service.impl;

import com.shoppingcart.dto.requestDto.ApparelDto;
import com.shoppingcart.entity.Apparel;
import com.shoppingcart.entity.Product;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.ApparelRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ApparelService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApparelServiceImpl implements ApparelService {

    @Autowired
    private ApparelRepository apparelRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${apparel-message}")
    private String apparelNotFoundMessage;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ApparelDto createApparel(ApparelDto apparelDto, Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not available"));

        Apparel apparel = modelMapper.map(apparelDto, Apparel.class);
        apparel.setProductDto(product);

        apparel.setType(apparelDto.getType());
        apparel.setBrand(apparelDto.getBrand());
        apparel.setDesign(apparelDto.getDesign());

        Apparel created = apparelRepository.save(apparel);
        return modelMapper.map(apparel, ApparelDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ApparelDto> searchByApparelDesign(String design) throws ResourceNotFoundException {
        List<Apparel> apparels = apparelRepository.findByDesignContaining(design);
        if(apparels.isEmpty()) {
            log.error("Apparels not found by Design {}",design);
            throw new ResourceNotFoundException(apparelNotFoundMessage);
        }
        List<ApparelDto> apparelDtos = apparels.stream().map(apparel -> modelMapper.map(apparel, ApparelDto.class)).collect(Collectors.toList());
        return apparelDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ApparelDto> getAllApparels() throws ResourceNotFoundException {
        List<Apparel> apparels = apparelRepository.findAll();
        if(apparels.isEmpty()) {
            log.error("Apparel Service: Apparels not available");
            throw new ResourceNotFoundException(apparelNotFoundMessage);
        }
        List<ApparelDto> apparelDtos = apparels.stream().map(apparel -> modelMapper.map(apparel, ApparelDto.class)).collect(Collectors.toList());
        return apparelDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ApparelDto> getApparelByBrand(String brand) throws ResourceNotFoundException {
        List<Apparel> apparels = apparelRepository.findByBrand(brand);
        if(apparels.isEmpty()) {
            log.error("Apparel Service: Apparels not available by Brand {}", brand);
            throw new ResourceNotFoundException(apparelNotFoundMessage);
        }
        List<ApparelDto> apparelDtos = apparels.stream().map(apparel -> modelMapper.map(apparel, ApparelDto.class)).collect(Collectors.toList());
        return apparelDtos;
    }
}
