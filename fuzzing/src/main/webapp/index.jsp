<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fuzzing!</title>
    <!-- Bootstrap CSS -->
    <link rel="icon" href="http://qfdk.free.fr/favicon.ico" type="image/vnd.microsoft.icon">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 70px 0 20px 0;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <a href=https://github.com/qfdk/fuzzing-api" class="github-corner">
        <svg width="80" height="80" viewBox="0 0 250 250"
             style="fill:#151513; color:#fff; position: absolute; top: 0; border: 0; right: 0;">
            <path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path>
            <path d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2"
                  fill="currentColor" style="transform-origin: 130px 106px;" class="octo-arm"></path>
            <path d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z"
                  fill="currentColor" class="octo-body"></path>
        </svg>
    </a>
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Fuzzing!</a>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li><a href="/">Home</a></li>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</nav>

<div class="container">

    <div class="jumbotron">
        <div class="row">
            <h1>Fuzzing or Amazing</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="input-group">
                <input id="url" type="text" class="form-control" value="http://petstore.swagger.io/v2/swagger.json"
                       placeholder="http://localhost:8080/swagger.json">
                <span class="input-group-btn">
					<button id="go" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
                            type="button">Go!</button>
            </span>
            </div>
        </div>
    </div>

    <br>
    <div class="jumbotron" style="background-color: #fff9f0;">
        <%--<table class="table">--%>
            <%--<thead>--%>
            <%--<tr>--%>
                <%--<th>#No.</th>--%>
                <%--<th>Target url</th>--%>
                <%--<th>Reponse code</th>--%>
                <%--<th>Result list</th>--%>
                <%--<th>Operation Type</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>

            <%--</tbody>--%>
        <%--</table>--%>

        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-info">
                <div class="panel-heading" role="tab" id="default">
                    <h4 class="panel-title">
                        <a class="collapsed">
                            #No. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Operation type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reponse code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Result list&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Target url
                        </a></h4>
                </div>
            </div>

        </div>

    </div>

    <hr/>
    <p>
        <a href="#" target="_blank">About</a> &middot; Code licensed under <a
            href="http://www.apache.org/licenses/LICENSE-2.0"
            target="_blank">Apache
        License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/4.0/" target="_blank">CC
        BY 4.0</a>.
    </p>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Information :)</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-info" role="alert">Loading ...</div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/myapp.js"></script>
</body>
</html>