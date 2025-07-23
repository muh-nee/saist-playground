#!/usr/bin/env python3

import subprocess
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/execute', methods=['POST'])
def execute_command():
    user_command = request.json.get('command', '')
    
    if not user_command:
        return jsonify({'error': 'No command provided'}), 400
    
    try:
        result = subprocess.run(user_command, shell=True, capture_output=True, text=True, timeout=10)
        return jsonify({
            'stdout': result.stdout,
            'stderr': result.stderr,
            'returncode': result.returncode
        })
    except subprocess.TimeoutExpired:
        return jsonify({'error': 'Command timeout'}), 408
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/ping', methods=['GET'])
def ping_service():
    host = request.args.get('host', 'localhost')
    command = f"ping -c 3 {host}"
    
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    return jsonify({
        'command': command,
        'output': result.stdout
    })

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)