import re

class PasswordValidator:
    def validate_user_password(self, password):
        if len(password) < 8:
            return False
        if not re.search("[a-z]", password):
            return False
        if not re.search("[A-Z]", password):
            return False
        if not re.search("[0-9]", password):
            return False
        if not re.search("[!@#$%^&*()-_=+{}[]|;:'\",.<>/?~]", password):
            return False
        return True
    
    def get_rules(self):
        return "Password should be at least 8 characters including 1 uppercase letter, 1 lowercase latter, 1 number and 1 special character"
