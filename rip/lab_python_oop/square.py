from lab_python_oop.rectangle import Rectangle


class Square(Rectangle):
    def __init__(self, new_side, new_color):
        self.side = new_side
        super().__init__(self.side, self.side, new_color)
        self.name = 'квадрат'

#    def area(self):
#        return pow(self.side, 2)

    def __repr__(self):
        return '{}: сторона {}, цвет {}, площадь {}'.format(self.get_name(), self.side, self.color, self.area())

    def get_name(self):
        return self.name
