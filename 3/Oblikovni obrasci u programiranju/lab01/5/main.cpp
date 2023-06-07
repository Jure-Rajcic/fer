#include <stdio.h>
#include <stdlib.h>

class B {
public:
  virtual int prva()=0;
  virtual int druga(int)=0;
};

class D: public B{
public:
  virtual int prva(){return 42;}
  virtual int druga(int x){return prva()+x;}
};


void fun(B* pb) {

   typedef int *(*FIRST)(B *);
   FIRST *first = *(FIRST **)pb; 
   // first points to the table and can be called with (B*) argument

   typedef int *(*SECOND)(B *, int);
   SECOND *second = *(SECOND **)pb;
   // second points to the table and can be called with (B*, int) argument

   printf("%d\n%d\n", first[0](pb), second[1](pb, 2));
}

int main() {
   fun(new D());
}