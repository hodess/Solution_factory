# import toute mes classes
from classe.gare import Gare
from classe.voie import Voie
from classe.line import Line


def print_dico(dico):
    for key, value in dico.items():
        print(f"{key.name}: {value}")


def Dijkstra(start, end):
    T={start}
    distance={start:0}
    parent = {start: None}

    for lien in start.voie:
        voisin = lien.get_other(start)
        distance[voisin] = lien.temps
        parent[voisin] = start


    while len(T) < Voie.nombre_d_elements : #verifier si T contient toute les gares
        #print(len(T), Voie.nombre_d_elements)

        # Trouver le noeud t dans V - T avec la plus petite distance
        t = None
        min_distance = float('inf')

        for gare, dist in distance.items():
            if gare not in T and dist < min_distance:
                min_distance = dist
                t = gare

        if t == end:
            break
        elif t is None:  # Si t est None, cela signifie qu'il n'y a plus de noeud accessible
            return None,None

        # Ajouter t à T
        T.add(t)



        # Mettre à jour les distances pour les voisins de t
        for lien in t.voie:
            voisin = lien.get_other(t)
            if voisin not in T:
                new_distance = distance[t] + lien.temps
                if new_distance < distance.get(voisin, float('inf')):
                    distance[voisin] = new_distance
                    parent[voisin] = t

        # Construction du chemin depuis start jusqu'à end

    if end not in distance.keys():
        return None,None

    chemin = []
    current = end
    while current is not None:
        chemin.insert(0, current)
        current = parent[current]

    if distance[end] == float('inf'):
        return None,None

    return chemin, distance[end]












def main():
    #tu peux me creer des gares et des voies pour tester

    line_5 = Line("5")
    line_4 = Line("4")
    line_0 = Line("0")



    #gare1 = Gare("Gare du Nord_4", 48.8809, 2.3553)
    gare1_2 = Gare("Gare du Nord_5", 48.8809, 2.3553)

    gare2 = Gare("Gare de Lyon", 50.8462, 3.5335)

    gare3 = Gare("Gare de l'est", 48.8809, 2.3553)

    gare4 = Gare("Gare Montparnasse", 50.8462, 3.5335)

    gare7 = Gare("Chatelet", 50.8462, 3.5335)

    # Nord -> Nord ligne 0
    #Voie(gare1, gare1_2, line_0, 60)

    # Nord - > Lyon ligne 4
    #Voie(gare1, gare2, line_4, 180)

    # Nord -> est -> Montparnasse ligne 5
    Voie(gare3, gare4, line_5, 120)
    Voie(gare1_2, gare3, line_5, 300)

    start= gare1_2
    end = gare4


    chemin,temps=Dijkstra(start, end)
    if chemin:
        print("chemin trouvé entre les gares : ", start.name, " et ", end.name)
        print("Chemin trouvé: ", [gare.name for gare in chemin])
        print("Temps: ", temps)
    else:
        print("Pas de chemin trouvé entre les gares : ", start.name, " et ", end.name)


import random
import time

def sec_main(nb_gare):
    lines = [Line(str(i)) for i in range(5)]

    gares = []
    for i in range(nb_gare):
        gares.append(Gare(f"Gare {i}", 48.8809 + i * 0.01, 2.3553 + i * 0.01))

    for i in range(nb_gare*2):
        sec_gare= random.choice(gares)
        while gares[int(i/2)] == sec_gare:  # Assurez-vous que start et end ne sont pas les mêmes
            sec_gare = random.choice(gares)
        rand_temp= random.randint(60, 300)
        rand_ligne = random.choice(lines)
        #print(gares[int(i/2)].name,sec_gare.name,"ligne=",rand_ligne.name)

        Voie(gares[int(i/2)], sec_gare, rand_ligne, rand_temp)  # Voie bidirectionnelle seulement

    start = random.choice(gares)
    end = random.choice(gares)

    while start == end:  # Assurez-vous que start et end ne sont pas les mêmes
        end = random.choice(gares)
    print(start.name,end.name)

    for i in lines:
        print(i,"\n")

    debut = time.time()
    chemin, temps = Dijkstra(start, end)
    fin = time.time()



    if chemin:
        print("Chemin trouvé:", [gare.name for gare in chemin])
        print("Temps:", temps)
    else:
        print("Pas de chemin trouvé entre les gares :", start.name, "et", end.name)

    print("Temps d'exécution:", fin - debut, "secondes")





if __name__ == "__main__":
    sec_main(10000)
    #main()



