#!/bin/bash

# Docker 초기화용


# ./run-ddl.sh

# MySQL 접속 정보
HOST=localhost
PORT=3306
USER=root
PASS='password'

SCRIPT_DIR="infra/rdb/ddl-script"

# 실행 시작
echo "📂 Running SQL scripts in $SCRIPT_DIR"

for file in $(ls "$SCRIPT_DIR"/*.sql | sort); do
  echo "▶️ Running: $file"
  mysql -h "$HOST" -P "$PORT" -u "$USER" -p"$PASS" < "$file"
done

echo "✅ All DDL scripts executed."