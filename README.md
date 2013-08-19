guesthouse
==========

guesthouse use neo4j

서버에서 GIT 소스 댕겨 오기
env GIT_SSL_NO_VERIFY=true git pull https://github.com/KimJejun/guesthouse.git

메이븐 빌드 하기
mvn clean compile war:exploded -P real

war:exploded 하면 clean compile 해주는걸로 아는데 이상하게 안되네...
