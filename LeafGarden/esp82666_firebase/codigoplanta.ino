
#include "ESP8266WiFi.h"          //https://github.com/esp8266/Arduino
//needed for library
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>          //https://github.com/tzapu/WiFiManager
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


const char* ssid = "Lenovo"; //nome do roteador
const char* password = "12345678";  //senha

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
    Serial.print(F("Humidade:"));
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
  Serial.print(F("Temperatura do solo:"));
  Serial.print(soilTemperature);
  Serial.println(F("°C"));
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
  WiFiManager wifiManager;
  wifiManager.setTimeout(180);

  
  if(!wifiManager.autoConnect(ssid,password)) {
    Serial.println("conexão falhou");
    delay(3000);
    ESP.reset();
    delay(5000);
  } 
  Serial.println("conectado com sucesso:)");
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);  //conexão firebase
  dht.begin(); // inicializa os sensores
  LightSensor.begin();
  sensorTemperatureSoil.begin();
  sensorTemperatureSoil.getAddress(sensorDS18B20Address, 0);
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

  stringTS = int(temperaturaSolo);
  stringTA = int(temperaturaAmbiente);
  stringMI = int(moisureInformation);
  stringLI = int(luminosidade);
  stringUA = int(umidadeAmbiente);
  MAC = String(WiFi.macAddress());
  String randnumber= String(random(500000));


  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/temperaturasolo",stringTS);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/temperaturaambiente",stringTA);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/umidadesolo",stringMI);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/luminosidade",stringLI);
  Firebase.setInt("Historico/"+MAC+"/"+randnumber+"/umidadeambiente",stringUA);
  Firebase.setString("Historico/"+MAC+"/"+randnumber+"/idsensor",MAC);

}

//fica verificando em tempo real 
void firebaseTempoReal(){
  temperaturaSolo = getTemperatureOfSoil(sensorTemperatureSoil,sensorDS18B20Address);
  temperaturaAmbiente = getEnvironmentTemperatureInformation(dht);
  moisureInformation = getMoisureInformation(A0);
  luminosidade = getEnvironmentLightIntensityInformation(LightSensor);
  umidadeAmbiente = getEnvironmentHumidityInformation(dht);

  stringTS = int(temperaturaSolo);
  stringTA = int(temperaturaAmbiente);
  stringMI = int(moisureInformation);
  stringLI = int(luminosidade);
  stringUA = int(umidadeAmbiente);
  MAC = String(WiFi.macAddress());

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
    Serial.println("lendo");
  }if((tempoMili % 7200000)==0 ){ // salva a cada 2hrs 
    salvaHistorico();
    Serial.println("salvo");
  }
}
