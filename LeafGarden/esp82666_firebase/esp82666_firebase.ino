#include <ESP8266WiFi.h>
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

// Set these to run example.
#define FIREBASE_HOST "leafgarden-8f8cc.firebaseio.com"
#define FIREBASE_AUTH "MEXm6JjNobiIBaOSF55qqSFdiml9oind9QJg2UPP"
#define WIFI_SSID "ICOMP"
#define WIFI_PASSWORD ""

#define CAYENNE_PRINT Serial
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

String stringTS;
String stringTA;
String stringMI;
String stringLI;
String stringUA;

DHT_Unified dht(DHTPIN, DHTTYPE); // on pin D3
OneWire  pin(ONE_WIRE_BUS);  // on pin D4 (a 4.7K resistor is necessary)
BH1750FVI LightSensor(BH1750FVI::k_DevModeContHighRes); // Create the Lightsensor instance using I2c. Pin D1 and D2 is necessary.
DallasTemperature sensorTemperatureSoil(&pin);
DeviceAddress sensorDS18B20Address;

float getEnvironmentTemperatureInformation(DHT_Unified dht) {
  delay(delayBetweenReading());
  sensors_event_t event; // Get temperature event and print its value
  dht.temperature().getEvent(&event);
  if (isnan(event.temperature)) {
    Serial.println(F("Error reading temperature!"));
    return -1;
  }
  else {
    Serial.print(F("Temperature: "));
    Serial.print(event.temperature);
    Serial.println(F("°C"));
    return event.temperature;
  }
}

float getEnvironmentHumidityInformation(DHT_Unified dht) {
  delay(delayBetweenReading());
  sensors_event_t event; // Get humidity event and print its value.
  dht.humidity().getEvent(&event);
  if (isnan(event.relative_humidity)) {
    Serial.println(F("Error reading humidity!"));
    return -1;
  }
  else {
    Serial.print(F("Humidity: "));
    Serial.print(event.relative_humidity);
    Serial.println(F("%"));
    return event.relative_humidity;

  }
}

float getEnvironmentLightIntensityInformation(BH1750FVI LightSensor) {
  uint16_t lux = LightSensor.GetLightIntensity();
  if (lux < 0 ) {
    Serial.print("Error reading the BH1750FVI sensor !! ");
    return -1;
  }
  Serial.print("Light: ");
  Serial.println(lux);
  delay(250);
  return lux;
}

int getMoisureInformation(int analogPin) {
  int rawValue = analogRead(analogPin);
  int dryValue = 727;
  int wetValue = 330;
  int friendlyDryValue = 0;
  int friendlyWetValue = 100;
  int friendlyValue = map(rawValue, dryValue, wetValue, friendlyDryValue, friendlyWetValue);
  Serial.print(F("Moisure: "));
  Serial.print(friendlyValue);
  Serial.println(F("%"));
  return friendlyValue;
}

float getTemperatureOfSoil(DallasTemperature sensorTemperature ,DeviceAddress sensorDS18B20Address ) {
  sensorTemperature.requestTemperatures(); 
  float soilTemperature = sensorTemperature.getTempC(sensorDS18B20Address);
  Serial.print(F("Temperature of Soil: "));
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
void setup()
{
  // Debug console
  Serial.begin(9600);
  
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
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  dht.begin();
  LightSensor.begin();
  sensorTemperatureSoil.begin();
  sensorTemperatureSoil.getAddress(sensorDS18B20Address, 0);
  delay(5000);
}


void loop(){
  temperaturaSolo = getEnvironmentTemperatureInformation(dht);
  temperaturaAmbiente = getEnvironmentTemperatureInformation(dht);
  moisureInformation = getMoisureInformation(A0);
  luminosidade = getEnvironmentLightIntensityInformation(LightSensor);
  umidadeAmbiente = getEnvironmentHumidityInformation(dht);

  stringTS = String(temperaturaSolo);
  stringTA = String(temperaturaAmbiente);
  stringMI = String(moisureInformation);
  stringLI = String(luminosidade);
  stringUA = String(umidadeAmbiente);

  Firebase.setString("Sensor/TemperaturaSolo",stringTS);
  Firebase.setString("Sensor/TemperaturaAmbiente",stringTA);
  Firebase.setString("Sensor/UmidadeSolo",stringMI);
  Firebase.setString("Sensor/Luminosidade",stringLI);
  Firebase.setString("Sensor/UmidadeAmbiente",stringUA);

  delay(1000);            
}
