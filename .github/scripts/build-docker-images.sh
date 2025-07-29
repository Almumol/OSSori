#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

IMAGES=""

append() {
  if [ -z "$IMAGES" ]; then
    IMAGES="$1"
  else
    IMAGES="$IMAGES,$1"
  fi
}

for module in "$@"
do
  echo "Processing $module"
  JAR_FILE=$(find artifacts/artifact-"$module" -name '*.jar' | head -n1)
  cp "$JAR_FILE" "$module".jar
  docker build -f ./Dockerfile --build-arg MODULE="$module" --platform linux/arm64 --no-cache -t ossori/ossori-$module:dev .
  docker push ossori/ossori-"$module":dev
  append ossori/ossori-"$module":dev
done

echo "images=$IMAGES" >> "$GITHUB_OUTPUT"
