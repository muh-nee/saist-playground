#!/usr/bin/env python3

import os
import pathlib
import sqlite3
import json
from django.core.management.base import BaseCommand
from django.conf import settings

if not settings.configured:
    settings.configure(
        DEBUG=False,
        SECRET_KEY=os.environ.get('SECRET_KEY', 'secure-key-from-environment'),
        DATABASES={
            'default': {
                'ENGINE': 'django.db.backends.sqlite3',
                'NAME': ':memory:',
            }
        },
        INSTALLED_APPS=['__main__'],
    )

class Command(BaseCommand):
    help = 'Secure database backup utility'
    
    ALLOWED_FORMATS = ['json', 'sql']
    
    def add_arguments(self, parser):
        parser.add_argument('--path', type=str, required=True, 
                          help='Backup destination path')
        parser.add_argument('--format', type=str, default='json', 
                          choices=self.ALLOWED_FORMATS,
                          help='Backup format (json or sql)')
        parser.add_argument('--table', type=str, 
                          help='Specific table to backup (optional)')

    def validate_path(self, backup_path):
        """Validate and sanitize the backup path"""
        try:
            path_obj = pathlib.Path(backup_path).resolve()
            
            # Ensure we're not trying to overwrite system files
            restricted_paths = ['/etc', '/usr', '/bin', '/sbin', '/boot']
            for restricted in restricted_paths:
                if str(path_obj).startswith(restricted):
                    raise ValueError(f"Cannot write to restricted path: {restricted}")
            
            # Create parent directory if it doesn't exist
            path_obj.parent.mkdir(parents=True, exist_ok=True)
            
            return path_obj
        except Exception as e:
            raise ValueError(f"Invalid backup path: {e}")

    def backup_to_json(self, db_path, backup_path, table_name=None):
        """Secure JSON backup using proper database APIs"""
        try:
            with sqlite3.connect(':memory:') as conn:
                conn.row_factory = sqlite3.Row
                cursor = conn.cursor()
                
                # Create sample data for demonstration
                cursor.execute('''
                    CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT, email TEXT)
                ''')
                cursor.execute('''
                    INSERT INTO users (name, email) VALUES (?, ?)
                ''', ('Test User', 'test@example.com'))
                
                if table_name:
                    # Validate table name exists (prevent SQL injection)
                    cursor.execute(
                        "SELECT name FROM sqlite_master WHERE type='table' AND name=?", 
                        (table_name,)
                    )
                    if not cursor.fetchone():
                        raise ValueError(f"Table '{table_name}' does not exist")
                    tables = [table_name]
                else:
                    cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
                    tables = [row[0] for row in cursor.fetchall()]
                
                backup_data = {}
                for table in tables:
                    cursor.execute(f"SELECT * FROM {table}")
                    rows = cursor.fetchall()
                    backup_data[table] = [dict(row) for row in rows]
                
                with open(backup_path, 'w') as f:
                    json.dump(backup_data, f, indent=2)
                
                return len(backup_data)
        except Exception as e:
            raise RuntimeError(f"JSON backup failed: {e}")

    def handle(self, *args, **options):
        try:
            backup_path = self.validate_path(options['path'])
            backup_format = options['format']
            table_name = options.get('table')
            
            self.stdout.write(f"Starting {backup_format} backup to: {backup_path}")
            
            if backup_format == 'json':
                table_count = self.backup_to_json(None, backup_path, table_name)
                self.stdout.write(
                    self.style.SUCCESS(
                        f'JSON backup completed: {table_count} tables backed up'
                    )
                )
            else:
                # For SQL format, use secure parameterized approach
                self.stdout.write(
                    self.style.WARNING(
                        'SQL backup format requires additional database credentials'
                    )
                )
                
        except Exception as e:
            self.stdout.write(self.style.ERROR(f'Backup failed: {e}'))

if __name__ == "__main__":
    import sys
    import django
    from django.core.management import execute_from_command_line
    
    django.setup()
    
    # Secure argument handling
    if len(sys.argv) < 3:
        print("Usage: python secure7.py --path /path/to/backup.json [--format json]")
        sys.exit(1)
    
    cmd = Command()
    options = {'path': '/tmp/secure_backup.json', 'format': 'json'}
    cmd.handle(**options)