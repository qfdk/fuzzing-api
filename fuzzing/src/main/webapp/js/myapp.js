/**
 * Created by qfdk on 2016/10/23.
 */
$('#go').click(function () {
    send($('#url').val());
});


function send(json) {
    $('.table tbody tr').remove();
    $.get('http://'+window.location['hostname']+':8080/api/v1/getPath?url=' + json, function (data, b, c) {
        var paths =data.urls;
        var tmp='';
        paths.forEach(function (path,i) {
            var number = i+1;

            if (path.valided)
            {
                tmp='<tr class="success"><th scope="row">'+number+'</th><td>'+path.link+'</td> <td>'+path.reponseCode+'</td><td>'+path.codes+'</td><td>'+path.operationType+'</td></tr>';
            }else
            {
                tmp='<tr class="danger"><th scope="row">'+number+'</th><td>'+path.link+'</td> <td>'+path.reponseCode+'</td><td>'+path.codes+'</td><td>'+path.operationType+'</td></tr>';
            }
            $('.table').append(tmp);
            tmp = "";
        });
        $('#myModal').modal('hide');

    });
}