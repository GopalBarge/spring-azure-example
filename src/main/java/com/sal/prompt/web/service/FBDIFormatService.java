package com.sal.prompt.web.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FBDIFormatService {

    private final CloudBlobContainer cloudBlobContainer;

    public Path pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
        return p;
    }

    public boolean prepareFBDIFiles(Map<String, List> transformedData, String zipFileName) {
        log.info("Writing to FBDI files");
        String PATH = "./";
        String ZIP_PATH = "./" + zipFileName;

        String directoryPath = PATH.concat(FilenameUtils.removeExtension(zipFileName));
        File directory = new File(String.valueOf(directoryPath));

        if (!directory.exists()) {
            directory.mkdir();
        }
        transformedData.forEach((srcFile, data) -> {
            try {
                writeFile(data, directoryPath + "/" + srcFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            Path p = pack(directoryPath, ZIP_PATH);
            uploadZipToStorage(p, zipFileName);
            deleteLocalFiles(directoryPath, ZIP_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private void deleteLocalFiles(String directoryPath, String zip_path) {
        deleteDirectory(new File(directoryPath));
        deleteDirectory(new File(zip_path));
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

    public URI uploadZipToStorage(Path filePath, String fileName) {
        URI uri = null;
        CloudBlockBlob blob = null;
        try {

            blob = cloudBlobContainer.getBlockBlobReference(fileName);
            blob.upload(Files.newInputStream(filePath), -1);
            uri = blob.getUri();
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        } catch (
                StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
