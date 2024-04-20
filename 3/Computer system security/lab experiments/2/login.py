import sys
import json
import hashlib
from getpass import getpass
# import password_validator as pv


def hash_password(password, salt, iterations=100000):
    return hashlib.pbkdf2_hmac('sha256', password.encode(), salt, iterations)

def load_users():
    try:
        with open("users.json", "r") as file:
            return json.load(file)
    except FileNotFoundError:
        return {}

def login(username):
    users = load_users()
    password = getpass("Password: ")
    if username not in users:
        return False
    user = users[username]
    salt = bytes.fromhex(users[username]["salt"])
    hashed_password = hash_password(password, salt)

    if user["password"] == hashed_password.hex(): 
        if user["force_pass_change"]:
            print("Administrator wants you to change password.")

            new_password = getpass("New password: ")
            # if not pv.PasswordValidator().validate_user_password(password):
            #     print(pv.PasswordValidator().get_rules())
            #     return False
            
            new_password_repeat = getpass("Repeat new password: ")
            if new_password != new_password_repeat:
                print("Password change failed. Password mismatch.")
                return False
            
            

            user["password"] = hash_password(new_password, salt).hex()
            user["force_pass_change"] = False
            save_users(users)
            print("Password change successful.")
        return True
    return False

def save_users(users):
    with open("users.json", "w") as file:
        json.dump(users, file)

def main():
    if len(sys.argv) < 2:
        print("Usage: login.py <username>")
        return

    username = sys.argv[1]

    login_successful = login(username)
    while not login_successful:
        print("Username or password incorrect.")
        login_successful = login(username)
    print("Login successful.")
    

if __name__ == "__main__":
    main()
