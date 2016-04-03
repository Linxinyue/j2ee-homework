$(function () {

    $(".title").click(function () {
        location.href = "page_indexId?id=" + $(this).parent().attr('id');
    });
    $(".tiltePicDiv").click(function () {
        location.href = "page_indexId?id=" + $(this).parent().attr('id');
    });

    $(".newstLi").click(function () {
        location.href = "page_indexId?id=" + $(this).attr('id').substr(4);
    });

    $(".comentArticleTitle").click(function () {
        location.href = "page_indexId?id=" + $(this).attr('id').substr(4);
    });

    $(".singleEntry").click(function () {
        switch ($(this).text()) {
            case "推荐":
                window.location = "news_index";
                break;
            case "热点":
                window.location = "news_remen";
                break;
            case "社会":
                window.location = "news_shehui";
                break;
            case "娱乐":
                window.location = "news_yule";
                break;
            case "科技":
                window.location = "news_keji";
                break;
            case "财经":
                window.location = "news_caijing";
                break;
            case "军事":
                window.location = "news_junshi";
                break;
            case "国际":
                window.location = "news_guoji";
                break;
            case "时尚":
                window.location = "news_shishang";
                break;
            case "探索":
                window.location = "news_tansuo";
                break;
            case "美文":
                window.location = "news_meiwen";
                break;
            case "历史":
                window.location = "news_lishi";
                break;
            case "故事":
                window.location = "news_gushi";
                break;
            case "游戏":
                window.location = "news_youxi";
                break;
            case "体育":
                window.location = "news_tiyu";
                break;
        }
    });
    $(".otherEntry").click(function () {
        switch ($(this).text()) {
            case "推荐":
                window.location = "index";
                break;
            case "热点":
                window.location = "news_remen";
                break;
            case "社会":
                window.location = "news_shehui";
                break;
            case "娱乐":
                window.location = "news_yule";
                break;
            case "科技":
                window.location = "news_keji";
                break;
            case "财经":
                window.location = "news_caijing";
                break;
            case "军事":
                window.location = "news_junshi";
                break;
            case "国际":
                window.location = "news_guoji";
                break;
            case "时尚":
                window.location = "news_shishang";
                break;
            case "探索":
                window.location = "news_tansuo";
                break;
            case "美文":
                window.location = "news_meiwen";
                break;
            case "历史":
                window.location = "news_lishi";
                break;
            case "故事":
                window.location = "news_gushi";
                break;
            case "游戏":
                window.location = "news_youxi";
                break;
            case "体育":
                window.location = "news_tiyu";
                break;
        }
    });

    // 修改天气位置
    $(".weatherCity").click(function () {
        if (!$("#checkCity").is(':checked')) {
            $(".choosCity").hide();
            $(".weatherDay").fadeIn();
        } else {
            $(".choosCity").fadeIn();
            $(".weatherDay").hide();
        }
    });
    $("#city0Choose").click(function () {
        $("#city1").attr("checked", false);
        if ($("#city0").is(':checked')) {
            $("#otherCitysRight").hide();
            $("#otherCitysLeft").fadeIn();
        } else {
            $("#otherCitysRight").hide();
            $("#otherCitysLeft").fadeOut();
        }
    });
    $("#city1Choose").click(function () {
        $("#city0").attr("checked", false);
        if ($("#city1").is(':checked')) {
            $("#otherCitysLeft").hide();
            $("#otherCitysRight").fadeIn();
        } else {
            $("#otherCitysLeft").hide();
            $("#otherCitysRight").fadeOut();
        }
    });

    //未登录视点赞及收藏，弹出登录框
    $(".agree").click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });
    $(".pleaseLogin").click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });

    $(".commentAgree").click(function () {
        if ($(this).attr("title") == "zan") {
            $.post("like_comment",
                {
                    comment_id: $(this).parent().attr("id").substr(7)
                },
                function (data, status) {
                    // alert(status);
                });
            $(this).parent().html("<img title='yizan' class='commentAgree' src='img/iconfont-zanzan2.png'>"+(new Number($(this).parent().text()) + 1));
        } else {
            alert("yizan");
        }
    });


    //登陆后点赞和收藏操作
    $(".useragree").click(function () {
        switch ($(this).attr("title")) {
            case "zan":
                ajaxLike("like", $(this).parent().parent().attr("id"))
                $(this).parent().html((new Number($(this).parent().text()) + 1) + "<img title='yizan' class='useragree' src='img/iconfont-zanzan2.png'>");
                break;
            case "yizan":
                alert("yizan");
                break;
            case "cai":
                ajaxLike("disLike", $(this).parent().parent().attr("id"))
                $(this).parent().html((new Number($(this).parent().text()) + 1) + "<img title='yicai' class='useragree' src='img/iconfont-zanzan-copy2.png'>");
                break;
            case "yicai":
                alert("yicai");
                break;
            case "shoucang":
                ajaxLike("collo", $(this).parent().parent().attr("id"))
                $(this).parent().html((new Number($(this).parent().text()) + 1) + "<img title='yishoucang' class='useragree' src='img/iconfont-icon2.png'>");
                break;
            case "yishoucang":
                alert("yishoucang")
                break;
        }
    });
});
function ajaxLike(option, article_id) {
    $.post("like_" + option,
        {
            article_id: article_id
        },
        function (data, status) {
            // alert(status);
        });
}