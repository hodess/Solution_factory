from line import Line

class Gare:
    def __init__(self, name, Line, x, y):
        self.name = name
        self.ligne = Line
        self.cord = [x, y]
        Line.add_Gare(self)

    def __str__(self):
        return f"Gare(name={self.name}, ligne={self.ligne}, coord=({self.cord[0]}, {self.cord[1]}))"

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)
