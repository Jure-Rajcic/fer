# ZADATAK: https://www.fer.unizg.hr/_download/repository/SRS_-_LV_1_-_Simetricna_kriptografija[1].pdf
# HELP: https://www.youtube.com/watch?v=O8596GPSJV4&t=5s&ab_channel=NeuralNine

import os
from cryptography.fernet import Fernet
from cryptography.fernet import InvalidToken
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
from cryptography.hazmat.primitives import hashes
import base64

SEPARATOR = " "
YES = "YES"
NO = "NO"

class ReplacementAttackException(Exception):
    message = "password replacement attack recognized"

class PasswordManager:
    def __init__(self):
        self.fernet = None
        self.password_file = "passwords.txt"
        self.salt_file = "salt.txt"

    ## FUNKCIONALNI 1.
    def create_key(self, master_password):

        # create empty file (if doesn't exist) for storing address and passwords
        if os.path.isfile(self.password_file):
            print(f"passwords in {self.password_file} file would be lost, are you sure you want to continue?")
            decision = input(f"Enter '{YES}' or '{NO}': ")
            while decision != YES and decision != NO:
                decision = input(f"Enter '{YES}' or '{NO}': ")
            if decision == NO:
                return
        with open(self.password_file, "w") as f:
            print(f"Empty password file created")

        # create file with salt data
        with open(self.salt_file, 'wb') as f:
            f.write(os.urandom(16)) # Generate a random 16-byte salt

        self.initialize_key(master_password)


    def initialize_key(self, master_password):
        # Read salt data from file
        if not os.path.isfile(self.salt_file):
            print(f"could't find valid {self.salt_file}, please create key with (1)")
            return
        with open(self.salt_file, 'rb') as f:
            salt = f.read()
        # Derive the encryption key using PBKDF2 with SHA256 as the hash function
        kdf = PBKDF2HMAC(algorithm=hashes.SHA256(), length=32, salt=salt, iterations=100000)
        key = kdf.derive(master_password.encode('utf-8'))
        key = base64.urlsafe_b64encode(key)
        self.fernet = Fernet(key)
        print(f"key initialized: {key}")


    ## SIGORNOSNI 1 i 2
    def encrypt_string(self, string):
        return self.fernet.encrypt(string.encode('utf-8')).decode('utf-8')
         
    def decrypt_string(self, string):
        string = string.strip().encode('utf-8')
        string_b64 = base64.b64encode(string).decode('utf-8')
        string_decoded = base64.b64decode(string_b64.encode('utf-8'))
        return self.fernet.decrypt(string_decoded).decode('utf-8')

    ## FUNKCIONALNI 2
    def save_password(self, site, password):
        if self.fernet == None:
            print("You need to initialize key before getting password for any site")
            return
        with open(self.password_file, 'r+') as f:
            # readLines uses buffer to optimize reading data from disk
            lines = f.readlines()
        index = 0
        for i in range(0, len(lines)):
            chipper_site = lines[i].strip().split(SEPARATOR)[0]
            if self.decrypt_string(chipper_site) == site:
                break
            index += 1
        cipher_site = self.encrypt_string(site)
        ## SIGURNOSNI 3
        cipher_site_password =  self.encrypt_string(f"{site}{SEPARATOR}{password}")
        line = f"{cipher_site}{SEPARATOR}{cipher_site_password}\n"
        if (index == len(lines)):
            lines.append(line)
            print(f"[{site}]:[{password}] ADDED")
        else:
            lines[index] = line
            print(f"[{site}]:[{password}] UPDATED")

        with open(self.password_file, 'w') as f:
            # writelines uses buffer to optimize writing data to disk
            f.writelines(lines)

    ## FUNKCIONALNI 3
    def get_password(self, site) -> str:
        if self.fernet == None:
            print("You need to initialize key before getting password for any site")
            return
        with open(self.password_file, 'r+') as f:
            for line in f:
                chipper_site, cipher_site_password = line.split(SEPARATOR)
                if (self.decrypt_string(chipper_site) == site):
                    decrypted_site, decrypted_password = self.decrypt_string(cipher_site_password).split(SEPARATOR)
                    if (decrypted_site == site):
                        return decrypted_password
                    raise ReplacementAttackException
        return None


def main():

    pm = PasswordManager()

    def show_menu():
        print("""What do you want to do?
        (1) Create new master password [asks for overriding permissions if password file already exists]
        (2) Initialize existing key with master password [gives you access to all your saved passwords]
        (3) Save or update password for site 
        (4) Get password for site
        (m) Show menu
        (q) Quit""")

    show_menu()
    done = False
    while not done:
        print()
        choice = input ("Enter your choice: ")
        if choice == "1":
            master_password = input ("Enter new master password: ")
            pm.create_key(master_password)
        elif choice == "2":
            master_password = input ("Enter existing master password: ")
            pm.initialize_key(master_password)
        elif choice == "3":
            site = input("Enter site: ").strip()
            password = input("Enter password for site: ").strip()
            try :
                pm.save_password(site, password)
            except InvalidToken:
                print("Master password is incorrect")
        elif choice == "4":
            site = input("What site do you want: ")
            try :
                print(f"password for site [{site}] is [{pm.get_password(site)}]")
            except InvalidToken:
                print("Master password is incorrect")
            except ReplacementAttackException as e:
                print(e.message)
        elif choice == "m":
            show_menu()
        elif choice == "q":
            done = True
            print("Bye!")
        else:
            print("Invalid choice!")


if __name__ == "__main__":
    main()



