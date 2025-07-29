#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

if ! command -v jq &> /dev/null; then
  sudo apt-get update && sudo apt-get install -y jq
fi

output=$(./gradlew findDependentModules -PchangedModules="$1")
echo "output=${output}"
candidates=$(echo "$output" | grep "candidates=" | cut -d'=' -f2)
executables=$(echo "$output" | grep "executables=" | cut -d'=' -f2)
echo "candidates=$candidates"
echjo "executables=$executables"
candidates_json=$(echo "$candidates" | tr ',' '\n' | jq -R . | jq -s .)
executables_json=$(echo "$executables" | tr ',' '\n' | jq -R . | jq -s .)
echo "candidates_json=${candidates_json}"
echo "executables_json=${executables_json}"
candidates_json=$(echo "$candidates" | tr ',' '\n' | jq -R 'gsub("^\\s+|\\s+$"; "")' | jq -s .)
executables_json=$(echo "$executables" | tr ',' '\n' | jq -R 'gsub("^\\s+|\\s+$"; "")' | jq -s .)
echo "candidates_json=${candidates_json}"
echo "executables_json=${executables_json}"

echo "candidates=\"${candidates_json}\"" >> "$GITHUB_OUTPUT"
echo "executables=\"${executables_json}\"" >> "$GITHUB_OUTPUT"
