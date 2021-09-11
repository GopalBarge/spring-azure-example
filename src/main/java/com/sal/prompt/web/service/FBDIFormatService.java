package com.sal.prompt.web.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sal.prompt.web.dto.response.PODistributionResponse;
import com.sal.prompt.web.dto.response.POHeaderResponse;
import com.sal.prompt.web.dto.response.POLineResponse;
import com.sal.prompt.web.dto.response.POLineLocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FBDIFormatService {
    public List<String> prepareFBDIFiles(List<POHeaderResponse> transformedData) {
        log.info("Writing to FBDI files");

        long millis = System.currentTimeMillis();
        String PO_HEADER_FILE_NAME = "./po-header_" + millis + ".csv";
        String PO_LINE_FILE_NAME = "./po-line_" + millis + ".csv";
        String PO_LINE_LOCATION_FILE_NAME = "./po-line_locations_" + millis + ".csv";
        String PO_DISTRIBUTION_FILE_NAME = "./po-distribution_" + millis + ".csv";

        List<POLineResponse> poLineResponses = transformedData.stream().flatMap(e -> e.getPoLineResponses().stream()).sorted(Comparator.comparing(POLineResponse::getInterfaceHeaderKey)).collect(Collectors.toList());
        List<POLineLocationResponse> poLineLocationResponses = poLineResponses.stream().flatMap(e -> e.getPoLineLocationResponses().stream()).sorted(Comparator.comparing(POLineLocationResponse::getInterfaceLineKey)).collect(Collectors.toList());
        List<PODistributionResponse> poDistributionResponses = poLineLocationResponses.stream().flatMap(e -> e.getPoDistributionResponses().stream()).sorted(Comparator.comparing(PODistributionResponse::getInterfaceLineLocationKey)).collect(Collectors.toList());

        writeFile(transformedData, PO_HEADER_FILE_NAME);
        writeFile(poLineResponses, PO_LINE_FILE_NAME);
        writeFile(poLineLocationResponses, PO_LINE_LOCATION_FILE_NAME);
        writeFile(poDistributionResponses, PO_DISTRIBUTION_FILE_NAME);

        return Arrays.asList(PO_HEADER_FILE_NAME, PO_LINE_FILE_NAME, PO_LINE_LOCATION_FILE_NAME, PO_DISTRIBUTION_FILE_NAME);
    }

    private void writeFile(List data, String fileName) {
        try {
            Writer writer = new FileWriter(Paths.get(fileName).toString());

            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(data);
            writer.close();

        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
