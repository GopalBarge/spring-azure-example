package com.sal.prompt.web.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sal.prompt.web.dto.response.PODistribution;
import com.sal.prompt.web.dto.response.POHeaderResponse;
import com.sal.prompt.web.dto.response.POLine;
import com.sal.prompt.web.dto.response.POLineLocation;
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

        List<POLine> poLines = transformedData.stream().flatMap(e -> e.getPoLines().stream()).sorted(Comparator.comparing(POLine::getInterfaceHeaderKey)).collect(Collectors.toList());
        List<POLineLocation> poLineLocations = poLines.stream().flatMap(e -> e.getPoLineLocations().stream()).sorted(Comparator.comparing(POLineLocation::getInterfaceLineKey)).collect(Collectors.toList());
        List<PODistribution> poDistributions = poLineLocations.stream().flatMap(e -> e.getPoDistributions().stream()).sorted(Comparator.comparing(PODistribution::getInterfaceLineLocationKey)).collect(Collectors.toList());

        writeFile(transformedData, PO_HEADER_FILE_NAME);
        writeFile(poLines, PO_LINE_FILE_NAME);
        writeFile(poLineLocations, PO_LINE_LOCATION_FILE_NAME);
        writeFile(poDistributions, PO_DISTRIBUTION_FILE_NAME);

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
