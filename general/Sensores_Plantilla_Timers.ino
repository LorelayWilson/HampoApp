#define MQ_PIN 1

#include "driver/timer.h"
#include "driver/adc.h"

#define TIMER_DIVIDER   80
#define TIMER_SCALE     (TIMER_BASE_CLK / TIMER_DIVIDER) // 80e6/TIMER_DIVIDER
#define TIMER0_INTERVAL0_SEC   (1) // segundos
#define ESP_INTR_FLAG_TIMER0 ESP_INTR_FLAG_LEVEL3
#define BUF_SIZE 1024
int ADC_Global = 0;
double bufferRaw[200];
unsigned int cont = 0;
unsigned int SumatorioRaw = 0,  maximoRaw = 0,  minimoRaw = 4095, media = 0;

// Declaracion objeto Timer0
hw_timer_t * timer0 = NULL;
volatile int Flag_ISR_Timer0 = 0;

//=======================================TEMPERATURA/HUMEDAD==========================================
#include <DHT.h>
#include <DHT_U.h>
// Uncomment whatever type you're using!
//#define DHTTYPE DHT11   // DHT 11
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321
//#define DHTTYPE DHT21   // DHT 21 (AM2301)

// Connect pin 1 (on the left) of the sensor to +5V
// NOTE: If using a board with 3.3V logic like an Arduino Due connect pin 1
// to 3.3V instead of 5V!
// Connect pin 2 of the sensor to whatever your DHTPIN is
// Connect pin 4 (on the right) of the sensor to GROUND
// Connect a 10K resistor from pin 2 (data) to pin 1 (power) of the sensor

const int DHTPin = 5;     // what digital pin we're connected to

DHT dht(DHTPin, DHTTYPE);

//========================= TIMER ========================================================
void IRAM_ATTR ISR_Timer0() {
  Flag_ISR_Timer0 = 1;
  ADC_Global = capturaADC(MQ_PIN);
}
//========================== LECTURAS =====================================================
double capturaADC(int pinaLeer) {
  double captura = analogRead(pinaLeer);
  return captura;
}
/*
  double voltaje(int sensorValue) {
  double voltaje = sensorValue * ( 1.72 / 1842) ;
  return voltaje;
  }*/
  
void lecturaHumTemp() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");  // Si esta cadena llega a la rasberry error de revisar sensores
    return;
  }
}
//======================= TRATAMIENTO DE DATOS ============================================
double mediaRaw(unsigned int cont ) {
  SumatorioRaw = bufferRaw[cont] / 100 + SumatorioRaw / 100 ;
  if (bufferRaw[cont] > maximoRaw ) maximoRaw = bufferRaw[cont];  // Da el maximo
  if (bufferRaw[cont] < minimoRaw ) minimoRaw = bufferRaw[cont];  //Da el minimo
  double mediaRaw = (SumatorioRaw * 100) / cont  ;
  return mediaRaw;
}

//=================================== SETUP ================================================
void setup() {
  // Configuracion Timer0 
  timer0 = timerBegin(0, TIMER_DIVIDER, true); // = 1uS
  timerAttachInterrupt(timer0, &ISR_Timer0, true);
  timerAlarmWrite(timer0, 10000, true); //cada 10 000us segundos
  timerAlarmEnable(timer0);
}

void loop() {
  if (Flag_ISR_Timer0) { // SI ESTA ACTIVADO EL FLAG TIMER
    Flag_ISR_Timer0 = 0; // DESACTIVAR EL FLAG
    bufferRaw[cont] = ADC_Global; // Buffer con datos
    cont++;
    media = mediaRaw(cont);
    // muestra.datos[punt_ent] = raw.datos[punt_ent];
    if (cont == 200) {
      cont = 0; // Return to home
    }
  }
}
