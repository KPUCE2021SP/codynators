 #include <MsTimer2.h>

const int THRESHOLD = 50;
char ch = 'a';
char ch1 = 'b';

void setup() {
  Serial.begin(9600); //시리얼 통신 초기화
}

void loop() {
  int sensorValue = analogRead(A0); //압력센서로 부터 데이터를 읽는다.
  if(sensorValue > THRESHOLD) { //값이 50 이상일 경우
    Serial.write(ch); //계기판으로 데이터'a'를 전송
  }
  else {
    Serial.write(ch1); //자리에 없을 경우 데이터 'b'를 전송
  }
  delay(2000);  
}
