int inputOpen = 2;
int inputClose = 3;
int outOpen = 4;
int outClose = 5;
int firstIn = 1;
int secondIn = 1;

void setup() {
  Serial.begin(9600);
  pinMode(inputOpen, INPUT_PULLUP);
  pinMode(inputClose, INPUT_PULLUP);
  pinMode(outOpen, OUTPUT);
  pinMode(outClose, OUTPUT);
}

void loop() {
  firstIn = digitalRead(inputOpen);
  secondIn = digitalRead(inputClose);

  if (!firstIn && secondIn){
    digitalWrite(outOpen, HIGH);
    Serial.println("Gates opening");
  }else{    
    digitalWrite(outOpen, LOW);
  }

  if (firstIn && !secondIn){
    digitalWrite(outClose, HIGH);  
    Serial.println("Gates closing");
  }else{    
    digitalWrite(outClose, LOW); 
  }
  delay(400);
}
