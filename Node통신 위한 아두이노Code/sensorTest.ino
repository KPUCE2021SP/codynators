#include<StopWatch.h>

int sensorPin = A0;
int ledPin = 9;
int value = 0;
StopWatch MySW;

void setup() {
  Serial.begin(9600); // 시리얼 통신을 위해서
}

void loop() {
  value = analogRead(sensorPin);

//  if(value >= 300){
//    Serial.println("자리에 사람이 있습니다");
//  }
  //Serial.println(MySW.isRunning());
  MySW.start(); // 스탑워치 시작
  Serial.println(MySW.elapsed());

  if(MySW.elapsed()>=10000 && value>=300){ // 10초 이상 인식하고 있는데 사람이 있는 경우
      Serial.println("현재 자리에 사람이 있습니다.");
  }else if(MySW.elapsed()>=10000 && value<=300){ // 10초 이상 있는데 사람이 없는 경우
      Serial.println("현재 자리에 사람이 없습니다.");
      delay(5000); // 
      MySW.reset(); // 타이머 초기화
  }else{
    Serial.println("현재 자리 분석 중입니다.");
  }
}
