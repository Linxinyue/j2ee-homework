$(function () {
    // $("[data-mask]").inputmask();
    $("#check").click(function () {
        // alert(1);
        if ($("#id").val() != "") {
            $.post("admin/article_getModArticle",
                {
                    ids: $("#id").val()
                },
                function (data, status) {
                    if (status) {
                        $('#displayConent').html(data);
                    } else {
                        alert(status + ":" + data);
                    }
                });
        } else {
            alert("id 为空");
        }
    });
    $("#submit").click(function () {
        // alert(1);
        if ($('#title').val() != "" && $('#author').val() != ""
            && $('#date').val() != "" && $('#abstracts').val() != "" && $('#content').val() != "" ) {
            $.post("admin/article_update",
                {
                    ids: $("#id").val(),
                    title: $('#title').val(),
                    author: $('#author').val(),
                    date: $('#date').val(),
                    abstracts: $('#abstracts').val(),
                    classification: $('#classification').val(),
                    content: $('#content').val()
                },
                function (data, status) {
                    if (status) {
                        $('#tips').html("点击查询查看更新信息");
                        setInterval("$('#tips').text('')",5000);
                    } else {
                        alert(status + ":" + data);
                    }
                });
        } else {
            alert("所有内容均为必填项，请完全填写后在进行提交！");
        }
    });
});