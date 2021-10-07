#include "WiFiEsp.h"
#include<StopWatch.h>
#include<WebSocketClient.h>
#ifndef HAVE_HWSERIAL1

#endif

#include "SoftwareSerial.h"

SoftwareSerial Serial1(2,3); // RX, TX
StopWatch MySW;

char ssid[] = "KT_GiGA_2G_Wave2_4781";
char pass[] = "eb33de0766";

int status = WL_IDLE_STATUS; // Wifi 상태 체크
int sensorPin = A0; // 무게감지 센서 A0포트
int value = 0;

char server[] = "taeyong.loca.lt"; // 서버 주소
char path[] ="/";
String postData;
String postVariable = "temp=";

WiFiEspClient client;
WebSocketClient webSocketClient;

void setup() {
  Serial.begin(9600);

  Serial1.begin(9600);

  WiFi.init(&Serial1);

  if(WiFi.status() == WL_NO_SHIELD){
    Serial.println("WiFi Shield not present");

    while(true);
  }

  while(status!=WL_CONNECTED){
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(ssid);

    status = WiFi.begin(ssid,pass);
  }

  Serial.println("You're Connected to the network");

  printWifiStatus();

  Serial.println();

  Serial.println("Starting connection to Server...");

  if(client.connect(server, 80)){
    Serial.println("Connected");
  }else{
    Serial.println("Connected Failed.");
    while(1){
      
    }
  }

  webSocketClient.path = path;
  webSocketClient.host = server;

  if(webSocketClient.handshake(client)){
    Serial.println("HandShake successful");
  }else{
    Serial.println("HandShake Failed");
    while(1){
      
    }
  }
}

void loop() {
  value = analogRead(sensorPin); // 값을 읽어드림.

  
  MySW.start(); // 스탑워치 시작
  if(MySW.elapsed()>=10000 && value>=300){ // 10초 이상 인식하고 있는데 사람이 있는 경우
      postData = postVariable + "사람 있음";
      Serial.println("사람 있음");
      webSocketClient.sendData(postData);
  }else if(MySW.elapsed()>=10000 && value<=300){ // 10초 이상 있는데 사람이 없는 경우
      Serial.println("사람 없음");
      postData = postVariable+"table 1";
      webSocketClient.sendData(postData);
      delay(5000); // 
      MySW.reset(); // 타이머 초기화
  }else{
      
  }
}

void printWifiStatus()

{

  // print the SSID of the network you're attached to

  Serial.print("SSID: ");

  Serial.println(WiFi.SSID());



  // print your WiFi shield's IP address

  IPAddress ip = WiFi.localIP();

  Serial.print("IP Address: ");

  Serial.println(ip);



  // print the received signal strength

  long rssi = WiFi.RSSI();

  Serial.print("Signal strength (RSSI):");

  Serial.print(rssi);

  Serial.println(" dBm");

}
