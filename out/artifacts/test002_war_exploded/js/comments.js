$(function() {
    $(".getComments").click(function() {
        $.post("page_comment",
            {
                page:$(this).text()
            },
            function (data, status) {
                if(status)
                    $("#showComments").html(data);
                else
                    alert("数据：" + data + "\n状态：" + status);
            });
    });
    $(".pleaseLogin").click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });

    $(".commentzan").click(function () {
        if ($(this).attr("title") == "zan") {
            $.post("like_comment",
                {
                    comment_id: $(this).parent().attr("id").substr(7)
                },
                function (data, status) {
                    // alert(status);
                });
            $(this).parent().html("<img title='yizan' class='commentzan' src='img/iconfont-zanzan2.png'>"+(new Number($(this).parent().text()) + 1));
        } else {
            alert("yizan");
        }

    });
});