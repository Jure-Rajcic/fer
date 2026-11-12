#!/bin/bash

echo "Syncing code.py to test.sh..."
python3 sync_code.py

if [ $? -ne 0 ]; then
    echo "Failed to sync code"
    exit 1
fi

echo ""
echo "Running test and checking result..."
python3 check_result.py
