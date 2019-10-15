<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LOGIN</title>
    <style>
        html {
            height: 100%;
        }

        body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: #FFCC00;
        }

        em {
            display: block;
            text-align: center;
            color: #a90329;
            visibility: hidden;
            margin-top: 6px;
            padding: 0 1px;
            font-style: normal;
            font-size: 12px;
        }
    </style>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/open-iconic-bootstrap.css"/>


</head>
<body>
<div class="container h-100">
    <div class="row h-100">
        <div class="col-sm-12 col-md-12 col-xl-12 my-auto">
            <h1 class="text-center"><span class="oi oi-chat"></span></h1>
            <div class="card card-block w-25 mx-auto">
                <ul class="nav nav-pills nav-justified">
                    <li class="nav-item">
                        <a class="nav-link active" href="#" role="tab" data-toggle="tab" id="enter-nav">Вхід</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" role="tab" data-toggle="tab" id="reg-nav">Реєстрація</a>
                    </li>
                </ul>
                <div id="loginForm" class="card-body">
                    <h2 class="text-center mb-4"></h2>
                    <div class="form-group">
                        <label for="inputEmailForm" class="sr-only form-control-label">Email</label>
                        <div class="mx-auto col-sm-10">
                            <input type="text" class="form-control" name="login" id="inputEmailForm"
                                   placeholder="логін"
                                   required="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPasswordForm" class="sr-only form-control-label">Password</label>
                        <div class="mx-auto col-sm-10">
                            <input type="text" class="form-control" name="password" id="inputPasswordForm"
                                   placeholder="пароль"
                                   required="">
                            <em id="info-bubble-login">template</em>
                        </div>
                    </div>
                    <div class="mx-auto col-sm-10 pb-3 pt-5">
                        <button id="login-btn" type="submit" class="btn btn-outline-primary btn-sm btn-block">
                            Ввійти
                        </button>
                    </div>
                </div>
                <div id="registerForm" class="card-body">
                    <h2 class="text-center mb-4"></h2>
                    <div class="form-group">
                        <label for="input2EmailForm" class="sr-only form-control-label">логін</label>
                        <div class="mx-auto col-sm-10">
                            <input type="text" class="form-control" name="login" id="input2EmailForm"
                                   placeholder="логін">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="input2PasswordForm" class="sr-only form-control-label">пароль</label>
                        <div class="mx-auto col-sm-10">
                            <input type="password" class="form-control" name="password" id="input2PasswordForm"
                                   placeholder="пароль">
                            <em id="info-bubble-register">template</em>
                        </div>
                    </div>
                    <div class="mx-auto col-sm-10 pb-3 pt-5">
                        <button id="register-btn" type="submit"
                                class="btn btn-outline-primary btn-sm btn-block" formaction="register">
                            Зареєструватися
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#registerForm").hide();
    $("#loginForm").show();
    $("#enter-nav").click(function () {
        $("#registerForm").hide();
        $("#loginForm").show();
    });

    $("#reg-nav").click(function () {
        $("#loginForm").hide();
        $("#registerForm").show();
    });

    $('#register-btn').click(function () {
        if (!$.trim($('#input2EmailForm').val()) == "" && !$.trim($('#input2PasswordForm').val()) == "") {
            loginSign(true);
        }
    });
    $('#login-btn').click(function () {
        if (!$.trim($('#inputEmailForm').val()) == "" && !$.trim($('#inputPasswordForm').val()) == "") {
            loginSign(false);
        }
    });
    $('#input2EmailForm').click(function () {
        $("#info-bubble-register").css("visibility", "hidden");
    });
    $('#inputEmailForm').click(function () {
        $("#info-bubble-login").css("visibility", "hidden");
        $("#inputPasswordForm").css("border-color", "#ced4da");
    });
    $('#inputPasswordForm').click(function () {
        $("#info-bubble-login").css("visibility", "hidden");
        $("#inputEmailForm").css("border-color", "#ced4da");
    });

    function loginSign(register) {

        if (register == true) {
            var login = document.getElementById("input2EmailForm").value;
            var password = document.getElementById("input2PasswordForm").value;
            var url = window.location.href + "register";
        } else {
            var login = document.getElementById("inputEmailForm").value;
            var password = document.getElementById("inputPasswordForm").value;
            var url = window.location.href + "login";
        }

        $.ajax({
            url: url,
            type: 'post',
            data: "login=" + login + "&password=" + password,
            success: function (response, status, xhr) {
                if (response.action == "register") {
                    regViewFormat(response);
                } else if (response.action == "login") {
                    logViewFormat(response);
                }


            }

        });
    }

    function regViewFormat(response) {
        if (response.response == 0) {
            $("#info-bubble-register").text("Успішно зареєстровано");
            $("#info-bubble-register").css("visibility", "visible");
            $('#input2EmailForm').val('');
            $('#input2PasswordForm').val('');
            $("#input2EmailForm").css("border-color", "#ced4da");
        } else if (response.response == 1) {
            $("#info-bubble-register").text("Логін має бути не більше 10 знаків");
            $("#info-bubble-register").css("visibility", "visible");
            $("#input2EmailForm").css("border-color", "#a90329");
        } else if (response.response == 2) {
            $("#info-bubble-register").text("Логін зайнято");
            $("#info-bubble-register").css("visibility", "visible");
            $("#input2EmailForm").css("border-color", "#a90329");
        }
    }

    function logViewFormat(response) {
        if (response.response == 0) {
            sessionStorage.setItem('S_userID', response.id);
            sessionStorage.setItem('S_username', response.un);
            location.reload();
        } else if (response.response == 1) {
            $("#info-bubble-login").text("Неправильний пароль");
            $("#info-bubble-login").css("visibility", "visible");
            $("#inputPasswordForm").css("border-color", "#a90329");
        } else if (response.response == 2) {
            $("#info-bubble-login").text("Користувача не знайдено");
            $("#info-bubble-login").css("visibility", "visible");
            $("#inputEmailForm").css("border-color", "#a90329");
        }
    }
</script>
</body>
</html>