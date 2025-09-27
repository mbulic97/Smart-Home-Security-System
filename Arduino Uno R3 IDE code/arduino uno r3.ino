#include <dht.h>
dht DHT;
#define DHT11_PIN 2

const int buzzerPin = 5;
const int ledPin = 6;
const int motionPin = 7;
const int buttonPin = 12;
const int waterSensorPin = A0;

boolean sensor_water = false;
boolean PIR = false; 
boolean buzzer_mode = false;
//String rijec = "Open";


//For LED
int ledState = LOW;
long previousMillis = 0;
long interval = 100;
unsigned long previousDHTMillis = 0;
const long DHTInterval = 1000;
void setup() {
  Serial.begin(9600);
  //The Following are our output
  pinMode(ledPin, OUTPUT);
  pinMode(buzzerPin, OUTPUT);
  pinMode(motionPin, INPUT);
  pinMode(waterSensorPin, INPUT);
  //Button is our Input
  pinMode(buttonPin, INPUT);
  // Wait before   starting the alarm
  delay(5000);


}

void loop() {
  unsigned long currentMillis = millis();
  /*if (Serial.available()) {
    String c = Serial.readString();
    Serial.print("Door: ");
    Serial.println(c);
    rijec = c;

    /*if(currentMillis - previousDHTMillis >= 3000){
      previousDHTMillis = currentMillis;
      rijec=c;
      }

  }*/
  
  int snesorValue = analogRead(waterSensorPin);
  if (snesorValue > 200) {
    sensor_water = true;
    buzzer_mode = true;
  }
  // To chech   whether the motion is detected or not
  if (digitalRead(motionPin) /*&& rijec == "Close"*/) {
    PIR = true;
    buzzer_mode = true;
  }
  if(currentMillis - previousDHTMillis >= DHTInterval){
      previousDHTMillis = currentMillis;
      int chk = DHT.read11(DHT11_PIN);
      //Serial.print("Temperature = ");
      Serial.println(DHT.temperature);
      //Serial.print("Humidity = ");
      Serial.println(DHT.humidity);
      //Serial.print("PIR = ");
      Serial.println(PIR);
      //Serial.print("Water level");
      Serial.println(sensor_water);
      //Serial.print("Alarm = ");
      if(buzzer_mode)
      Serial.println(true);
      else
      Serial.println(false);
    } 
    
  // If alarm mode is on, blink our LED
  if (buzzer_mode) {
    //unsigned long currentMillis = millis();
    if (currentMillis - previousMillis > interval) {
      previousMillis = currentMillis;
      if (ledState == LOW)
        ledState = HIGH;
      else
        ledState = LOW;
      //Switch the LED
      digitalWrite(ledPin, ledState);
    }
    tone(buzzerPin, 1000);
  }
  //If alarm is off
  /*if(buzzer_mode == false){
    //No tone & LED off
    noTone(buzzerPin);
    digitalWrite(ledPin, LOW);
    }*/
  // If our button is pressed Switch off ringing and Setup
  int button_state   = digitalRead(buttonPin);
  //If alarm is off
  if (button_state) {
    buzzer_mode = false;
    sensor_water = false;
    PIR = false;
    noTone(buzzerPin);
    digitalWrite(ledPin, LOW);
    delay(5000);
  }

}
