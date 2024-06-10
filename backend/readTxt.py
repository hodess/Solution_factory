#from  classe.gare import Gare
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
    for line in LineOfTxt:
        line = line.split(";")[1]
        line_name = line.replace(' ','')
        newLines.append(Line(line_name))
        #print(line_name)

def FillLineWithGare(LineOfTxt,NewLines,LineOfConnexion):
    for line in LineOfTxt:
        line = line.split(";")
        line_name = line[1].replace(' ','')
        for NewLine in NewLines:
            if NewLine.get_attr('name') == line_name:
                print(line_name)
                #getGare 
                copyLine=line[0]
                copyLine=copyLine.split(" ")
                gare_name=""
                for i in range(2,len(copyLine)):
                    gare_name += copyLine[i]
                    print(gare_name)
                #getCoord
                
                
                    
if __name__ == "__main__":   
    LineOfTxt=ReadTxtPoints()
    NewLines=CreateAndFillLine(LineOfTxt)
    
