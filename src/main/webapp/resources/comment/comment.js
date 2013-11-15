//Yarosalav Kovbas Intellij Idea 12
var pageCont;
function initializeComments(enterpriseId, context) {
    getComments(enterpriseId);
    pageCont=context
}

function getComments(enterpriseId) {
    var ajax = getXMLHttpRequestWithFunctionOnReadyState(function () {
        if (ajaxSuccess(ajax)) {
            renderComments(ajax.responseText);
        }
    });
    ajax.open("GET", pageCont+"/ajax/getComments?id=" + enterpriseId, true);
    ajax.withCredentials = true;
    ajax.setRequestHeader("Accept", "application/json");
    ajax.send();
}

function renderComments(responseText) {
    var jsonResponse = JSON.parse(responseText);
    for (var iterator in jsonResponse) {
        var commentInfo = JSON.parse(jsonResponse[iterator]);
        commentInfo['summary_ratio'] = commentInfo['ratio_positive'] - commentInfo['ratio_negative'];
        var commentTemplate = $("#template_comment").html();
        var comment = Mustache.render(commentTemplate, commentInfo);
        $("#comments").append(comment);
        disableRateButtonsIfAlreadyRated(commentInfo);
    }
}

function confirmEditComment(commentId){
    var formData = $("#edit_form_" + commentId).serializeArray();
    var URL = pageCont+"/ajax/updateComment";
    $.post(URL,
        formData,
        function(data, textStatus, jqXHR)
        {
            updateComment(data, commentId);
        }).fail(function(jqXHR, textStatus, errorThrown)
        {
            cancelEditComment(commentId);
        });
}

function updateComment(data, commentId){
    var commentTemplate = $("#template_comment").html();
    var jsonResponseObj = JSON.parse(data);
    jsonResponseObj['summary_ratio'] = jsonResponseObj['ratio_positive'] - jsonResponseObj['ratio_negative'];
    var comment = Mustache.render(commentTemplate, jsonResponseObj);
    $("#comment_" + commentId).replaceWith(comment);
    disableRateButtonsIfAlreadyRated(jsonResponseObj);
}

function cancelEditComment(commentId){
    $("#comment_main_" + commentId).show();
    $("#comment_footer_" + commentId).show();
    $("#edit_form_" + commentId).remove();
}

function deleteComment(commentId){
    var ajax = getXMLHttpRequestWithFunctionOnReadyState(function () {
        if (ajaxSuccess(ajax)) {
            runEffect("comment_" + commentId, function() {removeCommentFromForm(commentId);});
        }
    });
    ajax.open("GET", pageCont+"/ajax/deleteComment?id=" + commentId, true);
    ajax.withCredentials = true;
    ajax.send();
}

function runEffect(elementId, callback) {
    var effects = [ "bounce", "clip", "drop", "explode", "fold", "highlight", "puff",
                    "pulsate", "scale", "shake", "size", "slide" ];
    var effectNumber = Math.floor((Math.random()*(effects.length - 1))+1);
    var selectedEffect = effects[effectNumber];
    var options = {};
    if ( selectedEffect === "scale" ) {
        options = { percent: 0 };
    } else if ( selectedEffect === "size" ) {
        options = { to: { width: 200, height: 60 } };
    }
    $("#" + elementId).hide(selectedEffect, options, 1000, callback);
}

function addComment(){
    var formData = $("#form_add_comment").serializeArray();
    var URL = pageCont+"/ajax/addComment";
    $.post(URL,
        formData,
        function(data, textStatus, jqXHR)
        {
            addCommentToPage(data);
        }).fail(function(jqXHR, textStatus, errorThrown)
        {
            alert("something wrong");
        }).withCredentials = true;
}

function addCommentToPage(data){
    var commentInfo = JSON.parse(data);
    commentInfo['summary_ratio'] = commentInfo['ratio_positive'] - commentInfo['ratio_negative'];
    var commentTemplate = $("#template_comment").html();
    var comment = Mustache.render(commentTemplate, commentInfo);
    $("#comments").append(comment);
    disableRateButtonsIfAlreadyRated(commentInfo);
}

function removeCommentFromForm(commentId){
    $("#comment_" + commentId).remove();
}

function editComment(commentId){
    var ajax = getXMLHttpRequestWithFunctionOnReadyState(function () {
        if (ajaxSuccess(ajax)) {
            cancelEditComment(commentId);
            convertCommentToEditState(ajax.responseText, commentId);
        }
    });
    ajax.open("GET", pageCont+"/ajax/getComment?id=" + commentId, true);
    ajax.setRequestHeader("Accept", "application/json");
    ajax.send();
}

function rateComment(action, commentId){
    var ajax = getXMLHttpRequestWithFunctionOnReadyState(function () {
        if (ajaxSuccess(ajax)) {
            updateComment(ajax.responseText, commentId);
        }
    });
    ajax.open("POST", pageCont+"/ajax/rateComment", true);
    ajax.withCredentials = true;
    ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    ajax.send("action=" + action +"&id=" + commentId);
}

function replyComment(commentId){
    $('#replyBox');
}

function convertCommentToEditState(responseText, commentId) {
    var jsonResponseObj = JSON.parse(responseText);
    jsonResponseObj['localized_confirm'] = "confirm";
    jsonResponseObj['localized_cancel'] = "cancel";
    var edit_template = $("#template_comment_edit").html();
    var editPart = Mustache.render(edit_template, jsonResponseObj);
    $("#comment_main_" + commentId).hide();
    $("#comment_footer_" + commentId).hide();
    $("#comment_header_" + commentId).after(editPart);
}

function disableRateButtonsIfAlreadyRated(commentInfo){
    if(!commentInfo["can_rate"]){
        $("#rate_btn_down_" + commentInfo["id"]).prop('disabled', true);
        $("#rate_btn_up_" + commentInfo["id"]).prop('disabled', true);
    }
}

function ajaxSuccess(ajax) {
    return ajax.readyState == 4 && ((ajax.status >= 200 && ajax.status <= 300) || ajax.status == 304);
}

function getXMLHttpRequest() {
    return window.XMLHttpRequest
        ? new XMLHttpRequest()
        : new ActiveXObject("Microsoft.XMLHTTP");
}

function getXMLHttpRequestWithFunctionOnReadyState(_function) {
    var ajax = window.XMLHttpRequest
        ? new XMLHttpRequest()
        : new ActiveXObject("Microsoft.XMLHTTP");
    ajax.onreadystatechange = _function;
    return ajax;
}


