


#include <FastLED.h>

#define DATA_PIN 3
#define CLOCK_PIN 5
#define NUM_LEDS 80

CRGB leds[NUM_LEDS];

void setup()
{
  FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  pinMode(8, INPUT);
  pinMode(9, INPUT);
  pinMode(10, INPUT);
  pinMode(11, INPUT);
}

boolean isShooting(){
  return digitalRead(8) == HIGH;
}

boolean isLoading(){
  return digitalRead(9) == HIGH;
}

boolean isEnabled(){
  return digitalRead(10) == HIGH;
}

boolean isLoaded(){
  return digitalRead(11) == HIGH;
}

void loop()
{
  if(isEnabled() == true) {
      rainbow(10);
  }else{
    if(isLoading() == true) {
      fill(100, 0xff1500);
    }else if(isShooting() == true) {
      shoot(10);
    }
    else if(isLoaded() == true){
      //cylon(0xff1500, 20);
      winched(25);
    }
  }
}

void fill(int wait, CRGB color)
{
  int a = 0;
  int b = 39;
  int c = 40;
  int d = 79;
  for(int i = 0; i < 20; i++)
  {
    //0xff1500
    
    leds[a] = color;
    leds[b] = color;
    leds[c] = color;
    leds[d] = color;
    if(isLoading() == false) return;
    FastLED.show();
    /*
    leds[a] = CRGB::Black;
    leds[b] = CRGB::Black;
    leds[c] = CRGB::Black;
    leds[d] = CRGB::Black;
    */
    a++;
    b--;
    c++;
    d--;
    
    delay(wait);
    
  }
  return;
  
}


void shoot(int wait)
{
  int a = 19;
  int b = 20;
  int c = 59;
  int d = 60;
  for(int i = 20; i > 0; i--)
  {
    //0xff1500
    
    leds[a] = CRGB::Black;
    leds[b] = CRGB::Black;
    leds[c] = CRGB::Black;
    leds[d] = CRGB::Black;
    if(isShooting() == false) return;
    FastLED.show();
    /*
    leds[a] = CRGB::Black;
    leds[b] = CRGB::Black;
    leds[c] = CRGB::Black;
    leds[d] = CRGB::Black;
    */
    a--;
    b++;
    c--;
    d++;
    
    delay(wait);
    
  }
  return;
  
}    

void cylon(CRGB color, uint16_t wait)
{

		// Make it look like one LED is moving in one direction
                FastLED.clear();
		for(int led_number = 0; led_number < NUM_LEDS; led_number++)
		{
  
                        if(isShooting()) return;
			//Apply the color that was passed into the function
			leds[led_number] = CRGB::Black;
			//Actually turn on the LED we just set
			FastLED.show();
			// Turn it back off
			leds[led_number] = color;
			// Pause before "going" to next LED
			delay(wait);
		}

		// Now "move" the LED the other direction
		for(int led_number = NUM_LEDS-1; led_number >= 0; led_number--)
		{
                        if(isShooting()) return;
			//Apply the color that was passed into the function
			leds[led_number] = CRGB::Black;
			//Actually turn on the LED we just set
			FastLED.show();
			// Turn it back off
			leds[led_number] = color;
			// Pause before "going" to next LED
			delay(wait);
		}
	
	return;
}

void winched(int wait) {
  int a = 0;
  int b = 39;
  int c = 40;
  int d = 79;
  FastLED.show(0xff1500);
  for(int i = 0; i < 20; i++) {
    
 
    
    leds[a] = CRGB::Black;
    leds[b] = CRGB::Black;
    leds[c] = CRGB::Black;
    leds[d] = CRGB::Black;
    if(isLoaded() == false) return;
    FastLED.show();
    delay(wait);
    leds[a] = 0xff1500;
    leds[b] = 0xff1500;
    leds[c] = 0xff1500;
    leds[d] = 0xff1500;
    a++;
    b--;
    c++;
    d--;
    if(i == 19){
      a = 0;
      b = 39;
      c = 40;
      d = 79;
      i = 0;
    }
  }
}

void pulse(){
  FastLED.clear();
  for(int i = 255; i > 50; i--){
    FastLED.setBrightness(i);
    FastLED.show();
    delay(2);
    if(isEnabled() == true) {
      FastLED.setBrightness(255);
      FastLED.show();
      return;
    }
    
  }
  
  for(int i = 50; i < 256; i++){
    FastLED.setBrightness(i);
    FastLED.show();
    delay(2);
    if(isEnabled() == true) {
      FastLED.setBrightness(255);
      FastLED.show();
      return;
    }
    
  }
  
    
}
 
void rainbow(int wait) 
{

	int hue;
	FastLED.clear();

	for(hue=10; hue<255*3; hue++) 
	{ 
          int a = 0;
          int b = 39;
          int c = 40;
          int d = 79;
          CHSV hsv;
          hsv.hue = hue;
          hsv.val = 255;
          hsv.sat = 255;
          for(int i = 0; i < 20; i++) {
              hsv2rgb_rainbow( hsv, leds[a]);
              hsv2rgb_rainbow( hsv, leds[b]);
              hsv2rgb_rainbow( hsv, leds[c]);
              hsv2rgb_rainbow( hsv, leds[d]);
              a++;
              b--;
              c++;
              d--;
              hsv.hue += 5;
          }
          if(isEnabled() == false) {
            return;
          }		
          FastLED.show();
	  delay(wait);
	}
	return;
}



    
