/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import AVKit

public class Scan: NSObject {
    
    public override init(){
        BRScanManager.shared().licenseKey = ProcessInfo.processInfo.environment["LICENSE_KEY"]!
        BRScanManager.shared().prodIntelKey = ProcessInfo.processInfo.environment["PRODUCT_KEY"]!
    }
    
    @objc func scanPhysical() {
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
                    with: UIApplication.shared.windows.first!.rootViewController!)
            }
        } else {
            AVCaptureDevice.requestAccess(for: mediaType) { granted in
                if granted {
                    DispatchQueue.main.async {
                        BRScanManager.shared().startStaticCamera(
                            from: UIApplication.shared.windows.first!.rootViewController!,
                            cameraType: .uxStandard,
                            scanOptions: BRScanOptions(),
                            with: UIApplication.shared.windows.first!.rootViewController!)
                    }
                } else {
                    // return camera error
                }
            }
        }
    }
}

@objc(ScanResultsDelegate)
extension UIViewController: BRScanResultsDelegate{
    // Implement the delegate callback to handle scan results:
    @objc public func didFinishScanning(_ cameraViewController: UIViewController, with scanResults: BRScanResults) {
        cameraViewController.dismiss(animated: true, completion: nil)
        
        // Use scan results
    }
    
    @objc public func didCancelScanning(_ cameraViewController: UIViewController!){
        cameraViewController.dismiss(animated: true, completion: nil)
    }
    
    @objc public func didOutputRawText(_ rawText: String!){
        print(rawText)
    }
    
    @objc public func scanningErrorOccurred(_ error: Error!){
        print(error)
    }
    
    @objc public func didOutputDebugInfo(_ debugInfo: String!){
        print(debugInfo)
    }
    
    @objc public func didOutputDebugKey(_ key: String!, withValue val: Any!){
        print(key)
    }
    
}

