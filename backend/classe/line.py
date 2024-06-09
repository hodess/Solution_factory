class Line:
    def __init__(self, name):
        self.name = name
        self.liste_Gare=[]

    def add_Gare(self,New_gare):
        self.liste_Gare.append(New_gare)

    def get_attr(self, attr_name):
        return getattr(self, attr_name, None)

    def __str__(self):
        gares = ', '.join(gare.name for gare in self.liste_Gare)
        return f"Line(ligne={self.name}, liste_Gare=[{gares}])"
