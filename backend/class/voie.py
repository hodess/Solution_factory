class Voie:
    def __init__(self, Gare1, Gare2, ligne, temps, Bidirectionnel=1):
        self.Gare1 = Gare1
        self.Gare2 = Gare2
        self.ligne = ligne
        self.temps = temps
        self.Bidirectionnel = Bidirectionnel #Si egale a 0 alors Gare1 -> Gare 2 (besoin pour la ligne 10)

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)

    def __str__(self):
        return (f"Voie(Gare1={self.Gare1.get_attr('name')}, "
                f"Gare2={self.Gare2.get_attr('name')}, "
                f"ligne={self.ligne.get_attr('name')}, temps={self.temps}, "
                f"Bidirectionnel={self.Bidirectionnel})")
