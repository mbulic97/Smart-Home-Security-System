# Smart Home Security System 
<div align="center">
<img width="512" height="467.5" alt="LOGO" src="https://github.com/user-attachments/assets/f4eb68fd-02ca-441b-a6a7-ba8fe3b66d84" />
</div>
<div align="center">
<img width="216" height="463.2" src="https://github.com/user-attachments/assets/efe5c436-87b0-47cd-8175-056ef497cf69" />
</div>


### About
![20250926_233003](https://github.com/user-attachments/assets/f7ce2b74-4dc2-47cd-b8ce-d499f97b1d24)
**Smart Home Security System** built with **Arduino Uno R3**, **ESP32**, **Firebase**, and a **Kotlin Android application** using **Jetpack Compose** and **MVVM** architecture.
The system monitors multiple sensors in real time and provides feedback through the mobile app.

### Built With
- Android Studio Kotlin Jetpack Compose, MVVM
- Arduino IDE C++
- Firebase authentication, Realtime Database and Firestore
- Arduino Uno R3, ESP32
- Breadboard full size and half size
- Led diode + 220 Ohm
- Pushbutton switch 12mm + 1k Ohm
- Buzzer
- Passive infrared sensor HC SR501
- Door (servo – Open/Closed state)
- Resistor
- RFID Card 
- Voltage divider 5V(Arduino Uno R3) to 3.3V(ESP32), 2.2k OHm + 1k Ohm
- Water Level
- DHT11 temperature and humidity sensor
- Serial communication (Arduino Uno R3 <-> ESP32)
### Circuit Schema
<img width="3903" height="1965" alt="Shema" src="https://github.com/user-attachments/assets/f37e8ef3-ec2b-4799-852f-6a88a76d2ab4" />


### Demonstration 
 - <a href="https://drive.google.com/drive/u/0/folders/1x7JQR94-RUqXmYqFtOfnN2nC-1HilU2Z">Google Drive Videos</a>
 

 - Screenshots of the Android app
<img width="216" height="463.2" alt="Profile" src="https://github.com/user-attachments/assets/3fdcdcfc-1cb9-46de-8254-53c17a9b668a" />
<img width="216" height="463.2" alt="" src="https://github.com/user-attachments/assets/4f104a7f-7550-47ae-bf9b-2ac55eaa5413" />
<img width="216" height="463.2" alt="" src="https://github.com/user-attachments/assets/4fa6122b-41ad-48c7-bcb8-31cbbb9d51e6" />
<img width="216" height="463.2" alt="" src="https://github.com/user-attachments/assets/5bde7f25-5c95-421a-ace9-b5b5fc84787d" />

### :arrows_counterclockwise: How It Works
- Arduino Uno R3
  - Collects sensor data:
     - Alarm :arrow_right: true/false
     - PIR sensor :arrow_right: true/false
     - Temperature & Humidity :arrow_right: numerical values
     - Water Level :arrow_right: true/false
  - Sends these values to the ESP32 via Serial communication, pin TX0 (Arduino Uno R3) :arrow_right: Voltage divider (5V :arrow_right: 3.3V) :arrow_right: pin 16GPIO (ESP32).
- ESP32
  - Reads the Serial output from Arduino Uno R3.
  - Handles RFID card authentication.
  - Controls the servo motor (door: Open/Closed).
  - Sends all sensor values and states to Firebase Realtime Database
- Android App (Kotlin, Jetpack Compose, MVVM)
- Reads data from Firebase in real time.
- Displays live sensor values.
- Vibration & red text alerts when Alarm, PIR, or Water Level are triggered
- Provides door control (servo Open/Closed).
- User login, registration, profile, and sign out.
### :gear:Setup 
To run this project, you need to configure both hardware (Arduino/ESP32) and the Android application.
1. Arduino installation
    - Arduino Library manager: Go to `sketch` :arrow_right: `Include Library` :arrow_right: `Manage Libraries` search for DHTLib, ESP32Servo, Firebase_Arduino_Client_Library_for_ESP8266_and_ESP32, Servo and install.
2. Arduino / ESP32
    - Update your **WiFi SSID and password** in the ESP32 code
      <img width="1175" height="437" alt="null" src="https://github.com/user-attachments/assets/97c9cb19-c27d-4b04-b1bf-a5470359b806" />

    - Configure your **Firebase API key**
      <img width="1346" height="375" alt="Firebase api key" src="https://github.com/user-attachments/assets/eb200372-607a-408d-b06d-9600afc84aa4" />

    - **Firebase Database URL**:
      <img width="1356" height="366" alt="Realtime api link" src="https://github.com/user-attachments/assets/839b678a-9dfe-4af8-b207-ef369db0819c" />
3. Firebase
    - Enable **Firebase Authentication**(Email/Password)
    - Create a **Realtime Database** and **Firestore**
    - Connect Firebase SDK to Android Studio :arrow_right: Tutorial video <a href="https://www.youtube.com/watch?v=L5uf-5SJSBk">How To Connect Firebase SDK to Android Studio Youtube</a>
    - Place google-services.json in the app/ directory of the Android project
4. Android Studio
    - Open the project in Android Studio
    - Sync Gradle
    - Run on a real device (sensors require live Firebase updates)
