/*
 * Physical Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import AVKit

/// A Swift class representing physical receipt scanning functionality.
public class Physical {
    
    /// Initiates a receipt scanning process using the device's camera.
    @objc func scan() {
        // Get the scan results delegate from the root view controller.
        let scanResultsDelegate = UIApplication.shared.windows.first!.rootViewController! as BRScanResultsDelegate
        
        // Determine the media type and authorization status for the device's camera.
        let mediaType = AVMediaType.video
        let authStatus = AVCaptureDevice.authorizationStatus(for: mediaType)

        // Configure the scan options for receipt scanning.
        let scanOptions = BRScanOptions()
        scanOptions.detectDuplicates = true
        scanOptions.storeUserFrames = true
        scanOptions.jpegCompressionQuality = 100
        scanOptions.detectLogo = true

        // Check camera authorization status and initiate scanning accordingly.
        if authStatus == .authorized {
            DispatchQueue.main.async {
                // Start the camera for static receipt scanning.
                BRScanManager.shared().startStaticCamera(
                    from: UIApplication.shared.windows.first!.rootViewController!,
                    cameraType: .uxStandard,
                    scanOptions: BRScanOptions(),
                    with: scanResultsDelegate)
            }
        } else {
            // Request camera access if not authorized.
            AVCaptureDevice.requestAccess(for: mediaType) { granted in
                if granted {
                    DispatchQueue.main.async {
                        // Start the camera for static receipt scanning after authorization.
                        BRScanManager.shared().startStaticCamera(
                            from: UIApplication.shared.windows.first!.rootViewController!,
                            cameraType: .uxStandard,
                            scanOptions: BRScanOptions(),
                            with: scanResultsDelegate)
                    }
                } else {
                    // Reject the scanning process if camera access is not granted.
                    ReceiptCapture.pendingScanCall?.reject("Please provide camera access.")
                }
            }
        }
    }
}
