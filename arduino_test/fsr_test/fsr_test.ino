int sensorPin = 0;
int ledPin = 9;
 
void setup( ){
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
 
}
 
void loop( ){
  int value = analogRead(sensorPin);
  int intensity = map(value, 0, 1024, 0, 255);
  
  Serial.println(intensity);
  if(intensity>240){
    analogWrite(ledPin, intensity);
  }else{
    analogWrite(ledPin, 0);
  }
  delay(500);
 
}
