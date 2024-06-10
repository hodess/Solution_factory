class Gare:
    def __init__(self, name, x, y):
        self.name = name
        self.ligne = []
        self.cord = [x, y]
        self.voie=[]



    def add_Voie(self,New_Voie):
        self.voie.append(New_Voie)

    def add_line(self, Line):
        self.ligne.append(Line)
        Line.add_Gare(self)


    def __str__(self):
        return f"Gare(name={self.name}, ligne={self.ligne.name}, nb de voie={len(self.voie)}, coord=({self.cord[0]}, {self.cord[1]}))"

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)
