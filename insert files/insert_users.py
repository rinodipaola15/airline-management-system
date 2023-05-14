import requests
import random
import names
import sys

countries = ['United States','Italy', 'Japan', 'Spain', 'United Kingdom']

args = sys.argv
num_users = int(args[1])

if __name__ == '__main__':
    
    # insert users
    
    addUser_url = 'http://airline-management-system.app.loc/user-service/users/addUser'
    
    for i in range(num_users):
        first_name = names.get_first_name()
        last_name = names.get_last_name()
        email = first_name+"."+last_name+str(random.randint(1, 1000))+"@gmail.com"
        birth_day = str(random.randint(1, 31))+str(random.randint(1, 12))+str(random.randint(1900, 2023))
        country = random.choice(countries)
        phone_number = str(random.randint(1111111111, 9999999999))
        user_document = {"firstName": first_name, "lastName": last_name, "email": email, "birthDate": birth_day, "country": country, "phoneNumber": phone_number}
        x = requests.post(addUser_url, json = user_document)
        print(x.text) 