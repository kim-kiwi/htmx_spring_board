# HTMX로 만든 게시판
htmx가 좋아보여서 한 번 써봤습니다.

mysql은 설정이 귀찮아서 sqlite를 썼습니다..

다소 코드가 더럽습니다. 언젠가 정리 한 번 해야하는데..

XSS 취약점이 존재하는데 재밌어서 취약점 수정 코드는 주석처리 해뒀습니다.

## Build
```sh
# gradlew bootRun도 가능
$ gradlew build
$ gradlew run
```