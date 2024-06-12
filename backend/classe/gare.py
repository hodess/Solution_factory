class Gare:
    def __init__(self, name,ligne, x, y):
        self.name = name
        self.ligne = ligne
        self.cord = [x, y]
        self.voie=[]
        ligne.add_Gare(self)



    def add_Voie(self,New_Voie):
        self.voie.append(New_Voie)



    def __str__(self):
        return f"Gare(name={self.name}, ligne={self.ligne.name}, nb de voie={len(self.voie)}, coord=({self.cord[0]}, {self.cord[1]}))"

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)

    def find_voie(self,Gare2):
        for voie in self.voie:
            if voie.get_other(self) == Gare2:
                return voie
        return None