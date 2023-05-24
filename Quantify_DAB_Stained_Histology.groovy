/*******************************************************
 Quantify DAB Stained Histology

This code  quantifies DAB stained histology images counterstained with Hematoxylin. 
It detects positive cells using the PositiveCellDetection plugin and applies a threshold to remove dark line folds from positive DAB regions 
by thresholding the cytoplasm optical density mean.

******************************************************/

// Clear previous detections and select annotations
clearDetections();
selectAnnotations();

// Perform positive cell detection using the PositiveCellDetection plugin
runPlugin('qupath.imagej.detect.cells.PositiveCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,"backgroundRadiusMicrons":8.0,
"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,
"threshold":0.1,"maxBackground":2.0,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":2.0,"includeNuclei":true,
"smoothBoundaries":true,"makeMeasurements":true,"thresholdCompartment":"Nucleus: DAB OD mean","thresholdPositive1":0.35,"thresholdPositive2":0.4,
"thresholdPositive3":0.6000000000000001,"singleThreshold":true}')

// Remove dark line folds from positive DAB regions

// Starting from all cells
cells = getCellObjects()

// Define the class to use
tumorCl = getPathClass("Positive")
//tumorCl.setColor(getColorRGB(225, 0, 255))

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
