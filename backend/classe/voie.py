class Voie:
    nombre_d_elements = 0

    def __init__(self, Gare1, Gare2, ligne, temps, Bidirectionnel=1):
        self.Gare1 = Gare1
        self.Gare2 = Gare2
        self.ligne = ligne
        self.temps = temps
        self.Bidirectionnel = Bidirectionnel #Si egale a 0 alors Gare1 -> Gare 2 (besoin pour la ligne 10)
        Gare1.add_Voie(self)
        Gare2.add_Voie(self)
        Voie.nombre_d_elements += 1

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)

    def get_other(self, gare):
        if gare == self.Gare1:
            return self.Gare2
        elif gare == self.Gare2:
            return self.Gare1
        else:
            return None

    def __str__(self):
        return (f"Voie(Gare1={self.Gare1.get_attr('name')}, "
                f"Gare2={self.Gare2.get_attr('name')}, "
                f"ligne={self.ligne.get_attr('name')}, temps={self.temps}, "
                f"Bidirectionnel={self.Bidirectionnel})")

    #compare all element with a second voie
    def __eq__(self, other):
        print("a")
        return self.Gare1 == other.Gare1 and self.Gare2 == other.Gare2 and self.ligne == other.ligne and self.temps == other.temps and self.Bidirectionnel == other.Bidirectionnel
