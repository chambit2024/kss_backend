package com.emptyseat.kss.domain.yolo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessingServiceTest {

    private final FileProcessingService fileProcessingService = new FileProcessingService();
    private final String mockFilePath = "/Users/seungho/Projects/kss_backend/src/main/resources/mock/mockdata_1.txt";
    @Test
    void processFile() {
        assertDoesNotThrow(() -> fileProcessingService.processFile(mockFilePath));
    }
}