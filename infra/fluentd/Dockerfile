# 로그 수집 & Elasticsearch 전송용이므로 따로 Dockerfile 작성
FROM fluent/fluentd:v1.16-1

USER root

# faraday 1.x 설치 (ES7과 호환)
# @todo 2025/11/6 버전 호환성 맞추기가 너무 힘들었다. 나중에 여유 있을 때 다시 정리할 것
RUN apk add --no-cache --update --virtual .build-deps build-base ruby-dev && \
    gem uninstall -aIx faraday && \
    gem install faraday -v "1.10.3" --no-document && \
    gem install elasticsearch -v "7.17.8" --no-document && \
    gem install elasticsearch-api -v "7.17.8" --no-document && \
    gem install elasticsearch-transport -v "7.17.8" --no-document && \
    gem install fluent-plugin-elasticsearch -v "4.3.3" --no-document && \
    apk del .build-deps

USER fluent
