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
        <ul class="nav navbar-nav navbar-right" style="margin-right: 20px;">
            <li><a href="#">Login</a></li>
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
                <input id="url" type="text" class="form-control" value="http://petstore.swagger.io/v2/swagger.json" placeholder="http://localhost:8080/swagger.json">
                <span class="input-group-btn">
					<button id="go" class="btn btn-primary" type="button">Go!</button>
            </span>
            </div>
        </div>
    </div>

    <br>
    <div class="jumbotron" style="background-color: #fff9f0;">
        <table class="table">
            <thead>
            <tr>
                <th>#No.</th>
                <th>Tageet url</th>
                <th>Result ?</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>

    <hr/>
    <p>
        <a href="#" target="_blank">About</a> &middot; Code licensed under <a
            href="http://www.apache.org/licenses/LICENSE-2.0"
            target="_blank">Apache
        License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/4.0/" target="_blank">CC
        BY 4.0</a>.
    </p>
</div>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/basket.js/0.5.2/basket.full.min.js"></script>
<script type="text/javascript">
    basket.require({
        url: "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/js/collapse.min.js",
        unique: "1"
    });
</script>
<script src="js/myapp.js"></script>
</body>
</html>