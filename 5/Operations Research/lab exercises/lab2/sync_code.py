#!/usr/bin/env python3
import json
import re

def escape_code_for_json(code):
    """Escape code content for JSON string"""
    return json.dumps(code)[1:-1]

def sync_code_to_test():
    """Read code.py and update test.sh with the escaped code"""
    
    with open('code.py', 'r') as f:
        code_content = f.read()
    
    with open('test.sh', 'r') as f:
        test_sh_content = f.read()
    
    escaped_code = escape_code_for_json(code_content)
    
    pattern = r'(--data-raw \')(\{.*?\})(\')$'
    
    def replace_code(match):
        prefix = match.group(1)
        json_str = match.group(2)
        suffix = match.group(3)
        
        data = json.loads(json_str)
        data['code'] = code_content
        
        return prefix + json.dumps(data) + suffix
    
    updated_content = re.sub(pattern, replace_code, test_sh_content, flags=re.DOTALL)
    
    with open('test.sh', 'w') as f:
        f.write(updated_content)
    
    print("✓ Synced code.py to test.sh")

if __name__ == "__main__":
    sync_code_to_test()
