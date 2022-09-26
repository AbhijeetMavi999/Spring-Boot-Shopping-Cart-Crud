package com.shoppingcart.controller;

import com.shoppingcart.dto.requestDto.ApparelDto;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.service.ApparelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apparel")
public class ApparelController {

    @Autowired
    private ApparelService apparelService;

    @PostMapping("/create/product/{productId}")
    public ResponseEntity<ApparelDto> createApparel(@RequestBody ApparelDto apparelDto, @PathVariable("productId") Integer productId) throws ResourceNotFoundException {
        ApparelDto create = apparelService.createApparel(apparelDto, productId);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @GetMapping("/search/{design}")
    public ResponseEntity<List<ApparelDto>> searchApparelsByDesign(@PathVariable("design") String design) throws ResourceNotFoundException {
        List<ApparelDto> apparelDtos = apparelService.searchByApparelDesign(design);
        return new ResponseEntity<>(apparelDtos, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApparelDto>> getAllApparels() throws ResourceNotFoundException {
        List<ApparelDto> apparelDtos = apparelService.getAllApparels();
        return new ResponseEntity<>(apparelDtos, HttpStatus.FOUND);
    }

    @GetMapping("/get/{brand}")
    public ResponseEntity<List<ApparelDto>> getApparelsByBrand(@PathVariable("brand") String brand) throws ResourceNotFoundException {
        List<ApparelDto> apparelDtos = apparelService.getApparelByBrand(brand);
        return new ResponseEntity<>(apparelDtos, HttpStatus.FOUND);
    }
}
