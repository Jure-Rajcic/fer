'''
funkcija prima 3 parametra:
    prvi je poruka koju kodiramo (tipa string)
    drugi je duljina posmičnog prozora (tipa integer)
    treći je duljina prozora za kodiranje (tipa integer)
    
funkcija kodira poruku algoritmom LZ77

funkcija vraća kodiranu poruku (tipa string)

format kodirane poruke je niz uređenih trojki (x, y, z)
    x predstavlja pomak
    y predstavlja duljinu
    z predstavlja sljedeći simbol
'''

def encode_lz77(codeWord, searchBufferSize, lookAheadBufferSize):

    if codeWord[-1] != "*":
        codeWord += "*"

    # inicijalizacija početnih varijabli
    result = []
    searchBuffer = ""
    lookAheadBuffer = ""

    # u svakom koraku se predana kodna riječ smanjuje, i trenutak kad ona postaje prazan string označava kraj kodiranja
    while codeWord != "":
        
        # određivanje prozora za kodiranje i njegove duljine
        lookAheadBufferLength = min(lookAheadBufferSize, len(codeWord))
        lookAheadBuffer = codeWord[:lookAheadBufferLength]

        # inicijalizacija varijabli za svaki korak
        offset = 0
        length = 0
        matchFound = 0

        # pretraživanje posmičnog prozora i pronalazak najdužeg podudaranja
        for i in range(len(searchBuffer)):
            if searchBuffer[i] == lookAheadBuffer[0]:
                matchFound = 1
                newLength = 0
                j = 0
                searchBufferExtended = searchBuffer + codeWord
                lookAheadBufferExtended = codeWord

                while searchBufferExtended[i + j] == lookAheadBufferExtended[j] and j < lookAheadBufferSize - 1:
                    j += 1
                    newLength += 1
                    
                if newLength >= length:
                    length = newLength
                    offset = i
        
        # računanje pravog odmaka preko izračunate varijable odmaka i informacije je li pronađeno podudaranje
        if offset == 0:
            if matchFound == 0:
                realOffset = 0
            else:
                realOffset = len(searchBuffer)
        else:
            realOffset = len(searchBuffer) - offset
        
        # formatiranje zapisa
        result += [(realOffset,length,codeWord[length])]

        # izračun sljedećeg posmičnog prozora 
        nextSearchBufferLength = min(searchBufferSize, len(searchBuffer) + length + 1)
        currentSearchBufferLength = len(searchBuffer)
        tempWord = searchBuffer + codeWord
        searchBuffer = tempWord[currentSearchBufferLength + length + 1 - nextSearchBufferLength: currentSearchBufferLength + length + 1]
        
        # računanje sljedeće kodne riječi, tj. ostatka trenutne
        codeWord = codeWord[length + 1:]

    return result
