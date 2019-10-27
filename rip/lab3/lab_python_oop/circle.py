from lab_python_oop.figure import Figure
from lab_python_oop.figure_color import FigureColor
import math


class Circle(Figure):
    def __init__(self, new_radius, new_color):
        self.name = 'круг'
        self.radius = new_radius
        self.color = FigureColor().setColor(new_color)

    def area(self):
        return pow(self.radius, 2)*math.pi

    def __repr__(self):
        return '{}: радиус {}, цвет {}, площадь {}'.format(self.get_name(), self.radius, self.color, self.area())

    def get_name(self):
        return self.name
