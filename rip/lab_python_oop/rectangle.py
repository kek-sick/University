from lab_python_oop.figure import Figure
from lab_python_oop.figure_color import FigureColor


class Rectangle(Figure):
    def __init__(self, new_height, new_width, new_color):
        self.height = new_height
        self.width = new_width
        self.color = FigureColor().setColor(new_color)
        self.name = 'прямоугольник'

    def area(self):
        return self.height*self.width

    def __repr__(self):
        return '{}: высота {}, ширина {}, цвет {}, площадь {}'.format(self.get_name(), self.height, self.width ,self.color, self.area())

    def get_name(self):
        return self.name
