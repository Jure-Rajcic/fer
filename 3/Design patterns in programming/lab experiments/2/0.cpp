#include <iostream>
#include <cassert>
#include <cstdlib>

struct Point {
    int x;
    int y;
};

struct Shape {
    enum EType { circle, square, rhomb };
    EType type_;
};

struct Circle {
    Shape::EType type_;
    double radius_;
    Point center_;
};

struct Square {
    Shape::EType type_;
    double side_;
    Point center_;
};

struct Rhomb {
    Shape::EType type_;
    double diagonal1_;
    double diagonal2_;
    Point center_;
};

void drawSquare(struct Square* s) {
    std::cerr << "in drawing square: " << s->side_ << " " << s->center_.x << " " << s->center_.y << "\n";
}

void drawCircle(struct Circle* c) {
    std::cerr << "in drawing circle: " << c->radius_ << " " << c->center_.x << " " << c->center_.y << "\n";
}

void drawRhomb(struct Rhomb* r) {
    std::cerr << "in drawing rhomb: " << r->diagonal1_ << " " << r->diagonal2_ << " " << r->center_.x << " " << r->center_.y << "\n";
}

void drawShapes(Shape** shapes, int n) {
    for (int i = 0; i < n; ++i) {
        struct Shape* s = shapes[i];
        switch (s->type_) {
        case Shape::square:
            drawSquare((struct Square*)s);
            break;
        case Shape::circle:
            drawCircle((struct Circle*)s);
            break;
        case Shape::rhomb:
            drawRhomb((struct Rhomb*)s);
            break;
        default:
            assert(0);
            exit(0);
        }
    }
}

void moveShapes(Shape** shapes, int n, int dx, int dy) {
    for (int i = 0; i < n; ++i) {
        struct Shape* s = shapes[i];
        switch (s->type_) {
        case Shape::square: {
            struct Square* sq = (struct Square*)s;
            sq->center_.x += dx;
            sq->center_.y += dy;
            break;
        }
        case Shape::circle: {
            struct Circle* c = (struct Circle*)s;
            c->center_.x += dx;
            c->center_.y += dy;
            break;
        }
        case Shape::rhomb: {
            struct Rhomb* r = (struct Rhomb*)s;
            r->center_.x += dx;
            r->center_.y += dy;
            break;
        }
        default:
            assert(0);
            exit(0);
        }
    }
}

int main() {
    Shape* shapes[5];
    shapes[0] = (Shape*)new Circle;
    shapes[0]->type_ = Shape::circle;

    shapes[1] = (Shape*)new Square;
    shapes[1]->type_ = Shape::square;
    
    shapes[2] = (Shape*)new Square;
    shapes[2]->type_ = Shape::square;
    
    shapes[3] = (Shape*)new Circle;
    shapes[3]->type_ = Shape::circle;
    
    shapes[4] = (Shape*)new Rhomb;
    shapes[4]->type_ = Shape::rhomb;

    std::cerr << "drawing shapes before moving \n";
    drawShapes(shapes, 5);

    std::cerr << "drawing shapes after moving\n";
    moveShapes(shapes, 5, 2, 1);
    drawShapes(shapes, 5);
}
