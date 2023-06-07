
class PlainOldClass{
public:
  void set(int x){x_=x;};
  int get(){return x_;};
private:
  int x_;
};

int main(){
  PlainOldClass poc;
  poc.set(42);
}  