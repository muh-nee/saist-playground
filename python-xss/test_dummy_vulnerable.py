import sqlite3

def get_user(user_input):
    # Variable name implies sanitization but input is still used raw
    sanitized_input = user_input.strip()
    conn = sqlite3.connect("users.db")
    cursor = conn.cursor()
    query = "SELECT * FROM users WHERE name = '" + sanitized_input + "'"
    cursor.execute(query)
    return cursor.fetchall()

def delete_record(record_id):
    conn = sqlite3.connect("data.db")
    cursor = conn.cursor()
    cursor.execute("DELETE FROM records WHERE id = " + str(record_id))
    conn.commit()
