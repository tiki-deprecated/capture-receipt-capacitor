/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Scan:  CAPPlugin, BRScanResultsDelegate{
    public init(req: ReqInitialize){
        BRScanManager.shared().licenseKey = req.licenseKey
        BRScanManager.shared().prodIntelKey = req.productKey

    }
    
    public func scanPhysical (_ pluginCall: CAPPluginCall){
        let scanOptions = BRScanOptions()
        let myViewController = myViewController()
        let cameraViewController = BRCameraViewController()
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        BRScanManager.shared().startStaticCamera(from: rootVc!,
                                                 cameraType: .uxStandard,
                                                 scanOptions: scanOptions,
                                                 with: myViewController)
        pluginCall.resolve()
    }
}

public class myViewController : BRCameraViewController, BRScanResultsDelegate {
    
    public func didFinishScanning(_ cameraViewController: UIViewController!, with scanResults: BRScanResults!) {
        print("###################")
        print(scanResults!)
    }
    
}



