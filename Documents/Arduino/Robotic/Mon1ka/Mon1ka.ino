//#include <analogWrite.h>
#include <SoftwareSerial.h>
#include <Motor.h>

Motor motor1(11, 10, 9);
Motor motor2(5, 6, 3);

int angle, strength, button;

float maxRotatationSpeed = 255; //Maximum speed of motor when making the robot turn
float maxSpeed = 255;  //Maximum speed of motor when making the robot go forward or backward
float lowerSpeedLimit = 0;

long last_message_time;

void setup() {
  Serial.begin(9600);
}

void loop() {
  readSerial();
  processMotor(angle, strength);
}

void readSerial() {
  if (Serial.available() > 0)
  {
    String value = Serial.readStringUntil('#');
    if (value.length() == 7)
    {
      angle = value.substring(0, 3).toInt(); //0-2
      strength = value.substring(3, 6).toInt(); //3-5
      Serial.flush();
      value = "";
    }
    last_message_time = millis();
  }
}

void processMotor(int angle, int strength) {
    float motor1_throttle = getRightMotorValue(angle, strength);
    float motor2_throttle = getLeftMotorValue(angle, strength);
    if (millis() - last_message_time < 100) {
      motor1.control(motor1_throttle);
      motor2.control(motor2_throttle);
    } else {
      motor1.stop();
      motor2.stop();
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
