#!/usr/bin/env python3

import sqlite3
import subprocess
import os

def setup_vulnerable_database():
    conn = sqlite3.connect(':memory:')
    cursor = conn.cursor()
    
    cursor.execute('''
        CREATE TABLE users (
            id INTEGER PRIMARY KEY,
            username TEXT,
            email TEXT,
            role TEXT
        )
    ''')
    
    cursor.execute("INSERT INTO users VALUES (1, 'admin', 'admin@example.com', 'admin')")
    cursor.execute("INSERT INTO users VALUES (2, 'user', 'user@example.com', 'user')")
    
    conn.commit()
    return conn

def vulnerable_user_lookup(conn, user_id):
    cursor = conn.cursor()
    
    query = f"SELECT * FROM users WHERE id = {user_id}"
    print(f"Executing query: {query}")
    
    try:
        cursor.execute(query)
        results = cursor.fetchall()
        
        for row in results:
            user_data = {
                'id': row[0],
                'username': row[1],
                'email': row[2],
                'role': row[3]
            }
            
            if user_data['role'] == 'admin':
                log_command = f"echo 'Admin {user_data['username']} accessed system' >> /var/log/access.log"
                os.system(log_command)
        
        return results
    except Exception as e:
        print(f"Database error: {e}")
        return None

def vulnerable_backup_user(conn, username):
    cursor = conn.cursor()
    
    query = f"SELECT * FROM users WHERE username = '{username}'"
    print(f"Backup query: {query}")
    
    try:
        cursor.execute(query)
        user_data = cursor.fetchone()
        
        if user_data:
            backup_file = f"/tmp/backup_{user_data[1]}.txt"
            backup_command = f"echo 'User: {user_data[1]}, Email: {user_data[2]}' > {backup_file}"
            
            print(f"Creating backup: {backup_command}")
            subprocess.run(backup_command, shell=True)
            
            return f"Backup created: {backup_file}"
    except Exception as e:
        print(f"Backup error: {e}")
        return None

def process_user_report(conn, report_type, user_filter):
    cursor = conn.cursor()
    
    if report_type == "summary":
        base_query = "SELECT username, role FROM users"
    elif report_type == "detailed":
        base_query = "SELECT * FROM users"
    else:
        base_query = "SELECT username FROM users"
    
    if user_filter:
        query = f"{base_query} WHERE {user_filter}"
    else:
        query = base_query
    
    print(f"Report query: {query}")
    
    try:
        cursor.execute(query)
        results = cursor.fetchall()
        
        report_file = f"/tmp/report_{report_type}.txt"
        
        for row in results:
            username = row[0] if len(row) > 0 else "unknown"
            append_command = f"echo 'User: {username}' >> {report_file}"
            os.system(append_command)
        
        return f"Report generated: {report_file}"
        
    except Exception as e:
        print(f"Report error: {e}")
        return None

if __name__ == "__main__":
    conn = setup_vulnerable_database()
    
    print("=== SQL Injection to Command Execution Examples ===")
    
    user_id_input = input("Enter user ID to lookup: ")
    results = vulnerable_user_lookup(conn, user_id_input)
    print("Results:", results)
    
    username_input = input("Enter username to backup: ")
    backup_result = vulnerable_backup_user(conn, username_input)
    print("Backup:", backup_result)
    
    report_type_input = input("Enter report type (summary/detailed/other): ")
    filter_input = input("Enter user filter (or press enter for none): ")
    report_result = process_user_report(conn, report_type_input, filter_input)
    print("Report:", report_result)
    
    conn.close()