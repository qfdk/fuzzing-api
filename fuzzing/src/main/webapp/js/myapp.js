/**
 * Created by qfdk on 2016/10/23.
 */
$('#go').click(function () {
    send($('#url').val());
});

$('#go').keyup(function (evt) {
    if (evt.keyCode == 13) {
        send($('#url').val());
    }
});

function send(json) {
    $.get('http://localhost:8080/api/v1/getPath?url=' + json, function (data, b, c) {
        $('.active').append(data.paths);
    });
}