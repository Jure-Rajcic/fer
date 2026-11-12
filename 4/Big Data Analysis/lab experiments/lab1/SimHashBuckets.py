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

def hash2int(band, hash_value):
    binary = bin(int(hash_value, 16))[2:].zfill(128)
    r = 16
    start_bit = (band - 1) * r
    end_bit = start_bit + r
    band_bits = binary[start_bit:end_bit]
    return int(band_bits, 2)

def main():
    n = int(input().strip())
    texts = []
    simhashes = []
    for _ in range(n):
        text = input().strip()
        texts.append(text)
        simhashes.append(generate_simhash(text))
    b = 8
    candidates = {i: set() for i in range(n)}
    for band in range(1, b + 1):
        buckets = {}
        for current_id in range(n):
            hash_value = simhashes[current_id]
            val = hash2int(band, hash_value)
            if val in buckets:
                for text_id in buckets[val]:
                    candidates[current_id].add(text_id)
                    candidates[text_id].add(current_id)
            else:
                buckets[val] = set()
            buckets[val].add(current_id)
    q = int(input().strip())
    for _ in range(q):
        query = input().strip().split()
        i, k = int(query[0]), int(query[1])
        count = 0
        target_hash = simhashes[i]
        for j in candidates[i]:
            if i != j:
                distance = hamming_distance(target_hash, simhashes[j])
                if distance <= k:
                    count += 1
        print(count)
if __name__ == "__main__":
    main()
