/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import UIKit
import BlinkReceipt

extension UIViewController: BRScanResultsDelegate{
    // Implement the delegate callback to handle scan results:
    @objc public func didFinishScanning(_ cameraViewController: UIViewController, with scanResults: BRScanResults) {
        cameraViewController.dismiss(animated: true, completion: nil)

    }
    
    @objc public func didCancelScanning(_ cameraViewController: UIViewController!){
        cameraViewController.dismiss(animated: true, completion: nil)
    }
    
    @objc public func scanningErrorOccurred(_ error: Error!){
        cameraViewController.dismiss(animated: true, completion: nil)
        print(error)
    }
    
}
