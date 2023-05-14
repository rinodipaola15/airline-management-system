from pymongo import MongoClient

def db_connect(hostname, port, database): 
    client = MongoClient(hostname, port)
    db = client[database]
    return db 
   
def insert_document_into_collection(database, collection, document):
    result = database[collection].insert_one(document)
    print("Inserted document with ID:", result.inserted_id)