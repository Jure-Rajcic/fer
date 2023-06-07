#include <stdio.h>

class Base{
public:
  Base() {
    // *vtable immediately points to table which holds Base::virtualMethod()
    metoda();
  }


  virtual void virtualnaMetoda() {
    printf("ja sam bazna implementacija!\n");
  }

  void metoda() {
    printf("Metoda kaze: ");
    virtualnaMetoda();
  }
};

class Derived: public Base{
public:
  Derived(): Base() {
    // *vtable immediately points on table which holds Derived::virtualMethod()
    metoda();
  }
  virtual void virtualnaMetoda() {
    printf("ja sam izvedena implementacija!\n");
  }
};

int main(){
  Derived* pd=new Derived();
  pd->metoda();
}