#!/bin/bash

BODY=$(curl -s http://localhost:80/dashboards)
FAILED_TESTS=0

LENGTH=$(echo "$BODY" | jq length)
if ! [[ $LENGTH -ge 0 ]]; then
  echo "it should return at least one dashboard: FAIL" 1>&2
  ((FAILED_TESTS++))
else
  echo "it should return at least one dashboard: PASS"
fi

ID=$(echo "$BODY" | jq '.[0] | .id')
if ! [[ $ID -ge 0 ]]; then
  echo "it should include a numeric id: FAIL" 1>&2
  ((FAILED_TESTS++))
else
  echo "it should include a numeric id: PASS"
fi

CREATED_AT=$(echo "$BODY" | jq '.[0] | .createdAt')
if [[ $CREATED_AT == null ]]; then
  echo "it should include a non empty createdAt: FAIL" 1>&2
  ((FAILED_TESTS++))
else
  echo "it should include a non empty createdAt: PASS"
fi

UPDATED_AT=$(echo "$BODY" | jq '.[0] | .updatedAt')
if [[ $UPDATED_AT == null ]]; then
  echo "it should include a non empty updatedAt: FAIL" 1>&2
  ((FAILED_TESTS++))
else
  echo "it should include a non empty updatedAt: PASS"
fi

TITLE=$(echo "$BODY" | jq '.[0] | .title')
if [[ $TITLE == null ]]; then
  echo "it should include a non empty title: FAIL" 1>&2
  ((FAILED_TESTS++))
else
  echo "it should include a non empty title: PASS"
fi

if [[ $FAILED_TESTS -ne 0 ]]; then
  echo
  echo $FAILED_TESTS SMOKE TESTS FAILED
  echo
  echo The api returned the following response
  echo "$BODY" | jq
  echo
  exit 1
fi
