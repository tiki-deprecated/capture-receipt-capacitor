    //
    //  ContentView.swift
    //  Example
    //
    //  Created by Jesse Monteiro Ferreira on 16/08/23.
    //

    import SwiftUI
    import Foundation
    import BlinkReceipt
    import BlinkEReceipt

struct ContentView: View {
    @State var username: String = ""
    @State var password: String = ""
    @State var showLogs: [String] = []
    var testMicroblink = TestMicroblink.init()
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Test Microblink")
            TextField(
                "Username",
                text: $username
            ).padding(.horizontal, 50).padding(.vertical, 10)
            TextField(
                "Password",
                text: $password
            ).padding(.horizontal, 50).padding(.vertical, 10)
            Button("SignIn Amazon") {
                showLogs.append("Signin Amazon")
                testMicroblink.signInAmazon(username: username, password: password)
            }.padding(10)
            Button("SignIn Gmail") {
                showLogs.append("Signin Gmail")
                testMicroblink.signInGmail(username: username, password: password, appendLog: appendLog)
                
            }.padding(10)
            NavigationView{
                List{
                    Section {
                        Text(showLogs.description)
                            .font(.system(size: 14))
                            .fontWeight(.medium)
                            .foregroundColor(.blue)
                            .multilineTextAlignment(.leading)
                            .listRowSeparator(.hidden)
                        
                    }
                }.padding(10)
            }
            
        }
    }
    
    func appendLog(log: String){
        showLogs.append(log)
    }
        
}
