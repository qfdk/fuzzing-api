/**
 * Created by qfdk on 2016/10/23.
 */
$('#go').click(function () {
    send($('#url').val());
});


function send(json) {
    $('.table tbody tr').remove();
    $.get('http://localhost:8080/api/v1/getPath?url=' + json, function (data, b, c) {
        var paths =data.paths;
        paths.forEach(function (path,i) {
            var number = i+1;
            var tmp='<tr class="active"><th scope="row">'+number+'</th><td>'+path.split("#")[1]+'</td> <td>'+path.split("#")[0]+'</td> </tr>';
            $('.table').append(tmp);
        });

    });
}