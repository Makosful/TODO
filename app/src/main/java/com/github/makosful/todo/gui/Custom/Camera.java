package com.github.makosful.todo.gui.Custom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.github.makosful.todo.BuildConfig;
import com.github.makosful.todo.Common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Camera {
    private static final String TAG = "Camera";

    private Activity activity;

    /**
     * Creates a new instance of the Camera class, which will handle the functionality surrounding
     * the camera feature.
     *
     * To use this class properly, the activity holding it must override onRequestPermissionsResult
     * with the request code PERMISSION_REQUEST_CODE_CAMERA, override onActivityResult with the
     * request code SERVICE_REQUEST_CODE_CAMERA.
     *
     * Permissions much be checked and granted before launching the camera.
     *
     * @param activity The Activity that will hold the instance of this class.
     */
    public Camera(Activity activity) {
        Log.d(TAG, "Camera: Creating class");
        this.activity = activity;
    }

    /**
     * Requests the permissions necessary to use the Camera feature.
     * This method compiles a list of permissions not granted and requests the user for permission.
     * This method does not handle the response of the request. The Activity that was passed into
     * the constructor should handle the result in the onRequestPermissionResult override with the
     * request code PERMISSION_REQUEST_CODE_CAMERA.
     *
     * @return The boolean returned by this method indicate whether a request was sent to the user
     * or not
     */
    public boolean requestPermissions() {
        Log.d(TAG, "requestPermissions() called");

        ArrayList<String> permissionList = checkPermissions();

        // Checks if any permissions has been added
        if (!permissionList.isEmpty()) {
            // Ask for permission on all entries in the list

            String msg = String.format("Number of missing permissions are [%o]", permissionList.size());
            Log.d(TAG, "requestPermissions: " + msg);

            String[] permissions = new String[permissionList.size()];
            permissionList.toArray(permissions);

            Log.d(TAG, "requestPermissions: Requestion permissions");
            this.activity.requestPermissions(permissions, Common.PERMISSION_REQUEST_CODE_CAMERA);
            return true;
        } else {
            // If not permissions has been added, then all have been granted
            Log.d(TAG, "requestPermissions: Permissions already granted");
            return false;
        }
    }

    /**
     * Checks what permissions have been granted and adds the missing ones to a list
     * @return Returns a list of missing permissions required to launch the Camera app
     */
    private ArrayList<String> checkPermissions() {
        ArrayList<String> permissionList = new ArrayList<>();

        String permissionCamera = Manifest.permission.CAMERA;
        String permissionStorageRead = Manifest.permission.READ_EXTERNAL_STORAGE;
        String permissionStorageWrite = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        // Checks for Camera permission
        if (!hasPermission(permissionCamera)) {
            Log.d(TAG, "requestPermissions: Adding Camera permission to list");
            permissionList.add(permissionCamera);
        }

        // Checks for Storage Read permission
        if (!hasPermission(permissionStorageRead)) {
            Log.d(TAG, "requestPermissions: Adding Storage Read permission to list");
            permissionList.add(permissionStorageRead);
        }

        // Checks for Storage Write permission
        if (!hasPermission(permissionStorageWrite)) {
            Log.d(TAG, "requestPermissions: Adding Storage Write Permission to list");
            permissionList.add(permissionStorageWrite);
        }

        return permissionList;
    }

    /**
     * Checks if the specified permission has been granted for this app
     * @param permission The String representing the permission to check on
     * @return Returns a boolean showing whether the permission has been granted or denied
     */
    private boolean hasPermission(String permission) {
        return this.activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Opens the default Camera application.
     * Permission for Camera, Storage Read and Storage Write.
     * Permissions must be requested and granted before calling this method.
     * The result of the camera must be handled by the activity class holding this Camera instance
     * in the onActivityResult override method with the request code SERVICE_REQUEST_CODE_CAMERA
     *
     * @return Returns a URI pointing to the picture taken by the camera. Returns null if
     * permissions aren't granted or if the app failed to make the directory for the image
     */
    public Uri launchCameraAndGetFileUri() {
        Log.d(TAG, "launchCameraAndGetFileUri() called");
        if (!checkPermissions().isEmpty()) return null;

        try {
            Log.d(TAG, "launchCameraAndGetFileUri: Entering try bracket");
            File f = createNewFilePath();

            Uri path = FileProvider.getUriForFile(
                    this.activity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    f);

            Log.d(TAG, "launchCameraAndGetFileUri: Creating intent");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.putExtra(MediaStore.EXTRA_OUTPUT, path);

            Log.d(TAG, "launchCameraAndGetFileUri: Starting Camera");
            this.activity.startActivityForResult(i, Common.SERVICE_REQUEST_CODE_CAMERA);

            return path;
        } catch (IOException e) {
            Log.e(TAG, "launchCameraAndGetFileUri: Failed to launch Camera", e);
            return null;
        }
    }

    /**
     * Creates a new file in the public image folder with a name matching the current timestamp.
     * REQUIRES STORAGE PERMISSION - failing to properly grant permissions before running this
     * method will result in a crash
     * @return Returns a new file with a public path
     * @throws IOException Exception is thrown if the method fails to create a new file
     */
    private File createNewFilePath() throws IOException {
        Log.d(TAG, "createNewFilePath() called");

        String pics = Environment.DIRECTORY_PICTURES;
        File album = Environment.getExternalStoragePublicDirectory(pics);
        File dir = new File(album, Common.APPLICATION_NAME);

        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new IOException("Failed to create a new file");
            } else {
                Log.d(TAG, "createNewFilePath: New directory made");
            }
        } else {
            Log.d(TAG, "createNewFilePath: Directory already exists");
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(new Date());
        String prefix = "IMG";
        String postfix = "jpg";
        String filename = prefix + "_" + timeStamp + "." +postfix;

        return new File(dir.getPath() + File.separator + filename);
    }
}
