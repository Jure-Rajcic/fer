
class Base{
public:
  //if in doubt, google "pure virtual"
  virtual void set(int x)=0;
  virtual int get()=0;
};

class CoolClass: public Base{
public:
  virtual void set(int x){x_=x;};
  virtual int get(){return x_;};
private:
  int x_;
};


// clang++ -O0 -S -mllvm --x86-asm-syntax=intel ___.cpp
int main(){
  Base* pb=new CoolClass;
  pb->set(42);
}  