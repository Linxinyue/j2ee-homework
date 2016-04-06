$(function () {
    $(".adminSubmit").click(function () {
        if ($("#username").val() == "" || $("#password").val() == "" || $("#password1").val() == "") {
            alert("must input all")
        } else if ($("#password").val() != $("#password1").val()) {
            alert("password not same!")
        } else {
            $.post("user_register",
                {
                    name: $("#username").val(),
                    password: $("#password").val()
                },
                function (data, status) {
                    // alert("数据：" + data + "\n状态：" + status);
                    if (status) {
                        location.href = "index.html";
                    }
                });
        }
    });
});

function submitNow(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        if ($("#username").val() == "" || $("#password").val() == "" || $("#password1").val() == "") {
            alert("must input all")
        } else if ($("#password").val() != $("#password1").val()) {
            alert("password not same!")
        } else {
            $.post("user_register",
                {
                    name: $("#username").val(),
                    password: $("#password").val()
                },
                function (data, status) {
                    if (status) {
                        location.href = "index.html";
                    }
                });
        }
    }
}
function checkName() {
    // $("#password").val($("#username").val());
    $.post("user_checkName",
        {
            name: $("#username").val()
        },
        function (data, status) {
            if (status && data == "find") {
                $("#username").css("border-color", "red");
            } else if (data == "not") {
                $("#username").css("border-color", "#eaeaea");
            }
        });
}