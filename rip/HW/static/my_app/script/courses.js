$(function () {
    let csrftoken = $.cookie('csrftoken');
    $.ajax({
        method: 'post',
        url: '',
        dataType: 'json',
        data: {
            data: 'getcourses',
            csrfmiddlewaretoken: csrftoken
        },
    }).done(function (msg) {
        console.log(msg);
        let inp = jQuery.parseJSON(msg);
        console.log(inp);
        msg.forEach(function (item, index, array) {
            let id_selector = '#' + item;
            $(id_selector).addClass('list-group-item-success')
        })
    })
});