from lab_python_oop.rectangle import Rectangle
from lab_python_oop.circle import Circle
from lab_python_oop.square import Square


def main():
    square = Square(5, 'красный')
    rect = Rectangle(2, 3, 'синий')
    circ = Circle(5, 'зелёный')
    print(rect)
    print(circ)
    print(square)

if __name__ == '__main__':
    main()