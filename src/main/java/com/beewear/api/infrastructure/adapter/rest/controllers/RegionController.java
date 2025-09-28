package com.beewear.api.infrastructure.adapter.rest.controllers;

import com.beewear.api.application.services.RegionService;
import com.beewear.api.domain.entities.Region;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/region")
@Tag(name = "Region API", description = "API for BINUS regions")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Region>>> getRegions() {
        return ResponseEntity.ok(ApiResponse.success(200, regionService.getRegions()));
    }
}
