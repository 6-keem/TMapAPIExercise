TMap SDK에서 기본으로 제공하는 setOnMapReadyListener 사용하여 api 인증 후 지도가 로드되도록 설계하였으나 네트워크 문제로

인증에 실패한채로 접근하는 경우가 있어 Coroutine으로 명시적인 대기시간을 걸어주었다

<br>

상단 확대 버튼과 축소 버튼을 누르면 TMapView의 zoomIn, zoomOut 함수를 사용하여 줌인아웃을 구현하였다.

<br>

상단 출발지, 목적지에 원하는 장소를 입력 후 검색하기 버튼을 누르면 해당 값들을 ApiRequestHandler의 requestAPIs 함수로 넘기고

requestFullTextGeocodeingAPI의 함수를 출발지 목적지에 대해 각각 호출하여 출발지의 좌표, 목적지의 좌표를 얻는다. _(이때 Reqeust method는 GET이며 Query를 통해 요청한다.)_

이때 문자열도 함께 넘겨주어 Callback함수에서 Response 도착 시 변수 간 충돌을 막았고 flag 값을 true로 변경하였다.

<br>


앞선 두 API Request가 끝나면 Coroutine을 사용하여 스레드를 하나 생성하고 만약 두 주소의 좌표를 얻었다면(flag로 확인)

requestTransmitRouteAPI()를 통해 두 좌표 간 대중교통 경로를 찾는다. 이때 다음 탐색을 위해 flag는 다시 false로 세팅하여 준다.

<br>

requestTransmitRouteAPI 함수에서는 request를 요청하며 이때의 Reqeust method는 Post이다.

해당 Request를 통해 response가 정상적으로 도착하면 Callback 함수를 통해 그 전에 있던 경로 리스트의 값을 모두 제거해 주고

Response를 파싱하여 새로 찾은 경로를 넣어준다. 해당 작업이 끝나면 mainActivity의 drawOnMap 함수를 호출하여 지도에 경로를 그려준다.
