/******************************************************
 Quantify Fluorescent Cells using Stardist

 This code utilizes the Stardist algorithm to detect and quantify fluorescent cells in an image. It requires QuPath software and the Stardist model file.
 The code detects cells based on a prediction threshold, applies normalization and resolution settings, and measures shape and intensity of the cells.
 It then applies a size and intensity threshold to filter the detected cells and classifies them as "Positive."
 The code also calculates the percentage of the image area occupied by the positive cells.
 Finally, it exports the measurements to a TSV file and changes the color of the positive cells.

* Required libraries:
* - qupath.ext.stardist.StarDist2D
******************************************************/

import qupath.ext.stardist.StarDist2D
import qupath.lib.gui.measure.ObservableMeasurementTableData
import qupath.lib.objects.PathCellObject

// Clear previous detections and annotations
clearDetections();
clearAnnotations();

// Selects the entire image as an Annotation
createSelectAllObject(true)
selectAnnotations();

// Specify the model file (replace with the appropriate directory)
var pathModel = 'general_directory/Stardist_Models/dsb2018_heavy_augment.pb'

// Specify the threshold size  
Size_thresh = 50 // The threshold for considering cells based on their size in cubic millimeters
Intensity_thresh = 100 // The threshold for considering cells based on their fluroscent intensity (range = 0-255)

// Configure Stardist parameters
var stardist = StarDist2D.builder(pathModel)
      .threshold(0.2)              // Prediction threshold
      .channels('Green')           // Channel to perform Stardist on (Change this to Blue or Red, for DAPI or RFP)
      .normalizePercentiles(1, 99) // Percentile normalization
      .pixelSize(0.5)              // Resolution for detection
      .cellExpansion(0.1)          // Approximate cells based upon nucleus expansion
      .cellConstrainScale(1.5)     // Constrain cell expansion using nucleus size
      .measureShape()              // Add shape measurements
      .measureIntensity()          // Add cell measurements (in all compartments)
      .build()

// Run detection for the selected objects
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
stardist.close();

//--------------------------------------------------------
// Apply the thresholds to detected cells
Poscells = getCellObjects().findAll{ (measurement(it, "Nucleus: Area µm^2") > Size_thresh) & (measurement(it, "Green: Nucleus: Median") > Intensity_thresh)}
Poscells.each{ it.setPathClass(getPathClass("Positive"))}

// Update the visuals
fireHierarchyUpdate()

// Calculate the percentage of image area occupied by positive cells
def annotations = getAnnotationObjects()
def ob = new ObservableMeasurementTableData()
ob.setImageData(getCurrentImageData(),  annotations)

// Iterate through each annotation object
annotations.each { annotation ->
    {
        // Get child objects of the annotation
        def cells = annotation.getChildObjects()
        if (cells != null && cells.size() > 0) {
            // Calculate the sum of positive nucleus areas for the cells
            def sumPositive = cells.stream()
                    .filter(cell -> cell instanceof PathCellObject)
                    .filter(cell -> cell.getDisplayedName().equals("Positive"))
                    .mapToDouble(cell -> cell.getMeasurementList().getMeasurementValue("Nucleus: Area µm^2"))
                    .sum()

            // Store the sum of positive nucleus areas in the annotation's measurement list
            annotation.getMeasurementList().putMeasurement("Sum of positive nucleus (areas)", sumPositive)

            // Calculate the percentage of positive nucleus area relative to the total area
            def areaPercentage = (sumPositive / ob.getNumericValue(annotation, "Area µm^2")) * 100

            // Store the percentage of positive nucleus area in the annotation's measurement list
            annotation.getMeasurementList().putMeasurement("Percentage of positive nucleus (area)", areaPercentage)
        }
    }
}


//-------------------------------------------------
// Generate folder for output
def outputDir = buildFilePath(PROJECT_BASE_DIR, 'Measurements')
mkdirs(outputDir)

// Separate each measurement value in the output file with a tab ("\t")
def separator = "\t"

// Choose the type of objects that the export will process
def exportType = PathAnnotationObject.class

// Choose your *full* output path
def outputPath = buildFilePath(outputDir,"measurements.tsv")
def outputFile = new File(outputPath)

// Create the measurementExporter and start the export
def exporter  = new MeasurementExporter()
                  .imageList(imagesToExport)            // Images from which measurements will be exported
                  .separator(separator)                 // Character that separates values
                  .exportType(exportType)               // Type of objects to export
                  .exportMeasurements(outputFile)        // Start the export process

// -------------------------------------------------------------------------------------------------------
// Change color of the positive cells
positive = getPathClass('Positive')
cd8 = getPathClass('CD8+ Cell') // Just setting to a different class, has nothing to do with CD8+ cells 
color = getColorRGB(200, 0, 0) // (change these RGB numbers if you would like to change the color)
positive.setColor(color)

println 'Done!'
/******************************************************/
