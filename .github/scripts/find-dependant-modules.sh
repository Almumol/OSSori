#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

if ! command -v jq &> /dev/null; then
  sudo apt-get update && sudo apt-get install -y jq
fi

output=$(./gradlew findDependentModules -PchangedModules="$1")
candidates=$(echo "$output" | grep "candidates=" | cut -d'=' -f2)
executables=$(echo "$output" | grep "executables=" | cut -d'=' -f2)
candidates_json=$(echo "$candidates" | tr ',' '\n' | jq -R . | jq -s .)
executables_json=$(echo "$executables" | tr ',' '\n' | jq -R . | jq -s .)

echo "candidates=$candidates_json" >> "$GITHUB_OUTPUT"
echo "executables=$executables_json" >> "$GITHUB_OUTPUT"
