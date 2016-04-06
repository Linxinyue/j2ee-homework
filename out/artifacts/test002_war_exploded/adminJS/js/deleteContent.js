$(function () {
    $(".getArticles").click(function() {
        // alert("2423423");
        $.post("admin/article_getArticle",
            {
                page:$(this).text()
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });

    $(".getComments").click(function() {
        // alert("2423423");
        $.post("admin/comment_getComment",
            {
                page:$(this).text()
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".getUsers").click(function() {
        // alert("2423423");
        $.post("admin/user_getUsers",
            {
                page:$(this).text()
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".last").click(function() {
        // alert("2423423");
        $.post("admin/article_getArticle",
            {
                page:$(this).attr("id").substr(4)
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".commentLast").click(function() {
        // alert("2423423");
        $.post("admin/comment_getComment",
            {
                page:$(this).attr("id").substr(4)
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".userLast").click(function() {
        // alert("2423423");
        $.post("admin/user_getUsers",
            {
                page:$(this).attr("id").substr(4)
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".first").click(function() {
        // alert("2423423");
        $.post("admin/article_getArticle",
            {
                page:1
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".commentfirst").click(function() {
        // alert("2423423");
        $.post("admin/comment_getComment",
            {
                page:1
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".userFirst").click(function() {
        // alert("2423423");
        $.post("admin/user_getUsers",
            {
                page:1
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".delete").click(function() {
        var ids = $(this).parent().parent().attr("id");
        // alert(ids.substr(7));
        $.post("admin/article_delete",
            {
                ids: ids.substr(7)
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".commentDelete").click(function() {
        var ids = $(this).parent().parent().attr("id");
        $.post("admin/comment_delete",
            {
                ids: ids.substr(7)
            },
            function (data, status) {
                if(status)
                    $("#contents").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
});

