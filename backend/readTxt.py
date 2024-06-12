from  classe.gare import Gare
from classe.voie import Voie
from classe.line import Line
import re

def ReadTxtPoints():
    file=open('backend/data/metro.txt','r')
    pattern = re.compile(r'^V \d{4}')
    lines=file.readlines()
    filtered_lines = [line for line in lines if pattern.match(line)]
    #for line in filtered_lines:
        #print(line)
    return filtered_lines

def ReadTxtConnexions():
    file=open('backend/data/metro.txt','r')
    pattern = re.compile(r'^E \d')
    lines=file.readlines()
    filtered_lines = [line for line in lines if pattern.match(line)]
    #for line in filtered_lines:
        #print(line)
    return filtered_lines

def CreateAndFillLine(LineOfTxt):
    newLines=[]
    list_names = []
    for line in LineOfTxt:
        line = line.split(";")[1]
        line_name = line.replace(' ','')
        if line_name not in list_names:
            list_names.append(line_name)
        #print(line_name)
    for e in list_names:
        newLine = Line(e)
        newLines.append(newLine)
    return newLines

def GetLinesInPos():
    file=open('backend/data/pospoints.txt','r')
    lines=file.readlines()
    return lines



def FillLineWithGare(LineOfTxt, NewLines, PosTxt):
    Gares = []
    for line in LineOfTxt:
        line = line.split(";")
        line_name = line[1].replace(' ', '')
        id = line[0][2:6]
        for NewLine in NewLines:
            if NewLine.get_attr('name') == line_name:
                copyLine = line[0].split(" ")
                gare_name = " ".join(copyLine[2:]).strip()
                gare_name = re.sub(r'[\s\n]+', ' ', gare_name).strip()

                gareTraited = []
                for pos in PosTxt:
                    pos = pos.split(";")
                    normalized_pos2 = re.sub(r'[\s\n]+', ' ', pos[2]).strip()
                    if normalized_pos2 == gare_name:
                        if normalized_pos2 not in gareTraited:
                            gareTraited.append(normalized_pos2)
                            x = pos[0]
                            y = pos[1]
                            gare = Gare(gare_name, NewLine, x, y, id)
                            Gares.append(gare)
    return Gares


def CreateAndFillVoies(Connections, Gares):
    Voies = []
    for connection in Connections:
        connection = connection.split(" ")
        IdGare1 = connection[2]
        IdGare2 = connection[3]
        time = connection[1]
        Gare1 = None
        Gare2 = None
        for gare in Gares:
            if int(gare.get_attr('id')) == int(IdGare1):
                Gare1 = gare
            if int(gare.get_attr('id')) == int(IdGare2):
                Gare2 = gare
        
        # Vérification si les gares ont été trouvées
        if not Gare1 or not Gare2:
            print(f"Erreur : Gare1 ou Gare2 non trouvée pour la connexion {connection}. Gare1: {Gare1}, Gare2: {Gare2}")
            continue  # Passe à la connexion suivante
        else:
        # Si les deux gares sont trouvées, on crée une Voie
            Line = Gare1.get_attr('ligne')
            #si voies ne contients pas déjà un élément avec Gare1 et Gare2 on ajoute
            if not any(voie.Gare1.name == Gare1.name and voie.Gare2.name == Gare2.name for voie in Voies):
                Voies.append(Voie(Gare1, Gare2, Line, time))
            #Voies.append(Voie(Gare1, Gare2, Line, time))
    return Voies


        
                
                    
if __name__ == "__main__":   
    LineOfTxt=ReadTxtPoints()
    PosTxt=GetLinesInPos()
    NewLines=CreateAndFillLine(LineOfTxt)
    # for e in NewLines:
    #     print(e)
    Gares=FillLineWithGare(LineOfTxt,NewLines,PosTxt)
    # for e in Gares:
    #     print(e)
    Voies=CreateAndFillVoies(ReadTxtConnexions(),Gares)
    for voie in Voies:
        print(voie)
    
