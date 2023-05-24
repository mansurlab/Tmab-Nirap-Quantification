// ******************************************************
// Quantify H&E Stained Histology

// This code quantifies H&E stained histology images. It assumes that the tissue has already been annotated and labeled with the 'Tumor' class.
// The code performs a simple cell count using the WatershedCellDetection plugin. It removes cells that have a nucleus to cell ratio (NTCR) of less than 0.37.
//
// Required libraries:
// - qupath.imagej.detect.cells.WatershedCellDetection
// ******************************************************


// Clear any previous detections and select objects (aka annotations) labeled as 'Tumor'
clearDetections();
selectObjectsByClassification("Tumor");

// Perform cell detection using the WatershedCellDetection plugin
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,
"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":20.0,
"maxAreaMicrons":400.0,"threshold":0.1,"maxBackground":2.0,"watershedPostProcess":true,"cellExpansionMicrons":2.0,"includeNuclei":true,
"smoothBoundaries":true,"makeMeasurements":true}')

// Remove cells that have a nucleus to cell ratio (NTCR) of less than NTC_Ratio
NTC_Ratio_Threshold = 0.37
RatioConfirmCells = getCellObjects().findAll{ measurement(it, "Nucleus/Cell area ratio") > NTC_Ratio_Threshold }

RatioConfirmCells.each {
    it.setPathClass(getPathClass("Positive"))
}

// Update Visuallization
fireHierarchyUpdate()
