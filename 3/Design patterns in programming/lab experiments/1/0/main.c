#include <stdio.h>

struct Person {
    int age;
    // here ve cant create methods but we can create variables -> pointer variables on functions
    void(*set)(struct Person *, int);
    int (*get)(struct Person *);
};

void setAge(struct Person * p,  int age) {
    p->age = age; 
}

int getAge(struct Person * p) {
    return p->age;
}



int main(void) {
    struct Person p1;
    p1.set = setAge;
    p1.get = getAge;

    p1.set(&p1, 18);
    printf("The age is %d", p1.get(&p1));
    return 0;
}