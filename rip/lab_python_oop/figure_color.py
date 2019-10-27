
class FigureColor:
    def __init__(self):
        self._color = None

    def getColor(self):
        return self._color

    def setColor(self, new_color):
        self._color = new_color
        return self._color
    color = property(getColor, setColor)
