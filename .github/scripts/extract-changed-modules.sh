#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

git fetch origin "$1":"$1"
CHANGED=$(git diff --name-only origin/"$1"...HEAD)

MODULES=""

append() {
  if [ -z "$MODULES" ]; then
    MODULES="$1"
  else
    MODULES="$MODULES,$1"
  fi
}

echo "$CHANGED" | grep -q '^module-api/' && append "module-api"
echo "$CHANGED" | grep -q '^module-batch/' && append "module-batch"
echo "$CHANGED" | grep -q '^module-core/module-domain/' && append "module-core:module-domain"
echo "$CHANGED" | grep -q '^module-core/module-client/' && append "module-core:module-client"
echo "$CHANGED" | grep -q '^module-core/module-infra/' && append "module-core:module-infra"

echo "변경된 모듈 목록: $MODULES"
echo "modules=$MODULES" >> "$GITHUB_OUTPUT"
