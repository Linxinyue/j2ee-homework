$(function () {
    $("[data-mask]").inputmask();
    $("#submit").click(function () {

        if ($('#title').val() != "" && $('#author').val() != ""
            && $('#date').val() != "" && $('#abstracts').val() != "" && $('#content').val() != "" ) {

            $.post("admin/article_add",
                {
                    title: $('#title').val(),
                    author: $('#author').val(),
                    date: $('#date').val(),
                    abstracts: $('#abstracts').val(),
                    classification: $('#classification').val(),
                    content: $('#content').val()
                },
                function (data, status) {
                    // alert("success!!!!");
                    if (status){
                        $('#tips').text("提交成功！！！！");
                        setInterval("$('#tips').text('')",3000);
                        title: $('#title').val("");
                        author: $('#author').val("");
                        date: $('#date').val("");
                        abstracts: $('#abstracts').val("");
                        content: $('#content').val("");
                    }else {
                        $('#tips').text("提交失败，请检查后重试！！！！");
                        setInterval("$('#tips').text('')",3000);
                    }
                });
        }else {
            alert("所有内容均为必填项，请完全填写后在进行提交！");
        }
    });
    
    $("#check").click(function () {
        if($("#id").val() != ""){
            $.post("admin/comment_getArticleTtitle",
                {
                    ids: $('#id').val()
                },
                function (data, status) {
                    if (status){
                        $('#title').val(data);

                    }else {
                        alert(status+"::"+data);
                    }
                });
        }else {
            alert("id should not be none");
        }
    });

    $("#checkUser").click(function () {
        if($("#userId").val() != ""){
            $.post("admin/comment_getUserName",
                {
                    ids: $('#userId').val()
                },
                function (data, status) {
                    if (status){
                        $('#userName').val(data);
                    }else {
                        alert(status+"::"+data);
                    }
                });
        }else {
            alert("id should not be none");
        }
    });
    
    $("#commentSUbmit").click(function () {
        if ($('#title').val() != "" && $('#author').val() != ""
            && $('#date').val() != "" && $('#abstracts').val() != "" && $('#content').val() != "" ) {

            $.post("admin/comment_add",
                {
                    ids: $('#id').val(),
                    userId: $('#userId').val(),
                    date: $('#date').val(),
                    content: $('#content').val()
                },
                function (data, status) {
                    // alert("success!!!!");
                    if (status){
                        $('#tips').text("提交成功！！！！");
                        setInterval("$('#tips').text('')",3000);
                    }else {
                        $('#tips').text("提交失败，请检查后重试！！！！");
                        setInterval("$('#tips').text('')",3000);
                    }
                });
        }else {
            alert("所有内容均为必填项，请完全填写后在进行提交！");
        }
    });
});