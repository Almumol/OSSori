#!/bin/bash

: "${GITHUB_OUTPUT:=/dev/null}"

if [ "$TYPE" == "PR" ]; then
  template_id=123755
elif [ "$TYPE" == "ISSUE" ]; then
  template_id=123756
elif [ "$TYPE" == "COMMENT" ]; then
  template_id=123757
else
  echo "Not proper message type"
  exit 0
fi

response=$(curl -s -X POST "https://kauth.kakao.com/oauth/token" \
    -H "Content-Type: application/x-www-form-urlencoded;charset=utf-8" \
    -d "grant_type=authorization_code" \
    -d "client_id=$CLIENT_ID" \
    --data-urlencode "redirect_uri=$REDIRECT_URI" \
    -d "code=$CODE")

access_token=$(echo "$response" | jq -r '.access_token')

echo "template id: $template_id for $TYPE type"

template_args=$(jq -nc --arg t "$TITLE" --arg b "$BODY" --arg l "$LINK" \
    '{PR_TITLE:$t, PR_BODY:$b, REGI_WEB_DOMAIN:$l}')

curl -v -X POST "https://kapi.kakao.com/v1/api/talk/friends/message/send" \
    -H "Content-Type: application/x-www-form-urlencoded;charset=utf-8" \
    -H "Authorization: Bearer $access_token" \
    --data-urlencode "template_id=$template_id" \
    --data-urlencode "template_args=$template_args" \
    --data-urlencode "receiver_uuids=$UUIDS"
