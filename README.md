# Tmab-Nirap-Quantification
QuPath Code repository for "Trastuzumab-Niraparib combination therapy: Quantifying early proliferative alterations in HER2+ breast cancer models."

Includes: 
  1) Fluorescent Cell Counting Script
  2) Whole Tissue or Section Histology Cell Counting Script (H&E stained)
  3) Whole Tissue or Section Histology Cell Counting Script (Nuclear DAB)

## How to Download Groovy Scripts from GitHub and Use Them in QuPath
  ### Step 1: Find and Download the Script from GitHub

  1. Navigate to the GitHub repository where the Groovy script is located.
  2. Click on the file you wish to download.
  3. On the file's page, click the "Raw" button. This will open a new page with the raw script content.
  4. Right-click the page and select "Save As" to download the script to your local machine.

  ### Step 2: Use the Script in QuPath

  1. Open QuPath.
  2. From the top menu, select "Automate > Show Script Editor".
  3. In the script editor, click on "File > Open..." and navigate to the location where you saved the downloaded script. Open the script.
  4. The script will be loaded into the script editor. At this point, you can modify it as needed, or just run it as is.
  5. Click on "Run" (Play button) in the top right corner of the script editor to execute the script.

  **Note:** The specific steps might vary depending on the repository, the specific version of QuPath you are using, or the specifics of the script. Always be sure to check the documentation and any readme files provided in the repository for any specific instructions or requirements. Be mindful about the script's purpose and ensure it's safe and secure to use.

  **Caution:** Executing scripts from the internet can pose security risks. Be sure you trust the source and understand what the script does before you run it.

## How to download Stardist extention for Qupath
  1. Download the latest qupath-extension-stardist-[version].jar file from the releases page from the qupath-extension-stardist repository.
  https://github.com/qupath/qupath-extension-stardist

  2. Drag the downloaded .jar file onto the main QuPath window.If you haven't installed any extensions before, you'll be prompted to select a QuPath user directory. 
     The extension will then be copied to a location inside that directory.
     
  3. Restart QuPath.
  
## How to Annotate and Classify Annotations
### Annotating in QuPath
  1. Launch QuPath and open the image or slide you want to annotate.
  2. Click on the "Annotation" tab in the top menu.
  3. Choose the desired annotation tool from the toolbar, such as "Rectangle," "Ellipse," or "Wand."
  4. Draw the annotation shape around the region of interest in the image.
  5. Adjust the annotation by clicking and dragging the control points or handles. Or by holding down shift to add or option to remove regions. 
  7. To modify or delete an existing annotation, select it by clicking on it, then use the corresponding options in the toolbar.

### Classifying Annotations in QuPath
  1. Select the annotation you want to classify by clicking on it.
  2. In the "Annotation" tab, click on the Class in our case "Tumor". Then set class.
  4. QuPath assigns the chosen class to the selected annotations, and the class label is displayed alongside the annotations.


 Feel free to explore the QuPath documentation or seek additional resources for more in-depth instructions on using the software.
For the official QuPath GitHub Page: https://github.com/qupath/qupath
For documantion check: https://qupath.readthedocs.io/en/0.4/

