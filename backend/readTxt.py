#from  backend.classe.gare import Gare
#from  backend.classe.voie import Voie
from backend.classe.line import *
import re

def ReadTxt():
    file=open('backend/data/metro.txt','r')
    pattern = re.compile(r'^V \d{4}')
    lines=file.readlines()
    filtered_lines = [line for line in lines if pattern.match(line)]
    #for line in filtered_lines:
        #print(line)
    return filtered_lines

def CreateAndFillLine(LineOfTxt):
    for line in LineOfTxt:
        line = line.split()
        line_name = line[2]
        newLine = Line(line_name)
        

LineOfTxt=ReadTxt()
CreateAndFillLine(LineOfTxt)
