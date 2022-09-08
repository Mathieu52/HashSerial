//#include <analogWrite.h>
#include <SoftwareSerial.h>
#include <Motor.h>

Motor motor1(11, 10, 9);
Motor motor2(5, 6, 3);

int angle, strength, button;

float maxRotatationSpeed = 255; //Maximum speed of motor when making the robot turn
float maxSpeed = 255;  //Maximum speed of motor when making the robot go forward or backward
float lowerSpeedLimit = 0;

int BOUTON_ENREGISTRER = 1;
int BOUTON_ARRETER_ENREGISTREMENT = 2;
int BOUTON_DEMARER = 3;
bool enregistrementEnCour = false;
int tabEnregistrement[200];
int i = 0;
int enregistrementTemps;
int enregistrementLongueur;

void setup() {
  Serial.begin(9600);
  pinMode(4, OUTPUT);
  pinMode(7, OUTPUT);
  digitalWrite(4, HIGH);
  digitalWrite(7, HIGH);
}

void loop() {
  //updateBluetooth();
  updateEmulatedBluetooth();

  motor1.control(getRightMotorValue(angle, strength));
  motor2.control(getLeftMotorValue(angle, strength));
}

void updateEmulatedBluetooth() {
  if (Serial.available() > 0)
  {
    String value = Serial.readStringUntil('#');
    if (value.length() == 7)
    {
      angle = value.substring(0, 3).toInt(); //0-2
      strength = value.substring(3, 6).toInt(); //3-5
      button = value.substring(6, 7).toInt(); //6-7

      /*
      if (button == BOUTON_ENREGISTRER)
      {
        enregistrementEnCour = true;
        enregistrementTemps = millis();
        Serial.println("Début");
      }
      else if (button == BOUTON_ARRETER_ENREGISTREMENT) {
          enregistrementEnCour = false;
          enregistrementLongueur = i;
          i = 0;
          Serial.println("Fin");
        }
      if(enregistrementEnCour){
        enregistrer();
        Serial.println("Enregistre");
      }
      if (button == BOUTON_DEMARER)
      {
        Serial.println("Lie");
        lireEnregistrement();
      }
      */

      Serial.print("angle: "); Serial.print(angle); Serial.print('\t');
      Serial.print("strength: "); Serial.print(strength); Serial.print('\t');
      Serial.print("button: "); Serial.print(button); Serial.println("");
      Serial.flush();
      value = "";
    }
  }
}

float getRightMotorValue(float angle, float strength) {
  float motorValue = 0;

  if (angle < 90.0)
    motorValue = map(angle, 0.0, 90.0, -maxRotatationSpeed, maxSpeed);
  else if (angle < 180.0)
    motorValue = map(angle, 180.0, 90.0, maxRotatationSpeed, maxSpeed);
  else if (angle < 270.0)
    motorValue = map(angle, 180.0, 270.0, maxRotatationSpeed, -maxSpeed);
  else
    motorValue = map(angle, 360.0, 270.0, -maxRotatationSpeed, -maxSpeed);

  motorValue *= strength / 100.0;
  return abs(motorValue) > lowerSpeedLimit ? motorValue : 0;
}

float getLeftMotorValue(float angle, float strength) {
  float motorValue = 0;

  if (angle < 90.0)
    motorValue = map(angle, 0.0, 90.0, maxRotatationSpeed, maxSpeed);
  else if (angle < 180.0)
    motorValue = map(angle, 180.0, 90.0, -maxRotatationSpeed, maxSpeed);
  else if (angle < 270.0)
    motorValue = map(angle, 180.0, 270.0, -maxRotatationSpeed, -maxSpeed);
  else
    motorValue = map(angle, 360.0, 270.0, maxRotatationSpeed, -maxSpeed);

  motorValue *= strength / 100.0;
  return abs(motorValue) > lowerSpeedLimit ? motorValue : 0;
}
