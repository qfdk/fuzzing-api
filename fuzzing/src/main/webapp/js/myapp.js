/**
 * Created by qfdk on 2016/10/23.
 */

$('#go').click(function () {
    $.get('http://localhost:8080/api/v1/getPaths?url='+$('#url').val());
});

$('#go').keyup(function (evt) {
    if (evt.keyCode == 13) {
        $.get('http://localhost:8080/api/v1/getPaths?url='+$('#url').val());
    }
});