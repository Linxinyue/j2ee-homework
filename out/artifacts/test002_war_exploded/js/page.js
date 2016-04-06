$(function () {
    $(".subscribe").click(function () {
        if ($(this).text() == "已订阅") {
            $.post("page_unSub",
                {
                    classId: $(this).attr("id")
                },
                function (data, status) {
                    // alert(status);
                });
            $(this).text("+　订阅");
        } else {
            $.post("page_sub",
                {
                    classId: $(this).attr("id")
                },
                function (data, status) {
                    // alert(status);
                });
            $(this).text("已订阅");
        }
    });
    $('.pleaselogin').click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });
    $('.sendComment').click(function () {
        if ($(".commentsText").val() != "") {
            $.post("page_putComment",
                {
                    commentConent: $(".commentsText").val()
                },
                function (data, status) {
                    // alert(status);
                    if(status){
                        $(".commentsText").val("");
                        $('.commentTip').text('评论成功，审核后显示！！');
                        setInterval("$('.commentTip').text('')",3000);
                    }
                });
        } else {
            alert("评论不能为空！");
        }
    });
    $('.sendCommentLogin').click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });
});
function haveText() {
    if ($(".commentsText").val() != "") {
        $(".sendComment").css("background-color", "#FF9B02");
    } else {
        $(".sendComment").css("background-color", "#CDCDCD");
    }
}