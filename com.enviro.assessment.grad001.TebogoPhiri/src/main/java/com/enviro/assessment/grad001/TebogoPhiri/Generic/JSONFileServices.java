package com.enviro.assessment.grad001.TebogoPhiri.Generic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JSONFileServices<T> {

    private static final Logger log = LoggerFactory.getLogger(JSONFileServices.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Save the list of WasteCategory objects to the JSON file.
     * @param items The list of WasteCategory objects to save.
     * @throws IOException If writing to the JSON file fails.
     */
    public void saveToFile(List<T> items, String filePath) throws IOException {
        if (items == null) throw new NullPointerException("We can't save null objects");
        objectMapper.writeValue(new File(filePath), items);
    }
    /**
     * Read the list of WasteCategory objects from the JSON file.
     * @return The list of WasteCategory objects.
     * @throws IOException If reading from the JSON file fails.
     */
    public List<T> readFromFile(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);

        // If the file does not exist, return an empty list
        if (!file.exists()) {
            return new ArrayList<T>();
        }
        // If the file is empty, return an empty list
        if (file.length() == 0) {
            return new ArrayList<>();
        }

        // Parse the JSON content into a List<T>
        CollectionType listType = objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, clazz);
        return objectMapper.readValue(file, listType);
    }
}
