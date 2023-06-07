# Generic Programming (Python)
def mymax(iterable, key=lambda x: x):
    max_x = max_key = None
    for x in iterable:
        if max_key is None or key(x) > max_key:
            max_x, max_key = x, key(x)
    return max_x


strings = ["Gle", "malu", "vocku", "poslije", "kise"]
longest_word = mymax(strings, key=lambda x: len(x))
print(longest_word) 

maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
print(maxint)  

maxchar = mymax("Suncana strana ulice")
print(maxchar) 

maxstring = mymax([
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"
])
print(maxstring)  

D = {'burek': 8, 'buhtla': 5}
most_expensive_item = mymax(D.keys(), key=D.get)
print(most_expensive_item) 

people = [("A", "A"), ("A", "B"), ("B", "A"), ("B", "B")]
last_person = mymax(people)
print(last_person) 
