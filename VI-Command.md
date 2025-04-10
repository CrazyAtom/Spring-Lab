# VI/VIM 편집기 주요 명령어

## 1. 기본 모드 전환
| 명령어 | 설명 |
|--------|------|
| `i` | 현재 커서 위치에서 입력 모드 시작 |
| `a` | 현재 커서 다음 위치에서 입력 모드 시작 |
| `o` | 현재 커서 다음 줄에서 입력 모드 시작 |
| `Esc` | 입력 모드에서 명령 모드로 전환 |

## 2. 이동 명령어
| 명령어 | 설명 |
|--------|------|
| `h` | 왼쪽으로 이동 |
| `j` | 아래로 이동 |
| `k` | 위로 이동 |
| `l` | 오른쪽으로 이동 |
| `w` | 다음 단어의 시작으로 이동 |
| `b` | 이전 단어의 시작으로 이동 |
| `^` | 현재 줄의 처음으로 이동 |
| `$` | 현재 줄의 끝으로 이동 |
| `gg` | 문서의 처음으로 이동 |
| `G` | 문서의 끝으로 이동 |

## 3. 편집 명령어
| 명령어 | 설명 |
|--------|------|
| `x` | 현재 커서 위치의 문자 삭제 |
| `dd` | 현재 줄 삭제 |
| `yy` | 현재 줄 복사 |
| `p` | 현재 커서 다음에 붙여넣기 |
| `u` | 실행 취소 (undo) |
| `Ctrl + r` | 다시 실행 (redo) |

## 4. 검색 및 치환
| 명령어 | 설명 |
|--------|------|
| `/단어` | 앞방향 검색 |
| `?단어` | 뒷방향 검색 |
| `n` | 다음 검색 결과로 이동 |
| `N` | 이전 검색 결과로 이동 |
| `:%s/old/new/g` | 문서 전체에서 old를 new로 치환 |

## 5. 저장 및 종료
| 명령어 | 설명 |
|--------|------|
| `:w` | 저장 |
| `:q` | 종료 |
| `:wq` | 저장 후 종료 |
| `:q!` | 강제 종료 (저장하지 않음) |
| `ZZ` | 저장 후 종료 |

## 6. 화면 제어
| 명령어 | 설명 |
|--------|------|
| `Ctrl + f` | 다음 화면으로 이동 |
| `Ctrl + b` | 이전 화면으로 이동 |
| `Ctrl + u` | 반 화면 위로 이동 |
| `Ctrl + d` | 반 화면 아래로 이동 |
