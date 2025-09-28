# Smart Home Security System 
<div align="center">
<img width="512" height="467.5" alt="LOGO" src="https://github.com/user-attachments/assets/f4eb68fd-02ca-441b-a6a7-ba8fe3b66d84" />
</div>

# Mobile App
<div align="center">
<img width="216" height="463.2" src="https://github.com/user-attachments/assets/efe5c436-87b0-47cd-8175-056ef497cf69" />
</div>


# Overview
![20250926_233003](https://github.com/user-attachments/assets/f7ce2b74-4dc2-47cd-b8ce-d499f97b1d24)
**Smart Home Security System** built with **Arduino Uno R3**, **ESP32**, **Firebase**, and a **Kotlin Android application** using **Jetpack Compose** and **MVVM** architecture.
The system monitors multiple sensors in real time and provides feedback through the mobile app.

### Technologies used (Mobile App)
- Navigation Bar – Home, History (currently in development), and Profile screens.
- Firebase Authentication – Login, Sign up, and Sign out (with valid Arduino ID).
- Firebase Realtime Database – Stores and updates sensor states in real time.
- Firebase Firestore – Stores user profiles and Arduino IDs.
- MVVM & LiveData – separates logic from views, manages user authentication, profile, and real-time sensor data on Home screen.

### Built With
- Android Studio Kotlin Jetpack Compose, MVVM
- Arduino IDE C++
- Firebase authentication, Realtime Database and Firestore
- Arduino Uno R3, ESP32
- Full-size and half-size breadboards
- Led diode + 220 Ohm
- Pushbutton switch 12mm + 1k Ohm
- Buzzer
- Passive infrared sensor HC SR501
- Door (servo – Open/Closed state)
- Resistor
- RFID Card 
- Voltage divider 5V(Arduino Uno R3) to 3.3V(ESP32), 2.2k Ohm + 1k Ohm
- Water Level
- DHT11 temperature and humidity sensor
- Serial communication (Arduino Uno R3 &harr; ESP32)
### Circuit Schema
<img width="3903" height="1965" alt="Shema" src="https://github.com/user-attachments/assets/f37e8ef3-ec2b-4799-852f-6a88a76d2ab4" />


### Demonstrations
 - <a href="https://drive.google.com/drive/u/0/folders/1x7JQR94-RUqXmYqFtOfnN2nC-1HilU2Z">Google Drive Videos</a>
 - Screenshots of the Android app
   <table>
  <tr>
    <th>Profile</th>
    <th>Home</th>
    <th>Signup</th>
  </tr>
  <tr>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/3fdcdcfc-1cb9-46de-8254-53c17a9b668a" /></td>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/4f104a7f-7550-47ae-bf9b-2ac55eaa5413" /></td>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/4fa6122b-41ad-48c7-bcb8-31cbbb9d51e6" /></td>
  </tr>
</table>
<table>
  <tr>
    <th>Login</th>
    <th>Alarm</th>
    <th>Alarm & Flooding</th>
  </tr>
  <tr>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/5bde7f25-5c95-421a-ace9-b5b5fc84787d" /></td>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/d00261b9-7649-4e6d-9b02-d7e92d1917aa" /></td>
    <td><img width="216" height="463.2" src="https://github.com/user-attachments/assets/08f91890-267a-44b3-a946-28b467cb56a0" /></td>
  </tr>
</table>

### How It Works
- Arduino Uno R3
  - Collects sensor data:
     - Alarm &rarr; true/false
     - PIR sensor &rarr; true/false
     - Temperature & Humidity &rarr; numerical values
     - Water Level &rarr; true/false
     - Switch button stops the active alarm after 5 seconds, then the system restarts.
     - Sends these values to the ESP32 via Serial communication, pin TX0 (Arduino Uno R3) &rarr; Voltage divider (5V &rarr; 3.3V) &rarr; pin 16GPIO (ESP32).
- ESP32
  - Reads the Serial output from Arduino Uno R3.
  - Servo motor controls door (Open/Closed) with a valid RFID card.
  - Sends all sensor values and states to Firebase Realtime Database
- Android App (Kotlin, Jetpack Compose, MVVM)
  - Reads data from Firebase in real time.
  - Displays live sensor values.
  - Vibration & red text alerts when Alarm, PIR, or Water Level are triggered
  - Provides door control (servo Open/Closed).
  - User login, registration, profile, and sign out (requires valid Arduino ID).
