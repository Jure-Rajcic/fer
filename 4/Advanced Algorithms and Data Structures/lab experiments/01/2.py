person_data = {"Ana": 1995, "Zoran": 1978, "Lucija": 2001, "Anja": 1997}

# Umanjivanje godina rođenja za jedan
for name in person_data:
    person_data[name] -= 1
print(person_data)


year_age = []

# Izračunavanje dobi i stvaranje n-torki
for birth_year in person_data.values():
    age = 2022 - birth_year
    tuple = (birth_year, age)
    year_age.append(tuple)

print(year_age)
