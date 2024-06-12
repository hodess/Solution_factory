# import toute mes classes
from classe.gare import Gare
from classe.voie import Voie
from classe.line import Line


def print_dico(dico):
    for key, value in dico.items():
        print(f"{key.name}: {value}")

def print_liste(liste):
    for i in liste:
        print(i.name,end=" -> ")
    print()

def print_liste_with_time(liste):
    for i in range(len(liste)-1):
        print(liste[i].name,liste[i].find_voie(liste[i+1]).temps,end=" -> ")
    print(liste[-1].name)
    print()

def print_liste_voie(liste):
    for i in liste:
        print(i.Gare1.name," ",i.Gare2.name,end=" -> ")
    print()


#filtre_non est une liste de ligne a ne pas prendre en compte
def Dijkstra(start, end,filtre_voie=[],filtre_line=[]):
    T={start}
    distance={start:0}
    parent = {start: None}

    for lien in start.voie:
        if (lien.ligne not in filtre_line) and (lien not in filtre_voie):
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
        for lien in t.voie :
            voisin = lien.get_other(t)
            if (voisin not in T) and (lien.ligne not in filtre_line) and (lien not in filtre_voie):
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

def find_temps(chemin):
    temps=0
    for i in range(len(chemin)-1):
        voie=chemin[i].find_voie(chemin[i+1])
        temps+=voie.temps
    return temps


def YenKSP(start, end, k):
    #le premier chemin est le chemin le plus court
    A,temps=Dijkstra(start,end)
    if A is None:
        return [None],[None]
    A=[A]
    temps_all=[temps]
    #initialisation de la liste des chemins les plus courts
    B=[]
    B_temp=[]

    for k in range(0,k):
        for j in range(len(A[k])-1):
            spurnode = A[k][j]
            rootpath = A[k][:j]

            filtre_voie = []
            #trouver les chemins a supprimer
            for i in range(len(A)):
                for j in range(len(A[i])-1):
                    for l in rootpath:
                        if l == A[i][j]:
                            filtre_voie.append(A[i][j].find_voie(A[i][j+1]))

                    if A[i][j]==spurnode:
                        filtre_voie.append(A[i][j].find_voie(A[i][j+1]))




            spurPaht,temps= Dijkstra(spurnode,end,filtre_voie)
            if spurPaht is None:
                continue

            totalPath = rootpath + spurPaht
            rootpath.append(spurnode)
            temps+=find_temps(rootpath)

            if totalPath not in B:
                B.append(totalPath)
                B_temp.append(temps)


        if len(B) == 0:
            break

        #trouver le chemin le plus court
        index_min_time=0
        for i in range(1,len(B)):
            if B_temp[index_min_time] > B_temp[i]:
                index_min_time = i
        A.append(B[index_min_time])
        temps_all.append(B_temp[index_min_time])


        #supprimer le chemin le plus court de B
        B.pop(index_min_time)
        B_temp.pop(index_min_time)

    return A,temps_all


def main():
    #tu peux me creer des gares et des voies pour tester

    line_5 = Line("5")
    line_4 = Line("4")
    line_0 = Line("0")



    gare1 = Gare("Gare du Nord_4", line_4,48.8809, 2.3553)
    gare1_2 = Gare("Gare du Nord_5",line_5, 48.8809, 2.3553)

    gare2 = Gare("Gare de Lyon",line_4 ,50.8462, 3.5335)

    gare3 = Gare("Gare de l'est", line_5,48.8809, 2.3553)

    gare4 = Gare("Gare Montparnasse", line_5,50.8462, 3.5335)

    gare7 = Gare("Chatelet", line_5,50.8462, 3.5335)

    # Nord -> Nord ligne 0
    Voie(gare1, gare1_2, line_0, 60)

    # Nord - > Lyon ligne 4
    Voie(gare1, gare2, line_4, 180)

    # Nord -> est -> Montparnasse -> Chatelet ligne 5
    Voie(gare3, gare4, line_5, 120)
    Voie(gare1_2, gare3, line_5, 300)
    Voie(gare4, gare7, line_5, 180)

    #Chatelet -> Lyon ligne 4
    Voie(gare7, gare2, line_4, 120)

    start= gare1
    end = gare7
    print("start=",start.name,"end=",end.name)

    """
    chemin,temps=Dijkstra(start, end,[voie1,voie2])
    if chemin:
        print("chemin trouvé entre les gares : ", start.name, " et ", end.name)
        print("Chemin trouvé: ", [gare.name for gare in chemin])
        print("Temps: ", temps)
    else:
        print("Pas de chemin trouvé entre les gares : ", start.name, " et ", end.name)
    """

    """
    chemins,temps=first_Yens(start,end,3)
    if chemins:
        print("chemin trouvé entre les gares : ", start.name, " et ", end.name)
        for chemin in chemins:
            print("Chemin trouvé: ", [gare.name for gare in chemin])
        print("Temps: ", temps)
    else:
        print("Pas de chemin trouvé entre les gares : ", start.name, " et ", end.name)"""

    result,temps=YenKSP(start, end, 3)

    print("\n\n")
    print("final : ")
    if temps[0] is None:
        print("Pas de chemin trouvé entre les gares : ", start.name, " et ", end.name)
    else :
        for i in range(len(result)):
            print("Chemin trouvé: ", [gare.name for gare in result[i]])
            print("Temps: ", temps[i])
            print("\n")


import random
import time

def sec_main(nb_gare,nb_de_demande):
    lines = [Line(str(i)) for i in range(5)]

    gares = []
    for i in range(nb_gare):
        gares.append(Gare(f"Gare {i}",lines[0], 48.8809 + i * 0.01, 2.3553 + i * 0.01))

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



    debut = time.time()
    result, temps = YenKSP(start, end, nb_de_demande)
    fin = time.time()

    print("Temps d'execution : ", fin - debut)
    print("")
    print("final : ")
    if temps[0] is None:
        print("Pas de chemin trouvé entre les gares : ", start.name, " et ", end.name)
    else:
        for i in range(len(result)):
            print_liste_with_time(result[i])
            print("Temps: ", temps[i])
            print("\n")





if __name__ == "__main__":
    random.seed(87)
    sec_main(308,5)
    #main()


