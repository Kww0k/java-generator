package com.example.controller;

import com.example.entity.vo.GenerateVO;
import com.example.service.GenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class GenerateController {

    public final GenerateService generateService;

    @PostMapping("/generatorEntity")
    public void generatorEntity(@RequestBody GenerateVO generateVO) {
        generateService.generate(generateVO.getSql(), generateVO.getOrmType());
    }
}
