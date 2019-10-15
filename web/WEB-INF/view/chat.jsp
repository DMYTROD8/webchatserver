<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/open-iconic-bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="js/main.js"></script>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <!-- Vertical navbar -->
            <div class="vertical-nav bg-white" id="sidebar">
                <div class="py-4 px-3 mb-4 bg-light">
                    <div class="media d-flex align-items-center"><img src="/img/user_3.jpg"
                                                                      alt="..." width="50"
                                                                      class="mr-3 rounded-circle img-thumbnail shadow-sm">
                        <div id="media-body" class="media-body">
                            <b style="font-size: 14px;"></b>
                            <br>
                            <a href="/logout" style="font-size: 12px;">Вихід</a>
                        </div>
                    </div>
                </div>

                <p class="text-gray font-weight-bold text-uppercase px-3 small pb-4 mb-0">Чати</p>

                <ul class="nav flex-column mb-0">
                    <li class="nav-item">
                        <a class="nav-link active font-italic text-dark" href="#" role="tab" data-toggle="tab"
                           onclick="changeChat('general')">
                            <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                            Загальний чат
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-italic text-dark" href="#" role="tab" data-toggle="tab"
                           onclick="changeChat('dating')">
                            <i class="fa fa-picture-o mr-3 text-primary fa-fw"></i>
                            Знайомства
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-italic text-dark" href="#" role="tab" data-toggle="tab"
                           onclick="changeChat('flood')">
                            <i class="fa fa-address-card mr-3 text-primary fa-fw"></i>
                            Флуд
                        </a>
                    </li>
                    <li class="nav-item" role="tab" data-toggle="tab">
                        <a class="nav-link font-italic text-dark" href="#" role="tab" data-toggle="tab"
                           onclick="changeChat('adult')">
                            <i class="fa fa-cubes mr-3 text-primary fa-fw"></i>
                            18+
                        </a>
                    </li>
                </ul>

                <p class="text-gray font-weight-bold text-uppercase px-3 small py-4 mb-0">Повідомлення</p>

                <ul class="nav flex-column  mb-0">
                    <li class="nav-item">
                        <a class="nav-link active font-italic text-dark" href="#" role="tab" data-toggle="tab"
                           onclick="changeMessages('privateInbox')">
                            <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>
                            Вхідні
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-italic text-dark" id="outboxLink" href="#" role="tab" data-toggle="tab"
                           onclick="changeMessages('privateOutbox')">
                            <i class="fa fa-bar-chart mr-3 text-primary fa-fw"></i>
                            Вихідні
                        </a>
                    </li>
                </ul>
                <p class="text-gray font-weight-bold text-uppercase px-3 small py-4 mb-0">Опції</p>
                <ul class="nav flex-column  mb-0">
                    <li class="nav-item">
                        <a class="nav-link font-italic text-dark" href="#" data-toggle="modal" data-target="#modalUsers"
                           onclick="getUsersList();">
                            <i class="fa fa-bar-chart mr-3 text-primary fa-fw"></i>
                            Користувачі
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-italic text-dark" href="#" data-toggle="modal"
                           data-target="#modalStatus">
                            <i class="fa fa-bar-chart mr-3 text-primary fa-fw"></i>
                            Статус
                        </a>
                    </li>
                </ul>
                <!-- The Modal -->
                <div class="modal" id="modalUsers">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h6 class="modal-title">Список користувачів</h6>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">

                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger btn-sm close-modal" data-dismiss="modal">
                                    Закрити
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal fade bd-example-modal-sm" id="modalStatus">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h6 class="modal-title">Виберіть статус</h6>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">
                                <p style="text-align: center" uid="">
                                    <input type="button" role="button" onclick="changeUserStatus('0')" class="btn btn-sm btn-success popover-test" value="Available">
                                    <input type="button" role="button" onclick="changeUserStatus('1')" class="btn btn-sm btn-warning popover-test" value="Busy">
                                    <input type="button" role="button" onclick="changeUserStatus('2')" class="btn btn-sm btn-danger popover-test" value="Away">
                                </p>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger btn-sm close-modal" data-dismiss="modal">
                                    Закрити
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <!-- End vertical navbar -->
        </div>
        <!-- Messages bar -->
        <div class="col-md-5 center-panel">
            <div class="chat-container" class="chat-container">
                <div class="chat-header">

                </div>
                <div class="" style="padding-right: 17px; border-bottom: 1px solid rgba(0, 0, 0, .2);">
                    <table id="head-table" class="table table-borderless table-sm table-overflow"
                           style="margin-top: 0px !important; margin-left: 0px; margin-bottom: 0px;  width: 100%;"
                           role="grid"
                           width="100%">
                        <thead>
                        <tr role="row" style="font-size:12px;">
                            <th style="width: 13%;">Дата
                            </th>
                            <th style="width: 20%;">Відправник
                            </th>
                            <th style="width: 67%;">Повідомлення
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="chat-body message-scroll">
                    <div class="post-outbox-container" id="privateOutbox">
                        <div id="post-outbox-table-col">
                            <table id="post_outbox_table"
                                   class="table table-hover table-bordered dataTable no-footer table-sm table-overflow table-outbordered"
                                   style="margin-top: 0px !important; width: 100%;" role="grid"
                                   aria-describedby="post_inbox_table_info" width="100%" frame="void">
                                <thead>
                                <tr role="row" style="height: 0px;">
                                    <th class="sorting_desc" aria-controls="post_inbox_table" rowspan="1" colspan="1"
                                        style="width: 11%; padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: 0px;">
                                        <div class="dataTables_sizing" style="height:0px;overflow:hidden;">
                                            Дата
                                        </div>
                                    </th>
                                    <th class="sorting_disabled" rowspan="1" colspan="1"
                                        style="padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: auto; width: 20%; height: 0px;">
                                        <div class="dataTables_sizing" style="height:0px;overflow:hidden;">
                                            Відправник
                                        </div>
                                    </th>
                                    <th class="sorting_disabled" rowspan="1" colspan="1"
                                        style="padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: auto; width: 69%; height: 0px;"
                                        aria-label="Тема">
                                        <div class="dataTables_sizing" style="height:0px;overflow:hidden;">
                                            Повідомлення
                                        </div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="post-inbox-container" id="privateInbox">
                        <div id="post-inbox-table-col">
                            <table id="post_inbox_table"
                                   class="table table-hover table-bordered dataTable no-footer table-sm table-overflow table-outbordered"
                                   style="margin-top: 0px !important; width: 100%;" role="grid"
                                   aria-describedby="post_inbox_table_info" width="100%" frame="void">
                                <thead>
                                <tr role="row" style="height: 0px;">
                                    <th style="padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; width: 11%; height: 0px;">
                                    </th>
                                    <th style="padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; width: 20%; height: 0px;">
                                    </th>
                                    <th style="padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; width: 69%; height: 0px;">
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
                <div class="chat-footer">
                    <div class="textarea-div">
                        <div class="typearea">
                            <input id="message-receiver-input" class="message-receiver-input" type="text"
                                   placeholder="Отримувач..."
                                   uid="">
                            <button id="message-send-btn" class="btn btn-sm btn-primary pull-right">Відправити</button>
                            <hr>
                            <textarea id="message-input" class="message-input" placeholder="Текст повідомлення..."
                                      rows="3"></textarea>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-5 col-md-offset-2 col-sm-12 right-panel">
            <div id="chat-container" class="chat-container">
                <div class="chat-header">

                </div>
                <div id="chat-body-general" class="chat-body chat-scroll">
                    <div class="clearfix"></div>
                    <ul class="media-list chat-general" id="general">
                    </ul>
                    <ul class="media-list chat-dating" id="dating">
                    </ul>
                    <ul class="media-list chat-flood" id="flood">
                    </ul>
                    <ul class="media-list chat-adult" id="adult">
                    </ul>
                </div>
                <div class="chat-footer">
                    <div class="textarea-div">
                        <div class="typearea">
                            <input id="chat-input" class="chat-input" type="text" placeholder="Текст повідомлення..."
                                   uid="">
                            <button id="chat-send-btn" class="btn btn-sm btn-primary pull-right">Відправити</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


