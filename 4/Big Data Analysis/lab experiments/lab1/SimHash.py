import hashlib
def generate_simhash(text):
    sh = [0] * 128
    tokens = text.split()
    for token in tokens:
        hash_bytes = hashlib.md5(token.encode('utf-8')).digest()
        hash_bits = ''
        for byte in hash_bytes:
            hash_bits += format(byte, '08b')
        for i in range(128):
            if hash_bits[i] == '1':
                sh[i] += 1
            else:
                sh[i] -= 1
    binary_result = ''
    for i in range(128):
        if sh[i] >= 0:
            binary_result += '1'
        else:
            binary_result += '0'
    hex_result = ''
    for i in range(0, 128, 4):
        hex_digit = int(binary_result[i:i+4], 2)
        hex_result += format(hex_digit, 'x')
    return hex_result
def hamming_distance(hash1, hash2):
    bin1 = bin(int(hash1, 16))[2:].zfill(128)
    bin2 = bin(int(hash2, 16))[2:].zfill(128)
    distance = 0
    for i in range(len(bin1)):
        if bin1[i] != bin2[i]:
            distance += 1
    return distance
def main():
    n = int(input().strip())
    texts = []
    simhashes = []
    for _ in range(n):
        text = input().strip()
        texts.append(text)
        simhashes.append(generate_simhash(text))
    q = int(input().strip())
    for _ in range(q):
        query = input().strip().split()
        i, k = int(query[0]), int(query[1])
        count = 0
        target_hash = simhashes[i]
        for j, hash_value in enumerate(simhashes):
            if i == j:  
                continue
            distance = hamming_distance(target_hash, hash_value)
            if distance <= k:
                count += 1
        print(count)
if __name__ == "__main__":
    main()