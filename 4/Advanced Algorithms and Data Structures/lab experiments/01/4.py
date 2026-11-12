class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def increase_age(self):
        self.age += 1

class PersonDetail(Person):
    def __init__(self, name, age, address):
        super().__init__(name, age)
        self.address = address

# Stvaranje instance klase Person
first_person = Person("Marko", 39)
second_person = Person("Ivan", 17)

# Pozivanje metode increase_age nad instancom second_person
second_person.increase_age()

# Stvaranje instance klase PersonDetail
first_person_detail = PersonDetail("Ana", 25, "Unska 3")

# Pozivanje metode increase_age nad instancom first_person_detail
first_person_detail.increase_age()

# Ispis informacija o objektima
print("First Person:", first_person.name, first_person.age)
print("Second Person:", second_person.name, second_person.age)
print("First Person Detail:", first_person_detail.name, first_person_detail.age, first_person_detail.address)  # noqa: E501
