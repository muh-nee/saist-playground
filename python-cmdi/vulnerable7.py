#!/usr/bin/env python3

import os
import subprocess
from django.core.management.base import BaseCommand
from django.conf import settings

if not settings.configured:
    settings.configure(
        DEBUG=True,
        SECRET_KEY='demo-key-not-for-production',
        DATABASES={
            'default': {
                'ENGINE': 'django.db.backends.sqlite3',
                'NAME': ':memory:',
            }
        },
        INSTALLED_APPS=['__main__'],
    )

class Command(BaseCommand):
    help = 'Backup database to specified path'

    def add_arguments(self, parser):
        parser.add_argument('--path', type=str, help='Backup destination path')
        parser.add_argument('--format', type=str, default='sql', help='Backup format')

    def handle(self, *args, **options):
        backup_path = options.get('path', '/tmp/backup.sql')
        backup_format = options.get('format', 'sql')
        
        if backup_format == 'sql':
            command = f"mysqldump -u root database_name > {backup_path}"
        else:
            command = f"pg_dump database_name -f {backup_path}"
        
        self.stdout.write(f"Executing: {command}")
        
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        
        if result.returncode == 0:
            self.stdout.write(self.style.SUCCESS(f'Backup completed: {backup_path}'))
        else:
            self.stdout.write(self.style.ERROR(f'Backup failed: {result.stderr}'))

if __name__ == "__main__":
    import sys
    import django
    from django.core.management import execute_from_command_line
    
    django.setup()
    
    sys.argv = ['vulnerable7.py', 'backup', '--path', '/tmp/backup.sql']
    cmd = Command()
    cmd.handle(path='/tmp/backup.sql', format='sql')