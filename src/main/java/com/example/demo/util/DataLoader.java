package com.example.demo.util;

import com.example.demo.entity.dao.DataDAO;
import com.example.demo.repo.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DataLoader {

    private final String path;
    private final InputDeserializer deserializer;
    private final MarketRepository repository;

    @Autowired
    public DataLoader(InputDeserializer deserializer, String path, MarketRepository repository) {
        this.path = path;
        this.deserializer = deserializer;
        this.repository = repository;
    }

    @PostConstruct
    private void loadData() {
        String json = readFile(path);
        if (json == null) throw new IllegalStateException("Path to data file specified incorrectly");
        DataDAO dataDAO = deserializer.deserialize(json);
        log.debug(String.format("Loaded data: %s", dataDAO));
        RepoFiller<DataDAO, MarketRepository> filler = new RepoFiller<>();
        filler.addToRepository(repository, dataDAO);
    }

    public String readFile(String path) {
        try {
            return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8.name());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
