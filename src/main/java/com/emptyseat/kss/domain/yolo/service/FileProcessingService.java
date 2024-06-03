package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileProcessingService {

    private final List<Box> boxList = new ArrayList<>();
    public void processFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // TODO: 각 줄마다 공백 split을 통해 Box 객체 리스트에 추가하기
                log.info("Read line: " + line);

            }
        } catch (IOException e) {
            log.error("Error reading file: " + filePath, e);
        }

    }
}
