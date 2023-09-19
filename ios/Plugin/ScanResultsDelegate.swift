/*
 * UIViewController+BRScanResultsDelegate Extension
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import UIKit
import BlinkReceipt

/// An extension for the `UIViewController` class to conform to the `BRScanResultsDelegate` protocol.
extension UIViewController: BRScanResultsDelegate{

    /// This method is called when scanning is completed successfully.
    ///
    /// - Parameters:
    ///   - cameraViewController: The `UIViewController` responsible for scanning.
    ///   - scanResults: The scan results obtained from the scanning process.
    @objc public func didFinishScanning(_ cameraViewController: UIViewController, with scanResults: BRScanResults) {
        // Dismiss the scanning view controller and resolve the pending scan call with the scan results.
        cameraViewController.dismiss(animated: true, completion: nil)
        ReceiptCapture.pendingScanCall?.resolve(RspReceipt(scanResults: scanResults).toPluginCallResultData())
    }
    /// This method is called when scanning is canceled.
    ///
    /// - Parameter cameraViewController: The `UIViewController` responsible for scanning.
    @objc public func didCancelScanning(_ cameraViewController: UIViewController!){
        // Dismiss the scanning view controller and reject the pending scan call with a cancellation message.
        cameraViewController.dismiss(animated: true, completion: nil)
        ReceiptCapture.pendingScanCall?.reject("scan cancelled")
    }
    /// This method is called when an error occurs during scanning.
    ///
    /// - Parameter error: The error that occurred during scanning.
    @objc public func scanningErrorOccurred(_ error: Error!){
        // Reject the pending scan call with an error message.
        ReceiptCapture.pendingScanCall?.reject(error.localizedDescription)
    }
    
}
