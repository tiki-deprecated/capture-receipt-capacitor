source 'https://github.com/BlinkReceipt/PodSpecRepo.git'
source 'https://cdn.cocoapods.org/'

platform :ios, '13.0'

def capacitor_pods
  use_frameworks!
  pod 'Capacitor', :path => '../node_modules/@capacitor/ios'
  pod 'CapacitorCordova', :path => '../node_modules/@capacitor/ios'
end

target 'Plugin'do
  capacitor_pods
  use_frameworks!
  
  pod 'BlinkReceipt', '~> 1.27'
  pod 'BlinkEReceipt', '~> 2.0'
end


target 'PluginTests' do
  capacitor_pods
end
