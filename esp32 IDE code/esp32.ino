#include <SPI.h>
#include <MFRC522.h>
#include <ESP32Servo.h>
#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

#define WIFI_SSID "********"                                  // Change this to your WiFi SSID
#define WIFI_PASSWORD "**********"                            // Change this to your WiFi password
#define API_KEY "**********"                                  // Change this to your API KEY firebase service
#define DATABASE_URL "**********"                             //Change this to your Realtime Database Firebase service

#define SS_PIN  5  // ESP32 pin GPIO5 
#define RST_PIN 27 // ESP32 pin GPIO27 
#define RXp2 16      //SERIAL C. ARDUINO <-> ESP32 
#define TXp2 17      //SERIAL C. ARDUINO <-> ESP32 
#define SERVO_PIN 32 //ESP32 pin GPIO32 connects to servo motor


MFRC522 rfid(SS_PIN, RST_PIN); // RFID
Servo servo;                   // servo

//Firebase
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

unsigned long sendDataPrevMillis = 0;
bool signupOK = false;
String door = "Close";


byte authorizedUID[4] = {0x2C, 0x7F, 0x31, 0x03};    //number card
int angle = 0; // the current angle of servo motor



int temp = 0;//temprature
int hum = 0;//humidity
bool buzzer_mode = false; //alarm true, no alarm false
bool PIR = false; // move true, no move false
bool water_level = false; //ako poplava onda true, ako nije poplava onda false
void setup() {
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD); //wifi router
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  config.api_key = API_KEY;
  config.database_url = DATABASE_URL;
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("signUp OK");
    signupOK = true;
  }
  else {
    Serial.printf("%s\n", config.signer.signupError.message.c_str());
  }
  config.token_status_callback = tokenStatusCallback;
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  Serial2.begin(9600, SERIAL_8N1, RXp2, TXp2);
  SPI.begin(); // init SPI bus
  rfid.PCD_Init(); // init MFRC522
  servo.attach(SERVO_PIN);
  servo.write(angle); // rotate servo motor to 0°
  Serial.println("Tap an RFID/NFC tag on the RFID-RC522 reader");
}

void loop() {
  
  //-----------Temperature--------------
  if (Serial2.available()) {
    String msg = Serial2.readStringUntil('\n'); 
    if (msg.toInt() != temp) {
      temp = msg.toInt();
      if (Firebase.RTDB.setInt(&fbdo, "3145/temp", temp)) {
        Serial.print("Temp: ");
        Serial.print(temp);
        Serial.println("°C");
      }
      else {
        Serial.println("FAILED: " + fbdo.errorReason());
      }
    }

    
    //-----------Humidity--------------
    msg = Serial2.readStringUntil('\n');
    if (msg.toInt() != hum) {
      hum = msg.toInt();
      if (Firebase.RTDB.setInt(&fbdo, "3145/Humidity", hum)) {
        Serial.print("Humidity: ");
        Serial.println(hum);
        //Serial.println("°");
      }
      else {
        Serial.println("FAILED: " + fbdo.errorReason());
      }
    }

    
    //-----------PIR--------------
    msg = Serial2.readStringUntil('\n');
    if (msg.toInt() != PIR) {
      PIR = msg.toInt();
      if (Firebase.RTDB.setBool(&fbdo, "3145/PIR", PIR)) {
        Serial.print("PIR: ");
        Serial.println(PIR);
        //Serial.println("°");
      }
      else {
        Serial.println("FAILED: " + fbdo.errorReason());
      }
    }

    //-----------Water level--------------
    msg = Serial2.readStringUntil('\n');
    if (msg.toInt() != water_level) {
      if(msg.toInt()==1)
      water_level = true;
      else
      water_level = false;
      if (Firebase.RTDB.setBool(&fbdo, "3145/Water_Level", water_level)) {
        Serial.print("Water: ");
        Serial.println(water_level);
        //Serial.println("°");
      }
      else {
        Serial.println("FAILED: " + fbdo.errorReason());
      }
    }
    
    //-----------Alarm--------------
    msg = Serial2.readStringUntil('\n');
    if (msg.toInt() != buzzer_mode) {
      buzzer_mode = msg.toInt();
      if (Firebase.RTDB.setBool(&fbdo, "3145/Alarm", buzzer_mode)) {
        Serial.print("Alarm: ");
        Serial.println(buzzer_mode);
      }
      else {
        Serial.println("FAILED: " + fbdo.errorReason());
      }
    }
  }

  if (rfid.PICC_IsNewCardPresent()) { // new tag is available
    if (rfid.PICC_ReadCardSerial()) { // NUID has been readed
      MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);

      if (rfid.uid.uidByte[0] == authorizedUID[0] &&
          rfid.uid.uidByte[1] == authorizedUID[1] &&
          rfid.uid.uidByte[2] == authorizedUID[2] &&
          rfid.uid.uidByte[3] == authorizedUID[3] ) {
        //Serial.println("Authorized Tag");
        // change angle of servo motor
        if (angle == 0) {
          Serial2.print("Open"); 
          angle = 90;
        }
        else { //if(angle == 90)
          Serial2.print("Closed"); 
          angle = 0;
        }
        // control servo motor arccoding to the angle
        servo.write(angle);
        if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 1000 || sendDataPrevMillis == 0)) {
          sendDataPrevMillis = millis();
          //---------------------- STORE sensor data to a RTDB-------------------
          String door;
          if (angle == 0 )
            door = "Closed";
          else
            door = "Open";
          if (Firebase.RTDB.setString(&fbdo, "3145/door", door)) {
            Serial.print("Door: ");
            Serial.print(door);
            Serial.println(".");
          }
          else {
            Serial.println("FAILED: " + fbdo.errorReason());
          }
        }
       
      }

      

      rfid.PICC_HaltA(); // halt PICC
      rfid.PCD_StopCrypto1(); // stop encryption on PCD
    }
  }
}
