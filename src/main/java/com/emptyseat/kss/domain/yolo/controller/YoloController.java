package com.emptyseat.kss.domain.yolo.controller;

import com.emptyseat.kss.domain.yolo.dto.HoggedSeatResponse;
import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.service.CurrentHoggedSeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "\uD83D\uDCF8 Frame")
@RestController
@RequiredArgsConstructor
@RequestMapping("/frame")
public class YoloController {
    @Operation(
            summary = "프레임 조회",
            description = "특정 프레임을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "특정 프레임 조회")
    })
    @GetMapping
    public ResponseEntity<HoggedSeatResponse> getFrame() {
        // Redis에서 프레임 추출
        CurrentHoggedSeatService currentHoggedSeatService = new CurrentHoggedSeatService();

        return ResponseEntity.ok().body(currentHoggedSeatService.getResponse());
    }

}
