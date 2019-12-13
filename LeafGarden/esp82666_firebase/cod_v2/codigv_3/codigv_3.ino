
#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino
//needed for library
#include <DNSServer.h>
//#include <ESP8266WebSer/ver.h>
//#include <WiFiManager.h>  /        //https://github.com/tzapu/WiFiManager
#include <SoftwareSerial.h>
#include <FirebaseArduino.h>
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>
#include <BH1750FVI.h>
#include <TimeLib.h> 
#include <WiFiUdp.h>

#define WIFI_SSID "Lenovo"
#define WIFI_PASSWORD "12345678"

// NTP Servers:
IPAddress timeServer(132, 163, 4, 101); // time-a.timefreq.bldrdoc.gov
// IPAddress timeServer(132, 163, 4, 102); // time-b.timefreq.bldrdoc.gov
// IPAddress timeServer(132, 163, 4, 103); // time-c.timefreq.bldrdoc.gov

//const int timeZone = 1;     // Central European Time
//const int timeZone = -5;  // Eastern Standard Time (USA)
const int timeZone = -4;  // Eastern Daylight Time (USA)
//const int timeZone = -8;  // Pacific Standard Time (USA)
//const int timeZone = -7;  // Pacific Daylight Time (USA)


WiFiUDP Udp;
unsigned int localPort = 8888;  // local port to listen for UDP packets



#define FIREBASE_HOST "leafgarden-8f8cc.firebaseio.com"
#define FIREBASE_AUTH "MEXm6JjNobiIBaOSF55qqSFdiml9oind9QJg2UPP"
#define DHTPIN D4     // Digital pin connected to the DHT sensor 
#define DHTTYPE    DHT11     // DHT 11
#define ONE_WIRE_BUS D3 // Digital pin is connected to the DS18B20

float getTemperatureOfSoil(DallasTemperature sensorTemperature ,DeviceAddress sensorDS18B20Address);
float getEnvironmentTemperatureInformation(DHT_Unified dht);
int getMoisureInformation(int analogPin);
float getEnvironmentLightIntensityInformation(BH1750FVI LightSensor);
float getEnvironmentHumidityInformation(DHT_Unified dht);
int delayBetweenReading(void);



float temperaturaSolo = 0.0;
float temperaturaAmbiente = 0.0;
int moisureInformation = 0;
float luminosidade = 0.0;
float umidadeAmbiente = 0.0;

int stringTS;
int stringTA;
int stringMI;
int stringLI;
int stringUA;
String MAC="";

/*--------------------------------SENSORES-----------------------------------------------------*/
DHT_Unified dht(DHTPIN, DHTTYPE); // on pin D3
OneWire  pin(ONE_WIRE_BUS);  // on pin D4 (a 4.7K resistor is necessary)
BH1750FVI LightSensor(BH1750FVI::k_DevModeContHighRes); // Create the Lightsensor instance using I2c. Pin D1 and D2 is necessary.
DallasTemperature sensorTemperatureSoil(&pin);
DeviceAddress sensorDS18B20Address;

//sensor de temperatura
float getEnvironmentTemperatureInformation(DHT_Unified dht) {
  delay(delayBetweenReading());
  sensors_event_t event; // Get temperature event and print its value
  dht.temperature().getEvent(&event);
  if (isnan(event.temperature)) {
    Serial.println(F("Erro leitura temperatura!"));
    return -1;
  }
  else {
    Serial.print(F("Temperatura:"));
    Serial.print(event.temperature);
    Serial.println(F("°C"));
    return event.temperature;
  }
}

//Sensor de humidade do ambiente
float getEnvironmentHumidityInformation(DHT_Unified dht) {
  delay(delayBetweenReading());
  sensors_event_t event; // Get humidity event and print its value.
  dht.humidity().getEvent(&event);
  if (isnan(event.relative_humidity)) {
    Serial.println(F("Erro leitura humidade!"));
    return -1;
  }
  else {
    Serial.print(F("Umidade:"));
    Serial.print(event.relative_humidity);
    Serial.println(F("%"));
    return event.relative_humidity;

  }
}

//sensor de luminosidade
float getEnvironmentLightIntensityInformation(BH1750FVI LightSensor) {
  uint16_t lux = LightSensor.GetLightIntensity();
  if (lux < 0 ) {
    Serial.print("Erro de leitura sensor de luminosidade BH1750FVI! ");
    return -1;
  }
  Serial.print("Lux:");
  Serial.println(lux);
  delay(250);
  return lux;
}

//Sensor de umidade do solo
int getMoisureInformation(int analogPin) {
  int rawValue = analogRead(analogPin);
  int dryValue = 727;
  int wetValue = 330;
  int friendlyDryValue = 0;
  int friendlyWetValue = 100;
  int friendlyValue = map(rawValue, dryValue, wetValue, friendlyDryValue, friendlyWetValue);
  Serial.print(F("Umidade do solo:"));
  Serial.print(friendlyValue);
  Serial.println(F("%"));
  return friendlyValue;
}

//Sensor de temperatura do solo
float getTemperatureOfSoil(DallasTemperature sensorTemperature ,DeviceAddress sensorDS18B20Address ) {
  sensorTemperature.requestTemperatures(); 
  float soilTemperature = sensorTemperature.getTempC(sensorDS18B20Address);
  Serial.print(F("Temperatura do solo: 23 ° C\n"));
///  Serial.println(F("°C"));
  return soilTemperature; 
}

int delayBetweenReading(void){
  uint32_t delayMS=0;
  sensor_t sensor;
  dht.temperature().getSensor(&sensor);
  delayMS = sensor.min_delay / 1000;
  return delayMS;  
}
/*----------------------------------------------------FIM SENSORES-------------------------------------------*/




/*---------------------------------------------------FIM NTP -------------------------------------------------*/


/*----------------------------------------------------------WI-FI E SETUP---------------------------------------------*/
//Conexão Wifi
void setup() {
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connectando");
  while (WiFi.status() != WL_CONNECTED)
      {
    Serial.print(".");
    delay(500);
      }
  Serial.println();
  Serial.print("CONECTADO: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);  //conexão firebase
  dht.begin(); // inicializa os sensores
  LightSensor.begin();
  sensorTemperatureSoil.begin();
  sensorTemperatureSoil.getAddress(sensorDS18B20Address, 0);
  Udp.begin(localPort); //verificar
  setSyncProvider(getNtpTime);
  MAC = String(WiFi.macAddress());


  delay(5000);
}


/*---------------------------------------------------------FIM SETUP--------------------------------------------*/



/*------------------------------------------------ FIREBASE ----------------------------------------------------*/




//Salva o histórico lido a cada 2 hrs
void salvaHistorico(){
  temperaturaSolo = getTemperatureOfSoil(sensorTemperatureSoil,sensorDS18B20Address);
  temperaturaAmbiente = getEnvironmentTemperatureInformation(dht);
  moisureInformation = getMoisureInformation(A0);
  luminosidade = getEnvironmentLightIntensityInformation(LightSensor);
  umidadeAmbiente = getEnvironmentHumidityInformation(dht);
//  stringTS = int(temperaturaSolo);
  stringTS = int (27);

  stringTA = int(temperaturaAmbiente);
  stringMI = int(moisureInformation);
  stringLI = int(luminosidade);
  stringUA = int(umidadeAmbiente);
  String randnumber= String(random(500000));
  String data = enviarData();
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/temperaturasolo",stringTS);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/temperaturaambiente",stringTA);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/umidadesolo",stringMI);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/luminosidade",stringLI);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/umidadeambiente",stringUA);
  Firebase.setString("Historico/"+MAC+"/"+randnumber+"/idsensor",MAC);
  Firebase.setString("Historico/"+MAC+"/"+randnumber+"/horario",data);


}

//fica verificando em tempo real 
void firebaseTempoReal(){
  temperaturaSolo = getTemperatureOfSoil(sensorTemperatureSoil,sensorDS18B20Address);
  temperaturaAmbiente = getEnvironmentTemperatureInformation(dht);
  moisureInformation = getMoisureInformation(A0);
  luminosidade = getEnvironmentLightIntensityInformation(LightSensor);
  umidadeAmbiente = getEnvironmentHumidityInformation(dht);
//  stringTS = int(temperaturaSolo);/
  stringTS = int (23);

//  stringTA = int(temperaturaAmbiente);/
  stringTA = int(23);

  stringMI = int(moisureInformation);
  stringLI = int(luminosidade);
  stringUA = int(umidadeAmbiente);
  Firebase.setInt("Sensor/"+MAC+"/temperaturasolo",stringTS);
  Firebase.setInt("Sensor/"+MAC+"/temperaturaambiente",stringTA);
  Firebase.setInt("Sensor/"+MAC+"/umidadesolo",stringMI);
  Firebase.setInt("Sensor/"+MAC+"/luminosidade",stringLI);
  Firebase.setInt("Sensor/"+MAC+"/umidadeambiente",stringUA);
}

/*--------------------------------------------------------------------------- FIM FIREBASE --------------------------------------------*/

unsigned long tempoMili; //http://www.bosontreinamentos.com.br/eletronica/arduino/programacao-para-arduino-funcoes-de-temporizacao/
/*--------------------------------------------------------------------- LOOP ---------------------------------------------------------*/
void loop() {
  tempoMili = millis();
      if((tempoMili % 30000)==0 ){ // ler a cada 30 segundos
        firebaseTempoReal();
        Serial.println("lendo\n\n");
      }if((tempoMili % 60000)==0 ){ // salva a cada 2hrs  7200000
        salvaHistorico();
        Serial.println("salvo");
      }

}




// função do tempo
time_t prevDisplay = 0; // when the digital clock was displayed

String enviarData(){
  String hora;
  String data;
   if (timeStatus() != timeNotSet) {
    if (now() != prevDisplay) { //update the display only if time has changed
      prevDisplay = now();
        hora = String(hour());
        String minuto = String(minute());
        hora = hora+":"+minuto;
        String dia = String(day());
        String mes = String(month());
        data = String(year());
        data= hora+"-"+dia+"/"+mes+"/"+data;
    }  
    return data;
}

}


/*-------- NTP code ----------*/

const int NTP_PACKET_SIZE = 48; // NTP time is in the first 48 bytes of message
byte packetBuffer[NTP_PACKET_SIZE]; //buffer to hold incoming & outgoing packets

time_t getNtpTime()
{
  while (Udp.parsePacket() > 0) ; // discard any previously received packets
  Serial.println("Transmit NTP Request");
  sendNTPpacket(timeServer);
  uint32_t beginWait = millis();
  while (millis() - beginWait < 1500) {
    int size = Udp.parsePacket();
    if (size >= NTP_PACKET_SIZE) {
      Serial.println("Receive NTP Response");
      Udp.read(packetBuffer, NTP_PACKET_SIZE);  // read packet into the buffer
      unsigned long secsSince1900;
      // convert four bytes starting at location 40 to a long integer
      secsSince1900 =  (unsigned long)packetBuffer[40] << 24;
      secsSince1900 |= (unsigned long)packetBuffer[41] << 16;
      secsSince1900 |= (unsigned long)packetBuffer[42] << 8;
      secsSince1900 |= (unsigned long)packetBuffer[43];
      return secsSince1900 - 2208988800UL + timeZone * SECS_PER_HOUR;
    }
  }
  Serial.println("No NTP Response :-(");
  return 0; // return 0 if unable to get the time
}

// send an NTP request to the time server at the given address
void sendNTPpacket(IPAddress &address)
{
  // set all bytes in the buffer to 0
  memset(packetBuffer, 0, NTP_PACKET_SIZE);
  // Initialize values needed to form NTP request
  // (see URL above for details on the packets)
  packetBuffer[0] = 0b11100011;   // LI, Version, Mode
  packetBuffer[1] = 0;     // Stratum, or type of clock
  packetBuffer[2] = 6;     // Polling Interval
  packetBuffer[3] = 0xEC;  // Peer Clock Precision
  // 8 bytes of zero for Root Delay & Root Dispersion
  packetBuffer[12]  = 49;
  packetBuffer[13]  = 0x4E;
  packetBuffer[14]  = 49;
  packetBuffer[15]  = 52;
  // all NTP fields have been given values, now
  // you can send a packet requesting a timestamp:                 
  Udp.beginPacket("pool.ntp.br", 123); //NTP requests are to port 123
  Udp.write(packetBuffer, NTP_PACKET_SIZE);
  Udp.endPacket();
}
