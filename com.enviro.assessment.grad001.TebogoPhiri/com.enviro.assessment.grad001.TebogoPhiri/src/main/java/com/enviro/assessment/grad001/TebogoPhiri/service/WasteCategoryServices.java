package com.enviro.assessment.grad001.TebogoPhiri.service;

import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WasteCategoryServices {
    private static final String FILE_PATH = "waste_categories.json";
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final JSONFileServices jfs = new JSONFileServices();


}

