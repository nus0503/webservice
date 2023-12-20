var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
           _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-modify').on('click', function () {
            _this.modify();
        });

        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });

        document.querySelectorAll('#btn-comment-update').forEach(function (item) {
            item.addEventListener('click', function () {
                const form = this.closest('form');
                _this.commentUpdate(form);
            })
        })
    },

    save : function () {
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf=8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    modify : function () {
        const data = {
            id: $('#id').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            modifiedDate: $('#modifiedDate').val()
        }
        if(!data.password || data.password.trim() === "") {
                    alert("공백 또는 입력하지 않은 부분이 있습니다.");
                    return false;
                } else if(!/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/.test(data.password)) {
                    alert("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                    $('#password').focus();
                    return false;
                }
        const con_check = confirm("수정하시겠습니까?");
                if (con_check === true) {
                    $.ajax({
                        type: "PUT",
                        url: "/user",
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(data)

                    }).done(function () {
                        alert("회원수정이 완료되었습니다.");
                        window.location.href = "/";

                    });
                    }
    },

    commentSave : function () {
            const data = {
                postsId: $('#postsId').val(),
                comment: $('#comment').val()
            }

            // 공백 및 빈 문자열 체크
            if (!data.comment || data.comment.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/posts/' + data.postsId + '/comments',
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert('댓글이 등록되었습니다.');
                    window.location.reload();
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        },

    commentUpdate : function (form) {
            const data = {
                id: form.querySelector('#id').value,
                postsId: form.querySelector('#postsId').value,
                comment: form.querySelector('#comment-content').value,
                writerUserId: form.querySelector('#writerUserId').value,
                sessionUserId: form.querySelector('#sessionUserId').value,
            }
            console.log("commentWriterID : " + data.writerUserId);
            console.log("sessionUserID : " + data.sessionUserId);

            if (data.writerUserId !== data.sessionUserId) {
                alert("본인이 작성한 댓글만 수정 가능합니다.");
                return false;
            }

            if (!data.comment || data.comment.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            }
            const con_check = confirm("수정하시겠습니까?");
            if (con_check === true) {
                $.ajax({
                    type: 'PUT',
                    url: '/posts/' + data.postsId + '/comments/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    window.location.reload();
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        },
        /** 댓글 삭제 */
        commentDelete : function (postsId, commentId, commentWriterId, sessionUserId) {

        if (commentWriterId !== sessionUserId) {
            alert("본인이 작성한 댓글만 삭제 가능합니다.");
        } else {
            const con_check = confirm("삭제하시겠습니까?");
                        if (con_check === true) {
                            $.ajax({
                                type: 'DELETE',
                                url: '/posts/' + postsId + '/comments/' + commentId,
                                dataType: 'JSON',
                            }).done(function () {
                                alert('댓글이 삭제되었습니다.');
                                window.location.reload();
                            }).fail(function (error) {
                                alert(JSON.stringify(error));
                            });
                        }
            }
        }
};

main.init();