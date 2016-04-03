$(function () {
    $(".homedynamic").click(function () {
        if ($(this).text() == "首页") {
            location.href = "news_index";
        } else {
            alert($(this).text());
        }
    });
    $("#registerOption").click(function () {
        location.href = "login.jsp";
    });
    $("#loginEntry").click(function () {
        $(".shadow").fadeIn();
        $(".login").animate({
            top: '100px'
        });
    });
    $(".shadow").click(function () {
        $(".shadow").fadeOut();
        $(".login").animate({
            top: '-500px'
        });
    });
    $(".moreOption").click(function () {
        switch ($(this).text()){
            case "关于我们":
                location.href = "more_about";
                break;
            case "联系我们":
                location.href = "more_contact";
                break;
            case "用户协议":
                location.href = "more_agreenment";
                break;
            case "投诉指引":
                location.href = "more_complaint";
                break;
            case "合作说明":
                location.href = "more_cooperate";
                break;
        }
        // location.href = "more?mediaOption=" + $(this).text();
    });
    $(".singleNav").click(function () {
        switch ($(this).text()){
            case "关于我们":
                location.href = "more_about";
                break;
            case "联系我们":
                location.href = "more_contact";
                break;
            case "用户协议":
                location.href = "more_agreenment";
                break;
            case "投诉指引":
                location.href = "more_complaint";
                break;
            case "合作说明":
                location.href = "more_cooperate";
                break;
        }
    });
    $("#loginMore").click(function () {
        location.href = "more";
    });

    $(window).scroll(function () {
        var main = document.body.scrollTop;
        if (main > 70) {
            $(".mianBar").css('position', "fixed");
            $(".mianBar").css('top', "0");
        } else {
            $(".mianBar").css('position', "static");
        }
    });
    $("#loginOption").click(function () {
        if ($("#username").val() != "" && $("#password").val() != "") {
            var host = window.location.host;
            var realPath = window.location.href;
            // alert(realPath.substr(host.length+7+4));
            $.post("user_login",
                {
                    name: $("#username").val(),
                    password: $("#password").val(),
                    realPath: realPath.substr(host.length+7+4)
                },
                function (data, status) {
                    // alert("数据：" + data + "\n状态：" + status);
                   if (status&&data=="success"){
                       location.href="user_prePage?"+Math.random();
                   }else {
                       alert("用户名或密码错误！");
                   }
                });
            // $("#loginNow").submit();
        } else {
            alert("用户名或密码为空！");
        }
    });
    $("#registerOption").click(function () {
        location.href = "user_toregister";
    });

    $(".userOption").click(function () {
        if ($(this).text() == "退出") {
            location.href = "user_logout?"+Math.random();
        }
    });

});

function submitNow(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        if ($("#username").val() != "" && $("#password").val() != "") {
            $.post("user_login",
                {
                    name: $("#username").val(),
                    password: $("#password").val()
                },
                function (data, status) {
                    // alert("数据：" + data + "\n状态：" + status);
                    if (status&&data=="success"){
                        location.href="news_prePage?"+Math.random();
                    }else {
                        alert("用户名或密码错误！");
                    }
                });
            // $("#loginNow").submit();
        } else {
            alert("用户名或密码为空！");
        }
    }
}

