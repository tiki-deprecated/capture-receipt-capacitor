import XCTest
@testable import Plugin

class ReceiptCaptureTests: XCTestCase {
    
    let licenseKey = ProcessInfo.processInfo.environment["LICENSE_KEY"]!
    let productKey = ProcessInfo.processInfo.environment["PRODUCT_KEY"]!
    
    let jesseAccount = ProcessInfo.processInfo.environment["JESSE_LOGIN"]!
    let jessePassword = ProcessInfo.processInfo.environment["JESSE_PASSWORD"]!
    
        
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testEcho() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        
        let implementation = ReceiptCapture()
        let value = "Hello, World!"
        let result = implementation.echo(value)
        
        XCTAssertEqual(value, result)
    }
    
    func testRetailer() {
        let req = ReqInitialize(licenseKey: licenseKey, productKey: productKey)
        Retailer.init(req: req)
        Retailer(req: req).account(retailer: RetailerCommon.amazon, username: jesseAccount, password: jessePassword, dayCutoff: 10)
        //        Retailer(req: req).orders()
    }
    
    
    func testRetailerInitialize() {
        let req = ReqInitialize(licenseKey: licenseKey, productKey: productKey)
        Retailer.init(req: req)
    }
    
    func testRetailerAccount(){
        let req = ReqInitialize(licenseKey: licenseKey, productKey: productKey)
        Retailer.init(req: req)
        Retailer.init(req: req).account(retailer: RetailerCommon.amazon, username: jesseAccount, password: jessePassword, dayCutoff: 10)
    }
    func testRetailerOrder() {
        let req = ReqInitialize(licenseKey: licenseKey, productKey: productKey)
        Retailer(req: req).orders()
    }
}
