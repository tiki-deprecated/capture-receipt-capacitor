/// Represents the response containing a list of Account objects.
struct RspAccountList: Rsp {

    let accounts: [Account]
    let error: String? = nil

    // Converts this response to a JSON object.
    func toPluginCallResultData() -> [String: Any] {
        var result: [String: Any] = [:]
        result["accounts"] = accounts.map { account in account.toResultData() }
        result["error"] = error
        return result
    }
    
}
