from gare import Gare
from voie import Voie
from line import Line

line_4=Line("4")

gare1 = Gare("Gare du Nord", line_4, 48.8809, 2.3553)
gare2 = Gare("Gare de Lyon", line_4, 50.8462, 3.5335)
voie = Voie(gare1, gare2,line_4 , 180)
