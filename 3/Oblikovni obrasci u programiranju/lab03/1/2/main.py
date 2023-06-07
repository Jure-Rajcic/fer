import os
from importlib import import_module

def myfactory(class_name, name):
    module = import_module('plugins.' + class_name)
    return getattr(module, class_name.capitalize())(name)


def printGreeting(pet):
    print(f"{pet.name()} pozdravlja: {pet.greet()}")

def printMenu(pet):
    print(f"{pet.name()} voli {pet.menu()}.")

def test():
    pets = []
    for mymodule in os.listdir('plugins'):
        moduleName, moduleExt = os.path.splitext(mymodule)
        if moduleExt == '.py':
            ljubimac = myfactory(moduleName, 'Ljubimac' + str(len(pets)))
            pets.append(ljubimac)

    for pet in pets:
        printGreeting(pet)
        printMenu(pet)

test()
