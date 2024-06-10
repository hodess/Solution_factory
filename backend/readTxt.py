from  classe.gare import Gare
#from  backend.classe.voie import Voie
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
    file=open('backend/data/connexions.txt','r')
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



def FillLineWithGare(LineOfTxt,NewLines,PosTxt):
    Gares=[]
    for line in LineOfTxt:
        line = line.split(";")
        line_name = line[1].replace(' ','')
        for NewLine in NewLines:
            if NewLine.get_attr('name') == line_name:
                #print(line_name)
                #getGare 
                copyLine=line[0]
                copyLine=copyLine.split(" ")
                gare_name=""
                for i in range(2,len(copyLine)):
                    gare_name += copyLine[i]
                    #print(gare_name)
                
                #getCoord
                gareTraited=[]
                for pos in PosTxt:
                    pos = pos.split(";")
                    if pos[2].replace("\n","") == gare_name:
                        if pos[2].replace("\n","") not in gareTraited:
                            gareTraited.append(pos[2].replace("\n",""))
                            x = pos[0]
                            y = pos[1]
                            gare = Gare(gare_name,NewLine,x,y)
                            Gares.append(gare)
                        
    return Gares


# def GetIDGare()
                
                    
if __name__ == "__main__":   
    LineOfTxt=ReadTxtPoints()
    PosTxt=GetLinesInPos()
    NewLines=CreateAndFillLine(LineOfTxt)
    Gares=FillLineWithGare(LineOfTxt,NewLines,PosTxt)
    for gare in Gares:
        print(gare)
    
