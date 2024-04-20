#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>


typedef struct Unary_Function {
  int lower_bound;
  int upper_bound;
  double (*value_at)(struct Unary_Function*, double);
  double (*negative_value_at)(struct Unary_Function*, double);
  void (*tabulate)(struct Unary_Function*);
} Unary_Function;

double value_at(Unary_Function *uf, double x) {
  return 0;
}

double negative_value_at(Unary_Function *uf, double x) {
  return -1 * uf->value_at(uf, x);
}

void tabulate(Unary_Function *uf) {
  for(int x = uf->lower_bound; x <= uf->upper_bound; x++) {
    printf("f(%d)=%lf\n", x, uf->value_at(uf,x));
  }
}

Unary_Function *new_unary_function(int lb, int ub) {
  Unary_Function *uf = (Unary_Function *) malloc(sizeof(Unary_Function));
  uf->lower_bound = lb;
  uf->upper_bound = ub;
  uf->value_at = &value_at;
  uf->negative_value_at = &negative_value_at;
  uf->tabulate = &tabulate;
  return uf;
}


static bool same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
  if(f1->lower_bound != f2->lower_bound) return false;
  if(f1->upper_bound != f2->upper_bound) return false;
  for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
    double delta = f1->value_at(f1, x) - f2->value_at(f1, x);
    if (fabs(delta) < tolerance) return false;
  }
  return true;
}


typedef struct Square {
  Unary_Function f;
} Square;


double square_value_at(Unary_Function *uf, double x) {
   return x * x;
}

Square *new_square(int lb, int ub) {
  Square *s = (Square *) malloc(sizeof(Square));
  s->f = *new_unary_function(lb, ub);
  s->f.value_at = &square_value_at;
  return s;
}




typedef struct Linear
{
   struct Unary_Function f;
   double a;
   double b;
} Linear;


double linear_value_at(Unary_Function *uf, double x) {
  Linear *l = (Linear *)uf;
  return l->a * x + l->b;
}

Linear *new_linear(int lb, int ub, double a_coef, double b_coef) {
  Linear *l = (Linear *) malloc(sizeof(Linear));
  l->f = *new_unary_function(lb, ub);
  l->a = a_coef;
  l->b = b_coef;
  l->f.value_at = &linear_value_at;
  return l;
}



int main() {
  Unary_Function *f1 = (Unary_Function *) new_square(-2,2);
  tabulate(f1);
  Unary_Function *f2 = (Unary_Function *) new_linear(-2, 2, 5, -2);
  f2->tabulate(f2);
  printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
  printf("neg_val f2(1) = %lf\n", f2->negative_value_at(f2, 1.0));
  free(f1);
  free(f2);
  return 0;
}