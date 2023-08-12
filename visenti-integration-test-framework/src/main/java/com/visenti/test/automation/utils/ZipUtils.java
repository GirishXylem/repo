package com.visenti.test.automation.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.Log;

public class ZipUtils {

	private ZipUtils() {
		throw new IllegalStateException("Zip Utils class");
	}

	/**
	 * @param folderToBeZippedPath
	 * @param zipFileName
	 * @return
	 * @throws IOException
	 * 
	 *                     This method will zip a folder or a file and return the
	 *                     path of the zipped file relative to current directory
	 */
	public static String zipAFolderOrFileAndReturnFilePath(String folderFileToBeZippedPath, String destinationFolder,
			String zipFileName) throws IOException {
		List<String> fileList = new ArrayList<String>();
		File sourceFolder = new File(folderFileToBeZippedPath);
		fileList = generateFileList(sourceFolder, fileList);
		String zipFilePath = creatingZippedFileAndReturnFilePath(fileList, zipFileName, sourceFolder,
				destinationFolder);
		return zipFilePath;
	}

	/**
	 * @param node
	 * @param fileList
	 * @return
	 * 
	 * 		This method returns a List of files to be zipped
	 * 		Used Recursion to zip all files in the directory
	 */
	public static List<String> generateFileList(File node, List<String> fileList) {

		for (File file : node.listFiles()) {
			Log.debug(file.getName() + " is directory " + file.isDirectory());
			if (file.isFile()) {
				fileList.add(file.getAbsolutePath());
				Log.info("Successfully added " + file.getName() + " to the List");
			}

			else if (file.isDirectory()) {
				generateFileList(file, fileList);
			}
		}

		return fileList;
	}

	/**
	 * @param generatedFiles
	 * @param zipFileName
	 * @param sourceFolder   This method creates the ZippedFile
	 * @return
	 */
	public static String creatingZippedFileAndReturnFilePath(List<String> generatedFiles, String zipFileName,
			File sourceFolder, String destinationFolder) {
		String absoluteSourceFolderPath = sourceFolder.getAbsolutePath();
		String zipFilePath = null;
		try {
			zipFilePath = destinationFolder + zipFileName + "." + FrameworkConstants.ZIPPED_FILE_FORMAT;
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath));

			for (String path : generatedFiles) {
				File ipFile = new File(path);
				String zipPath = path.substring(absoluteSourceFolderPath.length() + 1, path.length());
				Log.debug("ZipPath " + zipPath);
				// Creating a ZipEntry with the specified name
				ZipEntry zen = new ZipEntry(zipPath);
				// Adding the Zip file entry to the ZipOutputStream
				zos.putNextEntry(zen);
				FileInputStream fis = new FileInputStream(ipFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				// Creating a byte array of size equal to file size
				byte[] bArray = new byte[bis.available()];

				// while with no body ,hence adding semicolon waiting for the condition to be
				// true
				// Condition : Reads Byte from the BufferedInputStream to the specified byte
				// array till the
				// end of file is reached
				while (bis.read(bArray, 0, bArray.length) != -1);

				// Write bytes from specified byteArray to this output Stream
				zos.write(bArray);
				// Closing the current ZipEntry
				zos.closeEntry();
				// Closing the BufferedInputStream
				bis.close();
				Log.info(ipFile.getAbsolutePath() + "is zipped");

			}
			// Closing the ZipOutputStream
			zos.close();
		} catch (IOException e) {
			Log.error("Some issue in Zipping the file");
			Log.error(e.getMessage());
		}
		return zipFilePath;
	}

	/**
	 * @param directory Clean up Zip Files from a Folder
	 */
	public static void cleanUpZipFilesInAFolder(String directory) {
		CommonUtils.deleteAFileTypeFromADirectory(directory, FrameworkConstants.ZIPPED_FILE_FORMAT);
	}
}
