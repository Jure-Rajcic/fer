#!/usr/bin/env python3
import subprocess
import json
import sys
import re

def run_test_and_check():
    """Run test.sh and parse the response"""
    
    print("=" * 60)
    print("Running test...")
    print("=" * 60)
    
    try:
        result = subprocess.run(
            ['bash', 'test.sh'],
            capture_output=True,
            text=True,
            timeout=30
        )
        
        if result.returncode != 0:
            print(f"❌ Error running test.sh:")
            print(result.stderr)
            return False
        
        response_text = result.stdout.strip()
        
        if not response_text:
            print("❌ Empty response from server")
            return False
        
        try:
            response = json.loads(response_text)
        except json.JSONDecodeError as e:
            print(f"❌ Failed to parse JSON response:")
            print(response_text[:500])
            return False
        
        print("\n" + "=" * 60)
        print("RESULT")
        print("=" * 60)
        
        score_data = response.get('score', {})
        is_correct = score_data.get('is_correct', False)
        is_partial = score_data.get('is_partial', False)
        score = score_data.get('score', 0)
        score_perc = score_data.get('score_perc', 0)
        
        if is_correct:
            print("✅ SUCCESS! Code is CORRECT!")
            print(f"Score: {score} ({score_perc*100:.0f}%)")
        elif is_partial:
            print("⚠️  PARTIAL - Code is partially correct")
            print(f"Score: {score} ({score_perc*100:.0f}%)")
        else:
            print("❌ FAILED - Code is incorrect")
            print(f"Score: {score} ({score_perc*100:.0f}%)")
        
        coderesult_html = response.get('coderesult', '')
        
        if coderesult_html:
            print("\n" + "-" * 60)
            print("TEST DETAILS:")
            print("-" * 60)
            
            test_pattern = r'<span class="aii-answer-badge badge badge-primary border border-dark"><h5>(\d+)\.</h5></span><span class="ml-2 border border-dark badge badge-(success|danger)">\s*<h5>(Correct|Incorrect)</h5>'
            tests = re.findall(test_pattern, coderesult_html)
            
            for test_num, badge_type, status in tests:
                status_icon = "✅" if status == "Correct" else "❌"
                print(f"{status_icon} Test {test_num}: {status}")
            
            hint_pattern = r'<small>Hint: ([^<]+)</small>'
            hints = re.findall(hint_pattern, coderesult_html)
            
            if hints:
                print("\n" + "-" * 60)
                print("HINTS:")
                print("-" * 60)
                for hint in hints:
                    print(f"  • {hint}")
        
        print("\n" + "=" * 60)
        
        return is_correct
        
    except subprocess.TimeoutExpired:
        print("❌ Test timed out after 30 seconds")
        return False
    except Exception as e:
        print(f"❌ Unexpected error: {e}")
        import traceback
        traceback.print_exc()
        return False

if __name__ == "__main__":
    success = run_test_and_check()
    sys.exit(0 if success else 1)
