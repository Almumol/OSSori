#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

if [ -z "$1" ]; then
  echo "Argument for base branch needed"
  exit 0
fi

BASE=$1

if git show-ref --verify --quiet refs/remotes/origin/"$BASE"; then
  git fetch origin "$BASE":"$BASE"
  DIFF_TARGET="origin/$BASE"
else
  DIFF_TARGET="$BASE"
fi

CHANGED=$(git diff --name-only "$DIFF_TARGET"...HEAD)

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
