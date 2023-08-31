/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import UIKit
import BlinkReceipt

extension UIViewController: BRScanResultsDelegate{

    @objc public func didFinishScanning(_ cameraViewController: UIViewController, with scanResults: BRScanResults) {
        cameraViewController.dismiss(animated: true, completion: nil)
        ReceiptCapture.pendingScanCall?.resolve(RspReceipt(scanResults: scanResults).toPluginCallResultData())
    }
    
    @objc public func didCancelScanning(_ cameraViewController: UIViewController!){
        cameraViewController.dismiss(animated: true, completion: nil)
        ReceiptCapture.pendingScanCall?.reject("scan cancelled")
    }
    
    @objc public func scanningErrorOccurred(_ error: Error!){
        ReceiptCapture.pendingScanCall?.reject(error.localizedDescription)
    }
    
}
