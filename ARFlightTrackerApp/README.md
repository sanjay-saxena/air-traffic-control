# IBMFlightTracker
IBMFlightTracker is an iOS based app which tracks flight pushed by SDR/ADSB message receiver through MQTT server. The app will display all the flights travelling point to point within the range of the receiver. IBM Flight tracker app is connected to IBM MQTT server to a topic which receives new/updated flight information based on which is rendered into the map view. The data is fed to the topic by SDR/ADSB message receiver. The map also shows animated view of flights heading in a particular direction towards its destination.

# Pre-requisites
 - Swift 3
 - Xcode 8.0+
 - CocoaPod - https://cocoapods.org/
 
# Dependencies
 - CocoaMQTT -  Note: moving to aphid client by IBM
 - SwiftyJSON
 
# Steps:
 1. git clone git@github.ibm.com:rogue-one/IBMFlightTracker.git
 2. cd IBMFlightTracker && open IBMFlightTracker.xcworkspace using xcode
 3. Run `pod install` from the project directory. This will install the dependencies define in `Podfile`
 4. Change MQTT credentials in class  util/MQTTConnection.swift using Xcode editor
 5. Build and Run
