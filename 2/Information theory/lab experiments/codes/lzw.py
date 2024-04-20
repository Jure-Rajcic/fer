'''
    Funkcija kodira poruku msg na temelju predanog rječnika metodom LZW.
'''
def encode_lzw(D, msg, verbose=False):
    output = []
    
    # Postavi oznaku kraja ako je nema
    if msg[-1] != D[0]:
        msg += D[0]

    # LZW kodiranje
    i=0
    while i<len(msg):
        j=i+1
        if msg[j]==D[0] or j==len(msg)-1:
            output.append(D.index(msg[i:j]))
            return output
        while msg[i:j+1] in D:
            j=j+1
            if msg[j]==D[0] or j==len(msg)-1:
                output.append(D.index(msg[i:j]))
                return output
        if verbose:
            print("D[{}]={}\n".format(len(D),msg[i:j+1]))
        output.append(D.index(msg[i:j]))
        D.append(msg[i:j+1])
        i=j
