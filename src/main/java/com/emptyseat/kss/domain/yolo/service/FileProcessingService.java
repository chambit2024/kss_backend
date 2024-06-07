package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Getter
public class FileProcessingService {
    private final List<Box> boxList = new ArrayList<>();
    public void processFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length != 5) {
                    throw new IllegalArgumentException("파싱 데이터 포멧이 맞지 않습니다.");
                }
                Box box = new Box(parts[0], parts[1], parts[2], parts[3], parts[4]);
                boxList.add(box);
            }
        } catch (IOException e) {
            log.error("Error reading file: " + filePath, e);
        }

        log.info("boxList: " + getBoxList());
    }
}
