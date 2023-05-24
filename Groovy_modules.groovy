// CHANGE IMAGE TYPE
/* 
   This code allows you to easily change the image type for further processing. 
   You can choose from the following options: 'BRIGHTFIELD_H_DAB', 'BRIGHTFIELD_H_E', and 'FLUORESCENCE'. 
   Simply update the parameter inside the setImageType() function to the desired image type.
*/
setImageType('BRIGHTFIELD_H_DAB');



/* ------------------------------------------------------------------------------------------------------
// Clear previous detections and select annotations
clearDetections();
selectAnnotations();



/* ------------------------------------------------------------------------------------------------------
Selecting whole image as annotation
createSelectAllObject(true)
selectAnnotations();

// running Stardist for quantification of flurocent cells 
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
----------------------------------

get all cells// Starting from all cells
cells = getCellObjects()
--------------------------
//change color of class the classes to use
tumorCl = getPathClass("Positive")
//tumorCl.setColor( getColorRGB(225, 0, 255) )








-------------------
threshold class parameter: 
// Define the cytoplasm threshold -- anything above this will not be incorprated as positve cells
CytopThreshold = 0.37
Cytop = "Cytoplasm: DAB OD mean"
tumorCells = cells.findAll{ it.getPathClass() == tumorCl }

tumorCells.each {
    if (measurement(it, Cytop) > CytopThreshold) {
        it.setPathClass(getDerivedPathClass(it.getPathClass(), "Positivep", getColorRGB(50,0,0)))
    } 
}

fireHierarchyUpdate()

