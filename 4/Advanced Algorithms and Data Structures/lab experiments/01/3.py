import numpy as np

# Definirajte vektore kao jednostupčane
vector_a = np.array([1, 3, 5])
vector_b = np.array([2, 4, 6])

# Vanjski umnožak
mat_mul = np.outer(vector_a, vector_b)

# Skalarni produkt
vect_dot = np.dot(vector_a, vector_b)

# Potenciranje na drugu potenciju
mat_exp = mat_mul ** 2

# Izdvajanje podmatrice 2x2 u četvrtom kvadrantu
sub_mat = mat_exp[2:, 2:]

print("Vanjski umnožak:")
print(mat_mul)

print("\nSkalarni produkt:")
print(vect_dot)

print("\nPotencirani umnožak:")
print(mat_exp)

print("\nPodmatrica 2x2 u četvrtom kvadrantu:")
print(sub_mat)
