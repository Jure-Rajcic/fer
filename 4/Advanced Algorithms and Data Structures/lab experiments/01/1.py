def reverse_sort(names):
    names.sort(reverse=True)
    return names

names = ["Ana", "Petar", "Ana", "Lucija", "Vanja", "Pavao", "Lucija"]
names_desc = reverse_sort(names)
print(names_desc)

selected_names = names_desc[:-1]
print(selected_names)

unique_selected_names = set(selected_names)
print(unique_selected_names)

pass_names = []
for name in unique_selected_names:
    pass_names.append(name + "- pass")

print(pass_names)
