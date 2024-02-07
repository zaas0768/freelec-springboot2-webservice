var main = {
    init: function () {
        // var보다 let사용 권장
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        // btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때 update function을 실행하도록 이벤트 등록
        $('#btn-update').on('click', function () {
           _this.update();
        });
    },
    save: function () {
        let data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        // id는 따로 분리 아이디를 통해 해당 게시글을 검색하기 위해
        let id = $('#id').val();

        $.ajax({
            type: 'PUT',    // 수정 API는 @PutMapping 으로 선언하였기 때문에 PUT 사용, REST 규약에 맞게 설정
            url: '/api/v1/posts/' + id, // id를 url과 전송, 어떤 게시글을 수정할지 URL Path로 구분하기 위해 id 추가
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};
main.init();