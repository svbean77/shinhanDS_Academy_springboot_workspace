/* 즉시 실행 함수로 사용: (function(){ return {}; })(); -> key, method를 가진 JS object를 리턴 */
/* 즉시 실행 함수를 여러 번 사용하기 위해 변수에 저장 + object 리턴 -> 변수를 통해  */
/* 사용법: replyManager.getAll() */
/* ResfFul 방식으로 요청하기 */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


var replyManager = (function() {
	/* day061: csrf 토큰을 항상 가지고 다니기 위해 ajax를 보내기 전 layout의 meta에 저장한 token 지정하기 (xhr에 header 설정) */
	function beforeSend(xhr) {
		xhr.setRequestHeader(header, token);
	}

	/* 특정 board의 댓글 가져오기 -> /replies/100, function(result){} 이런 요청이 주어질 것 */
	var getAll2 = function(obj, callback) {
		console.log("get All.....");
		$.getJSON("/app/replies/" + obj, callback); /* getJSON: 결과가 무조건 json인 ajax 호출 -> 요청: 앞, 요청 후 실행: callback */
		/* ajax 종류: $.get(url, callback), $.post(url, callback), $.getJSON(url, callback), $.ajax({}) -> callback은 성공 시 실행할 함수 */
	}

	/* 특정 board에 댓글 추가하기 -> url로 bno를 주고 실제 데이터로도 전송: obj = {"bno":1, "title":"제목", "writer":"user"} 이런 형태일 것! */
	var add2 = function(obj, callback) {
		console.log("add.....");
		$.ajax({
			beforeSend: beforeSend,
			type: "post",
			url: "/app/replies/" + obj.bno,
			data: JSON.stringify(obj),
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};

	/* 특정 댓글 수정하기 */
	var update2 = function(obj, callback) {
		$.ajax({
			beforeSend: beforeSend,
			type: "put",
			url: "/app/replies/" + obj.bno,
			data: JSON.stringify(obj),
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};

	/* 특정 board의 특정 댓글 지우기 -> bno, rno 둘 다 존재해야 함 */
	var remove2 = function(obj, callback) {
		$.ajax({
			beforeSend: beforeSend,
			type: "delete",
			url: "/app/replies/" + obj.bno + "/" + obj.rno,
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};

	return { getAll: getAll2, add: add2, update: update2, remove: remove2 }; /* getAll: function(){}, add: function(){}, ... */
})();


var externalFunc = (function() {
	return {
		myname: "홍길동",
		mycompany: "신한",
		work: function() { alert("외부함수"); }
	};
})();