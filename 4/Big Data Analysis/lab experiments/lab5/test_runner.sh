#!/bin/bash

# A script to test the NodeRank.py solution against multiple test cases.

# --- Configuration ---
# PYTHON_SCRIPT="NodeRank.py"
# TESTS_DIR="lab5A_primjeri"

PYTHON_SCRIPT="ClosestBlackNode.py"
TESTS_DIR="lab5B_primjeri"

# --- Colors and Emojis for better output ---
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color
PASS_ICON="✅"
FAIL_ICON="❌"

# --- Pre-run Checks ---
if [ ! -f "$PYTHON_SCRIPT" ]; then
    echo -e "${RED}Error: Python script '$PYTHON_SCRIPT' not found.${NC}"
    exit 1
fi
if [ ! -d "$TESTS_DIR" ]; then
    echo -e "${RED}Error: Test directory '$TESTS_DIR' not found.${NC}"
    exit 1
fi

# --- Core Test Function ---
# Arguments: $1: test_name, $2: mode ('summary' or 'verbose')
run_test() {
    local test_name=$1
    local mode=$2
    local test_dir="${TESTS_DIR}/${test_name}"
    local input_file="${test_dir}/R.in"
    local expected_output_file="${test_dir}/R.out"
    local actual_output_file="actual_output.tmp"

    # Check if test case files exist
    if [ ! -f "$input_file" ] || [ ! -f "$expected_output_file" ]; then
        echo -e "${YELLOW}Warning: Test case '$test_name' is missing R.in or R.out file. Skipping.${NC}"
        return 2 # Special return code for skipped test
    fi

    # Run the python script and capture its output
    python3 "$PYTHON_SCRIPT" < "$input_file" > "$actual_output_file"

    # Compare the actual output with the expected output
    # The `diff` command returns 0 if files are identical, non-zero otherwise.
    if diff -q --strip-trailing-cr "$actual_output_file" "$expected_output_file" >/dev/null 2>&1; then
        # Test PASSED
        if [ "$mode" == "summary" ]; then
            echo -e "${GREEN}PASSED${NC}"
        else
            echo -e "${GREEN}${PASS_ICON} Test '$test_name' PASSED${NC}"
        fi
        rm "$actual_output_file"
        return 0
    else
        # Test FAILED
        if [ "$mode" == "summary" ]; then
            echo -e "${RED}FAILED${NC}"
        else
            echo -e "${RED}${FAIL_ICON} Test '$test_name' FAILED${NC}"
            echo "--------------------- Differences ---------------------"
            # Use diff with --color for a nice, readable output of the differences
            diff -u --color=always "$expected_output_file" "$actual_output_file"
            echo "-----------------------------------------------------"
        fi
        rm "$actual_output_file"
        return 1
    fi
}

# --- Main Logic ---

# If no arguments are provided, run all tests in summary mode.
if [ "$#" -eq 0 ]; then
    echo "▶️  Running all tests..."
    failed_tests=()
    test_count=0

    # Loop through all subdirectories in the tests directory
    for test_path in "${TESTS_DIR}"/*; do
        if [ -d "$test_path" ]; then
            test_name=$(basename "$test_path")
            ((test_count++))
            echo -n "  - Testing '$test_name' ... "
            run_test "$test_name" "summary"
            if [ $? -eq 1 ]; then
                failed_tests+=("$test_name")
            fi
        fi
    done

    # Print final summary
    echo -e "\n---------------------- SUMMARY ----------------------"
    if [ ${#failed_tests[@]} -eq 0 ]; then
        echo -e "${GREEN}${PASS_ICON} All $test_count tests passed successfully! Great job!${NC}"
    else
        failed_count=${#failed_tests[@]}
        passed_count=$((test_count - failed_count))
        echo -e "${YELLOW}Completed: ${passed_count} passed, ${failed_count} failed.${NC}"
        echo -e "${RED}${FAIL_ICON} The following ${failed_count} test(s) failed:${NC}"
        for failed in "${failed_tests[@]}"; do
            echo "   - $failed"
        done
        echo -e "\n${YELLOW}To see details, run the script with a failed test name, for example:${NC}"
        echo "   ./test_runner.sh ${failed_tests[0]}"
    fi
    echo "-----------------------------------------------------"

# If arguments are provided, run only those specific tests in verbose mode.
else
    echo "▶️  Running specific test(s) in verbose mode..."
    for test_name in "$@"; do
        if [ ! -d "${TESTS_DIR}/${test_name}" ]; then
            echo -e "\n${YELLOW}⚠️  Test folder '$test_name' not found in '${TESTS_DIR}'.${NC}"
        else
            echo ""
            run_test "$test_name" "verbose"
        fi
    done
fi



# 1. select by uncommenting required variables
# # --- Configuration ---
# # PYTHON_SCRIPT="NodeRank.py"
# # TESTS_DIR="lab5A_primjeri"

# PYTHON_SCRIPT="ClosestBlackNode.py"
# TESTS_DIR="lab5B_primjeri"

# 2. run command
# ./test_runner.sh