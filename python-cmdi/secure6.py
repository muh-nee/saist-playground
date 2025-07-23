#!/usr/bin/env python3

import subprocess
import re
from flask import Flask, request, jsonify

app = Flask(__name__)

# Allowlist of safe commands
ALLOWED_COMMANDS = {
    'ping': {'cmd': ['ping', '-c', '3'], 'validator': r'^[a-zA-Z0-9.-]+$'},
    'date': {'cmd': ['date'], 'validator': None},
    'whoami': {'cmd': ['whoami'], 'validator': None},
    'uptime': {'cmd': ['uptime'], 'validator': None}
}

def validate_and_execute(command_name, args=None):
    if command_name not in ALLOWED_COMMANDS:
        return {'error': 'Command not allowed'}, 403
    
    cmd_config = ALLOWED_COMMANDS[command_name]
    command = cmd_config['cmd'].copy()
    
    # Validate arguments if provided
    if args and cmd_config['validator']:
        if not re.match(cmd_config['validator'], args):
            return {'error': 'Invalid argument format'}, 400
        command.append(args)
    elif args:
        return {'error': 'This command does not accept arguments'}, 400
    
    try:
        result = subprocess.run(command, capture_output=True, text=True, timeout=10)
        return {
            'command': ' '.join(command),
            'stdout': result.stdout,
            'stderr': result.stderr,
            'returncode': result.returncode
        }, 200
    except subprocess.TimeoutExpired:
        return {'error': 'Command timeout'}, 408
    except Exception as e:
        return {'error': f'Execution failed: {str(e)}'}, 500

@app.route('/execute', methods=['POST'])
def execute_command():
    data = request.get_json()
    if not data:
        return jsonify({'error': 'No JSON data provided'}), 400
    
    command = data.get('command', '').strip()
    args = data.get('args', '').strip() if data.get('args') else None
    
    if not command:
        return jsonify({'error': 'No command provided'}), 400
    
    result, status_code = validate_and_execute(command, args)
    return jsonify(result), status_code

@app.route('/ping', methods=['GET'])
def ping_service():
    host = request.args.get('host', '').strip()
    if not host:
        return jsonify({'error': 'No host provided'}), 400
    
    result, status_code = validate_and_execute('ping', host)
    return jsonify(result), status_code

@app.route('/commands', methods=['GET'])
def list_commands():
    return jsonify({
        'allowed_commands': list(ALLOWED_COMMANDS.keys()),
        'usage': 'POST /execute with {"command": "ping", "args": "example.com"}'
    })

if __name__ == "__main__":
    app.run(debug=False, host='127.0.0.1', port=5000)