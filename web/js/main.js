function sendToChat() {
    var text = document.getElementById("chat-input").value;
    var toId = parseInt(document.getElementById("chat-input").getAttribute("uid"));

    if (isNaN(toId)) {
        toId = -1;
    }

    var box = {
        text: text,
        toId: toId
    };

    var jsonBox = {
        chat: sessionStorage.getItem('S_chatType'),
        msg: JSON.stringify(box)
    };

    var url = window.location.href + "add";

    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(jsonBox),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
            document.getElementById("chat-input").setAttribute("uid", "");
        }

    });
}


function sendMessage(text) {
    var text = document.getElementById("message-input").value;
    var toLogin = document.getElementById("message-receiver-input").value;
    var url = window.location.href + "add";

    var box = {
        text: text,
        toLogin: toLogin,
    };

    var jsonBox = {
        chat: "privateOutbox",
        msg: JSON.stringify(box)
    };

    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(jsonBox),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
        }

    });
}


function updateChat() {
    var url = window.location.href + "get";
    var lastMessage = 0;
    var chatType = sessionStorage.getItem('S_chatType');

    if (chatType === "general") {
        lastMessage = currentUser.lastGeneralMessage;
    } else if (chatType === "dating") {
        lastMessage = currentUser.lastDatingMessage;
    } else if (chatType === "flood") {
        lastMessage = currentUser.lastFloodMessage;
    } else if (chatType === "adult") {
        lastMessage = currentUser.lastAdultMessage;
    }

    $.ajax({
        type: "GET",
        url: url,
        data: "chat=" + chatType + "&from=" + lastMessage,
        success: function (response) {
            var obj = response;
            if (obj.response != 0) {
                return;
            }

            var count = obj.list.length;
            if (count > 0) {
                for (var i = 0; i < count; i++) {
                    add(obj.list[i].date, obj.list[i].from, obj.list[i].fromId, obj.list[i].toId, obj.list[i].text, count);
                }
                if (chatType === "general") {
                    lastMessage = currentUser.lastGeneralMessage += count;
                } else if (chatType === "dating") {
                    lastMessage = currentUser.lastDatingMessage += count;
                } else if (chatType === "flood") {
                    lastMessage = currentUser.lastFloodMessage += count;
                } else if (chatType === "adult") {
                    lastMessage = currentUser.lastAdultMessage += count;
                }
            }

        }
    });
}


function updateMessages() {
    var url = window.location.href + "get";
    var lastMessage = 0;
    var chatType = sessionStorage.getItem('S_messageType');

    if (chatType === "privateInbox") {
        lastMessage = currentUser.lastInboxMessage;
    } else if (chatType === "privateOutbox") {
        lastMessage = currentUser.lastOutboxMessage;
    }

    $.ajax({
        type: "GET",
        url: url,
        data: "chat=" + chatType + "&from=" + lastMessage,
        success: function (response) {
            var obj = response;
            if (obj.response != 0) {
                return;
            }
            var count = obj.list.length;
            if (count > 0) {
                for (var i = 0; i < count; i++) {
                    addMessage(obj.list[i].date, obj.list[i].from, obj.list[i].fromId, obj.list[i].to, obj.list[i].toLogin, obj.list[i].text, chatType);
                }
                if (chatType === "privateInbox") {
                    lastMessage = currentUser.lastInboxMessage += count;
                } else if (chatType === "privateOutbox") {
                    lastMessage = currentUser.lastOutboxMessage += count;
                }
            }
        }
    });
}


function getUsersList() {
    var url = window.location.href + "users";

    $.ajax({
        type: "GET",
        url: url,
        data: "",
        success: function (response) {
            var obj = JSON.parse(response);
            var usersArray = obj['list'];
            addUsersModal(usersArray);
        }
    });
}

function getUserStatus(login) {
    var url = window.location.href + "status";

    $.ajax({
        type: "GET",
        url: url,
        data: "login=" + login + "&change=",
        success: function (response) {
            var obj = JSON.parse(response);
            if (obj.response != 0) {
                return;
            }
            var status = obj.status;
            var d1 = document.getElementsByClassName("modal-body")[0].getElementsByTagName("p");
            for (i = 0; i < d1.length; i++) {
                if (d1[i].getElementsByTagName("span")[0].innerText === login) {
                    var stringToAdd = d1[i].getElementsByTagName("span")[0];
                    stringToAdd.innerText = stringToAdd.innerText + " " + status;
                }
            }

        }
    });
}

function add(date, from, fromId, toId, msgtext, count) {

    var htmlInsert = '<li class="media">\n' +
        '<a class="pull-left avatar-class">\n' +
        '<img src="/img/user_3.jpg" alt="" class="img-circle">\n' +
        '</a>\n' +
        '<div class="media-body">\n' +
        '<span class="text-muted pull-right">\n' +
        '<small class="text-muted">' + date + '</small>\n' +
        '</span>\n' +
        '<span class="username" onclick="pointerSendChatTo(&#34;' + from + '&#34;, ' + fromId + ')">' + from + '</span>\n' +
        '<span class="msg-text">' + msgtext + '</span>' +
        '</div>\n' +
        '</li>';

    var placeToInsert = document.getElementById(sessionStorage.getItem("S_chatType"));
    placeToInsert.insertAdjacentHTML('beforeend', htmlInsert);

    if (toId === parseInt(sessionStorage.getItem('S_userID'))) {
        var ll = document.getElementsByClassName("msg-text").length;
        document.getElementsByClassName("msg-text")[ll - 1].style.background = "#efefef";
    }

    //автопрокрутка на останнє повідомлення в чаті
    var blockl = document.getElementsByClassName("media").length;
    var block = document.getElementsByClassName("media")[blockl - 1];
    block.scrollIntoView({
        block: "center",
        behavior: "smooth"
    });
}

function addMessage(date, fromLogin, fromId, toId, toLogin, msgtext, chatType) {
    var id;
    var login;

    if (chatType === "privateInbox") {
        id = fromId;
        login = fromLogin;
    } else if (chatType === "privateOutbox") {
        id = toId;
        login = toLogin;
    }

    var htmlInsert = '<tr role="row" class="odd">' +
        '<td style="font-size: 13px;">' + date + '</td>' +
        '<td><a class="playerLink pointer" data-pid="' + id + '">' + login + '</a></td>' +
        '<td>' + msgtext + '</td></tr>';

    var placeToInsert = document.getElementById(sessionStorage.getItem("S_messageType")).getElementsByTagName('tbody')[0];
    placeToInsert.insertAdjacentHTML('afterbegin', htmlInsert);

}

function addUsersModal(usersArray) {
    document.getElementById("modalUsers").getElementsByClassName("modal-body")[0].innerHTML = "";
    var count = usersArray.length;

    for (var i = 0; i < count; i++) {

        var htmlInsert = '<p style="text-align: center" uid="' + usersArray[i] + '"><span>' + usersArray[i] + '</span>' +
            '<input type="button" role="button" onclick="pointerSendMessageTo(' + '&#34;' + usersArray[i] + '&#34;' + ')" class="btn btn-sm ' +
            'btn-warning popover-test pull-right" value="Відправити лист">' +
            '<input type="button" role="button" onclick="getUserStatus(' + '&#34;' + usersArray[i] + '&#34;' + ')" class="btn btn-sm ' +
            'btn-info popover-test pull-left" value="Дізнатися статус">' +
            '</p>';

        var placeToInsert = document.getElementById("modalUsers").getElementsByClassName("modal-body")[0];
        placeToInsert.insertAdjacentHTML('beforeend', htmlInsert);

    }

}

function pointerSendMessageTo(to) {
    document.getElementsByClassName("close-modal")[0].click();
    document.getElementById("message-receiver-input").value = to;

    setTimeout(function () {
        document.getElementById("message-input").focus();
    }, 500);
}

function changeUserStatus(status) {
    var url = window.location.href + "status";

    $.ajax({
        type: "GET",
        url: url,
        data: "change="+status,
        success: function (response) {

        }
    });
}

function pointerSendChatTo(to, id) {
    document.getElementById("chat-input").value = to + ", ";
    document.getElementById("chat-input").setAttribute("uid", id);
    document.getElementById("chat-input").focus();
}

function changeChat(type) {
    var oldChat = sessionStorage.getItem("S_chatType");
    document.getElementById(oldChat).style.display = "none";

    sessionStorage.setItem("S_chatType", type);
    document.getElementById(type).style.display = "block";
    updateChat();
}

function changeMessages(type) {
    var headToChange = document.getElementById("head-table").getElementsByTagName("th")[1];

    if (type === "privateOutbox") {
        headToChange.innerText = "Отримувач";
    } else {
        headToChange.innerText = "Відправник";
    }

    var oldMessages = sessionStorage.getItem("S_messageType");
    document.getElementById(oldMessages).style.display = "none";
    sessionStorage.setItem("S_messageType", type);
    document.getElementById(type).style.display = "block";
    updateMessages();
}


window.onload = function () {
    window.currentUser = {
        lastGeneralMessage: 0,
        lastDatingMessage: 0,
        lastFloodMessage: 0,
        lastAdultMessage: 0,
        lastInboxMessage: 0,
        lastOutboxMessage: 0
    };

    document.getElementById("media-body").getElementsByTagName("b")[0].innerText =
        sessionStorage.getItem('S_username');
    sessionStorage.setItem('S_chatType', 'general');
    sessionStorage.setItem('S_messageType', 'privateInbox');
    document.getElementById("privateInbox").style.display = "block";
    document.getElementById("privateOutbox").style.display = "none";

    updateChat();
    updateMessages();

    setInterval(function () {
        updateChat();
        updateMessages();
    }, 10000);
};

$(document).ready(function () {
    $('#chat-send-btn').click(function () {
        if (!$.trim($('#chat-input').val()) == "") {
            sendToChat();
            setTimeout(function () {
                updateChat();
            }, 500);
            $('#chat-input').val('');
        }
    });
    $('#chat-input').keypress(function (e) {
        var key = e.which;
        if (key == 13) // the enter key code
        {
            $('#chat-send-btn').click();
            return false;
        }
    });

    $('#message-send-btn').click(function () {
        if (!$.trim($('#message-receiver-input').val()) == "" && !$.trim($('#message-input').val()) == "") {
            sendMessage();
            setTimeout(function () {
                $('#outboxLink').click();
                //changeMessages("privateOutbox");
                // updateMessages();
            }, 500);
            $('#message-receiver-input').val('');
            $('#message-input').val('');
        }
    });
    $('#message-input').keypress(function (e) {
        var key = e.which;
        if (key == 13) // the enter key code
        {
            $('#message-send-btn').click();
            return false;
        }
    });


    $('.btn-logout').click(function () {
        location.href = location.href;
        location.reload();
    });

    $('#inpt').on('input', function () {
        if ($.trim($('#chat-input').val()) == "") {
            $('#chat-input').attr("uid", "");
        }
    }).trigger('input');

});