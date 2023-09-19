/**
 Represents a response containing a list of Account objects for the ReceiptCapture plugin.
 
 This struct is used to encapsulate a list of accounts and an optional error message in response to plugin calls that retrieve account information.
 */
struct RspAccountList: Rsp {
    /// An array of `Account` objects representing the list of accounts.
    let accounts: [Account]
    
    /// An optional error message, which is `nil` when no error occurs.
    let error: String? = nil

    /**
     Converts the `RspAccountList` object into a dictionary suitable for use in plugin response data.
     
     - Returns: A dictionary representing the list of accounts and an optional error message in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> [String: Any] {
        var result: [String: Any] = [:]
        result["accounts"] = accounts.map { account in account.toResultData() }
        result["error"] = error
        return result
    }
}
