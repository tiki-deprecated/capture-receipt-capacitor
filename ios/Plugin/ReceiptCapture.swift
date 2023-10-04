/*
 * ReceiptCapture Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt

/// A Swift class responsible for receipt capture and management.
public class ReceiptCapture: NSObject {
    
    var email: Email? = nil
    var physical: Physical? = nil
    var retailer: Retailer? = nil
    
    /// The pending CAPPluginCall to handle asynchronous scanning.
    public static var pendingScanCall: CAPPluginCall?
    
    /// Initializes the ReceiptCapture class.
    ///
    /// - Parameter call: The CAPPluginCall representing the initialization request.
    public func initialize(_ call: CAPPluginCall) {
        do {
            let reqInit = try ReqInitialize(call)
            let licenseKey = reqInit.ios
            let productKey = reqInit.product
            let googleClientId = reqInit.googleClientId
            let scanManager = BRScanManager.shared()
            scanManager.licenseKey = licenseKey
            scanManager.prodIntelKey = productKey
            physical = Physical()
            email = Email(licenseKey, productKey, googleClientId)
            retailer = Retailer(licenseKey, productKey)
            call.resolve()
        }catch let error as NSError {
            call.reject(error.description)
        }
    }
    
    /// Handles user login for receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the login request.
   public func login(_ call: CAPPluginCall) {
        let reqLogin = ReqLogin(data: call)
        guard let accountType = AccountCommon.defaults[reqLogin.source] else {
            call.reject("Invalid source: \(reqLogin.source)")
            return
        }
        let account = Account.init(accountType: accountType, user: reqLogin.username, password: reqLogin.password, isVerified: false)
        switch account.accountType.type {
        case .email :
            guard let email = email else {
                call.reject("Email not initialized. Did you call .initialize()?")
                return
            }
            email.login(account, call)
            break
        case .retailer :
            guard let retailer = retailer else {
                call.reject("Retailer not initialized. Did you call .initialize()?")
                return
            }
            retailer.login(account, call)
            break
        }
    }
    /// Handles user logout from receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the logout request.
    public func logout(_ call: CAPPluginCall) {
        let reqLogout = ReqLogin(data: call)
        if(reqLogout.source == ""){
            if(reqLogout.username != "" || reqLogout.password != ""){
                call.reject("Error: Invalid logout arguments. If you want delete all accounts, don't send username of password")
                return
            }else{
                guard let retailer = retailer else {
                    call.reject("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                guard let email = email else {
                    call.reject("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.logout(call, nil)
                retailer.logout(call,  Account(retailer: "", username: "", password: ""))
                return
            }

        }
        guard let accountType = AccountCommon.defaults[reqLogout.source] else {
            call.reject("Invalid source: \(reqLogout.source)")
            return
        }
        let account = Account.init(accountType: AccountCommon.defaults[reqLogout.source]!, user: reqLogout.username, password: reqLogout.password, isVerified: false)
        switch account.accountType.type {
        case .email :
            guard let email = email else {
                call.reject("Email not initialized. Did you call .initialize()?")
                return
            }
            email.logout(call, account)
            break
        case .retailer :
            guard let retailer = retailer else {
                call.reject("Retailer not initialized. Did you call .initialize()?")
                return
            }
            retailer.logout(call, account)
            break
        }
    }
    /// Retrieves a list of user accounts for receipt management.
     ///
     /// - Parameter call: The CAPPluginCall representing the request for account information.
    public func accounts(_ call: CAPPluginCall) {
        guard let retailer = retailer else {
            call.reject("Retailer not initialized. Did you call .initialize()?")
            return
        }
        guard let email = email else {
            call.reject("Retailer not initialized. Did you call .initialize()?")
            return
        }
        
        let emails = email.accounts()
        let retailers = retailer.accounts()
        call.resolve(
            RspAccountList(
                accounts: emails + retailers
            ).toPluginCallResultData()
        )
    }
    
    /// Initiates receipt scanning based on the specified account type.
    ///
    /// - Parameter call: The CAPPluginCall representing the scan request.
    public func scan(_ call: CAPPluginCall) {
        let req = ReqScan(data: call)
        if req.account == nil {
            switch req.scanType {
            case .EMAIL:
                guard let email = email else {
                    call.reject("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.scan(call, req.account, call.getInt("dayCutOff"))
                break
            case .RETAILER:
                guard let retailer = retailer else {
                    call.reject("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                retailer.orders(req.account, call)
                break
            case .PHYSICAL:
                guard let physical = physical else {
                    call.reject("Physical not initialized. Did you call .initialize()?")
                    return
                }
                ReceiptCapture.pendingScanCall = call
                physical.scan()
                break
            case .ONLINE:
                guard let retailer = retailer else {
                    call.reject("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                guard let email = email else {
                    call.reject("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.scan(call, req.account, call.getInt("dayCutOff"))
                retailer.orders(req.account, call)
                break
            default:
                call.reject("invalid scan type for account")
            }
        } else {
            switch req.scanType {
            case .EMAIL:
                guard let email = email else {
                    call.reject("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.scan(call, req.account, call.getInt("dayCutOff"))
                break
            case .RETAILER:
                guard let retailer = retailer else {
                    call.reject("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                retailer.orders(req.account, call)
                break
            default:
                call.reject("invalid scan type for account")
            }
        }

    }
    
}
