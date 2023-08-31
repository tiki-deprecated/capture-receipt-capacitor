/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import AVKit

public class Physical: NSObject {
    
    public override init(){
        BRScanManager.shared().licenseKey = ProcessInfo.processInfo.environment["LICENSE_KEY"]!
        BRScanManager.shared().prodIntelKey = ProcessInfo.processInfo.environment["PRODUCT_KEY"]!
    }
    
    @objc func scan(onError: ((Error) -> Void)? = nil) {
        let scanResultsDelegate = UIApplication.shared.windows.first!.rootViewController! as! BRScanResultsDelegate
        let mediaType = AVMediaType.video
        let authStatus = AVCaptureDevice.authorizationStatus(for: mediaType)
        let scanOptions = BRScanOptions()
        scanOptions.detectDuplicates = true
        scanOptions.storeUserFrames = true
        scanOptions.jpegCompressionQuality = 100
        scanOptions.detectLogo = true
        if authStatus == .authorized {
            DispatchQueue.main.async {
                BRScanManager.shared().startStaticCamera(
                    from: UIApplication.shared.windows.first!.rootViewController!,
                    cameraType: .uxStandard,
                    scanOptions: BRScanOptions(),
                    with: scanResultsDelegate)
            }
        } else {
            AVCaptureDevice.requestAccess(for: mediaType) { granted in
                if granted {
                    DispatchQueue.main.async {
                        BRScanManager.shared().startStaticCamera(
                            from: UIApplication.shared.windows.first!.rootViewController!,
                            cameraType: .uxStandard,
                            scanOptions: BRScanOptions(),
                            with: scanResultsDelegate)
                    }
                } else {
                    onError?(NSError())
                }
            }
        }
    }
}
