--DROP TABLE IF EXISTS Waste_Category;
-- Create table for WasteCategory
CREATE TABLE IF NOT EXISTS Waste_Category (
    id INT NOT NULL PRIMARY KEY,        -- Primary key,
    name VARCHAR(255) NOT NULL,         -- Waste category name
    description TEXT NOT NULL ,          -- Description of the waste category
    version INT
);

--DROP TABLE IF EXISTS Recycling_Tip;
-- Create table for RecyclingTip
CREATE TABLE IF NOT EXISTS Recycling_Tip (
    id INT NOT NULL PRIMARY KEY,        -- Primary key,
    category VARCHAR(255) NOT NULL,     -- Category of the recycling tip
    tip TEXT NOT NULL                   -- Recycling tip description
    --wasteCategoryId INT,              -- Foreign key referencing WasteCategory
    --CONSTRAINT fk_recycling_wasteCategory FOREIGN KEY (wasteCategoryId) REFERENCES WasteCategory(id)
);

--DROP TABLE IF EXISTS Disposal_Guideline;
-- Create table for DisposalGuideline
CREATE TABLE IF NOT EXISTS Disposal_Guideline (
    id INT NOT NULL PRIMARY KEY,        -- Primary key,
    wasteType VARCHAR(255) NOT NULL,    -- Type of waste for which the guideline applies
    guideline TEXT NOT NULL             -- Disposal guideline description
    --wasteCategoryId INT,              -- Foreign key referencing WasteCategory
    --CONSTRAINT fk_disposal_wasteCategory FOREIGN KEY (wasteCategoryId) REFERENCES WasteCategory(id)
);
