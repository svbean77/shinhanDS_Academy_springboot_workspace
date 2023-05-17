/* 즉시 실행 함수로 사용: (function(){ return {}; })(); -> key, method를 가진 JS object를 리턴 */
/* 사용법: replyManager.getAll() */
/* ResfFul 방식으로 요청하기 */

var replyManager = (function() {
	/* 특정 board의 댓글 가져오기 -> /replies/100 이런 요청이 주어질 것 */
	var getAll2 = function(obj, callback) {
		console.log("get All.....");
		$.getJSON("/app/replies/" + obj, callback); /* getJSON: 결과가 무조건 json인 ajax 호출 -> 요청: 앞, 요청 후 실행: callback */
	}
	
	/* 특정 board에 댓글 추가하기 -> url로 bno를 주고 실제 데이터로도 전송: obj = {"bno":1, "title":"제목", "writer":"user"} 이런 형태일 것! */
	var add2 = function(obj, callback) {
		console.log("add.....");
		$.ajax({
			type: "post",
			url: "/myapp/replies/" + obj.bno,
			data: JSON.stringify(obj),
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};
	
	/* 특정 댓글 수정하기 */
	var update2 = function(obj, callback) {
		$.ajax({
			type: "put",
			url: "/myapp/replies/" + obj.bno,
			data: JSON.stringify(obj),
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};

	/* 특정 board의 특정 댓글 지우기 -> bno, rno 둘 다 존재해야 함 */
	var remove2 = function() {
		$.ajax({
			type: "delete",
			url: "/myapp/replies/" + obj.bno + "/" + obj.rno,
			dataType: "json",
			contentType: "application/json",
			success: callback
		});
	};

	return { getAll: getAll2, add: add2, update: update2, remove: remove2 }; /* getAll: function(){}, add: function(){}, ... */
})();
