import sys
import json
import hashlib
from getpass import getpass
import os
# import password_validator as pv

def hash_password(password, salt, iterations=100000):
    return hashlib.pbkdf2_hmac('sha256', password.encode(), salt, iterations)

def load_users():
    try:
        with open("users.json", "r") as file:
            return json.load(file)
    except FileNotFoundError:
        return {}

def save_users(users):
    with open("users.json", "w") as file:
        json.dump(users, file)

def generate_salt():
    return os.urandom(32)

def add_user(username):
    users = load_users()

    if username in users:
        print(f"User {username} already exists.")
        return False

    password = getpass("Password: ")
    password_repeat = getpass("Repeat Password: ")

    if password != password_repeat:
        print("User add failed. Password mismatch.")
        return False

    salt = generate_salt()
    hashed_password = hash_password(password, salt)

    users[username] = {"password": hashed_password.hex(), "salt": salt.hex(), "force_pass_change": False}

    save_users(users)
    print(f"User {username} successfully added.")
    return True

def change_password(username, force_pass=False):
    users = load_users()
    if username not in users:
        print("User does not exist.")
        return

    password = getpass("Password: ")
    # if not pv.PasswordValidator().validate_user_password(password):
    #     print(pv.PasswordValidator().get_rules())
    #     return
    
    password_repeat = getpass("Repeat Password: ")
    if password != password_repeat:
        print("Password change failed. Password mismatch.")
        return
    
    salt = bytes.fromhex(users[username]["salt"])
    hashed_password = hash_password(password, salt)
    users[username]["password"] = hashed_password.hex()
    users[username]["force_pass_change"] = force_pass
    save_users(users)
    print("Password change successful.")

def force_password_change(username):
    users = load_users()
    if username not in users:
        print("User does not exist.")
        return

    users[username]["force_pass_change"] = True
    save_users(users)
    print(f"User will be requested to change password on next login.")

def delete_user(username):
    users = load_users()
    if username not in users:
        print("User does not exist.")
        return

    del users[username]
    save_users(users)
    print(f"User {username} successfully removed.")

def main():
    if len(sys.argv) < 3:
        print("Usage: usermgmt.py <operation> <username>")
        return

    operation = sys.argv[1]
    username = sys.argv[2]

    if operation == "add":
        add_user(username)
    elif operation == "passwd":
        change_password(username)
    elif operation == "forcepass":
        force_password_change(username)
    elif operation == "del":
        delete_user(username)
    else:
        print("Invalid operation")

if __name__ == "__main__":
    main()
