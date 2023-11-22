# ML 서버
- `bin/`에는 일회용 스크립트
- `data/`에는 데이터셋
- `src/`에는 핵심 함수들

실행은 `grpc_server.py`를 이용해서 할 수 있다. 환경변수로 `PORT`를 지정해주면 된다. 기본값은 50051이다.

```bash
PORT=50051 python grpc_server.py
```
