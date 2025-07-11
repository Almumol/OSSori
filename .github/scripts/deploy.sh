#!/bin/bash

APP_NAME="your-app"  # app 이름 (선택)
JAR_PATH="/home/ec2-user/app"  # JAR이 저장된 디렉토리
LOG_PATH="/home/ec2-user/app/app.log"

# 1. 기존 실행 중인 애플리케이션 종료
echo "🔴 기존 애플리케이션 종료 중..."
PID=$(pgrep -f "$JAR_PATH/.*\.jar")
if [ -n "$PID" ]; then
  kill $PID
  echo "✅ 종료 완료 (PID: $PID)"
else
  echo "ℹ️ 종료할 프로세스 없음"
fi

# 2. JAR 파일 찾기
echo "📦 최신 JAR 파일 검색..."
JAR_FILE=$(ls -t $JAR_PATH/*.jar | head -n 1)

if [ -z "$JAR_FILE" ]; then
  echo "❌ JAR 파일을 찾을 수 없습니다!"
  exit 1
fi

echo "🚀 애플리케이션 시작 중: $JAR_FILE"
nohup java -jar "$JAR_FILE" > "$LOG_PATH" 2>&1 &

echo "✅ 배포 완료"

########

#!/bin/bash

BUILD_PATH=$(ls /home/ubuntu/build/*.jar)
APPLICATION_JAR_NAME=$(basename $BUILD_PATH)

echo "> build 파일명: $APPLICATION_JAR_NAME"

if [[ "$DEPLOYMENT_GROUP_NAME" == "2024-corea-backend-develop-group" ]]; then
  SPRING_PROFILE="dev"
elif [[ "$DEPLOYMENT_GROUP_NAME" == "2024-corea-backend-lb-group" ]]; then
  SPRING_PROFILE="prod"
else
  SPRING_PROFILE="local"
fi

echo "> $APPLICATION_JAR_NAME 배포 중 - 프로파일: $SPRING_PROFILE"

echo "> 현재 실행중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $APPLICATION_JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $APPLICATION_JAR_NAME 배포"
APPLICATION_JAR=/home/ubuntu/build/$APPLICATION_JAR_NAME

nohup java -Duser.timezone=Asia/Seoul \
    -Dfile.encoding=UTF-8 \
    -Xms1G \
    -Xmx1G \
    -jar -Dspring.profiles.active=$SPRING_PROFILE $APPLICATION_JAR > /dev/null 2> /dev/null < /dev/null &
